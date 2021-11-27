package com.amr.gradutionPro.gradutionPro.Controller;

import com.amr.gradutionPro.gradutionPro.DTO.TurnDto;
import com.amr.gradutionPro.gradutionPro.GradutionProApplication;
import com.amr.gradutionPro.gradutionPro.Model.ActiveTurns;
import com.amr.gradutionPro.gradutionPro.Model.Turn;
import com.amr.gradutionPro.gradutionPro.Model.User;
import com.amr.gradutionPro.gradutionPro.Repositry.ActiveTurnRep;
import com.amr.gradutionPro.gradutionPro.Repositry.TurnRep;
import com.amr.gradutionPro.gradutionPro.Repositry.UserRep;
import com.amr.gradutionPro.gradutionPro.Services.TurnServices;
import com.amr.gradutionPro.gradutionPro.Sms.SmsRequest;
import com.amr.gradutionPro.gradutionPro.Sms.SmsSender;
import com.amr.gradutionPro.gradutionPro.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.amr.gradutionPro.gradutionPro.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amr.gradutionPro.gradutionPro.EmailCfg;

@Controller
@RequestMapping("api/Turn")
public class TurnCont {
    private EmailCfg emailCfg;

    public TurnCont(EmailCfg emailCfg, SmsSender smsSender) {
        this.emailCfg = emailCfg;
        this.smsSender = smsSender;
    }

    @Autowired
    TurnServices turnServices;
    @Autowired
    TurnRep turnRep;
    @Autowired
    UserRep userRep;
    @Autowired
    ActiveTurnRep activeTurnRep;
    SmsSender smsSender;

    // function to create turn
    @PostMapping(path = "/pickTurn")
    public ResponseEntity<String> creatTurn(@RequestBody TurnDto turnDto) {
        initArrayList();
        System.out.println("reached1");
        long estimatedTime = 0;
        long clientExist = checkClient(turnDto.getClientId());
        LocalTime nowTime = LocalTime.now();
        LocalDate nowDate = LocalDate.now();
        Turn turn = new Turn();
        ActiveTurns activeTurn = new ActiveTurns();
        Long leastTime;
        long serviceTime = getServiceTime(turnDto.getSrvice());
        ActiveTurns canelled = getCancelledTurn();

        if (GradutionProApplication.turns.size() <= 100 && clientExist == 0) {
            System.out.println("size is "+GradutionProApplication.turns.size());
            if (turnDto.getArrivingTime() == 0 && canelled != null && serviceTime <= (canelled.getEstimatedTime() - canelled.getEstimatedTimeToStart()) && GradutionProApplication.numberofEmptyWindows == 0 && GradutionProApplication.turns.size() >=3) {
                System.out.println("in cancelled condition");
                estimatedTime = serviceTime + 2;
                activeTurn.setEstimatedTime((int) estimatedTime);
                activeTurn.setStatus(Status.waiting);
                activeTurn.setTime(nowTime);
                activeTurn.setDate(nowDate);
                activeTurn.setClientId(turnDto.getClientId());
                activeTurn.setService(turnDto.getSrvice());
                int timeToStart = (int) estimatedTime - (int) serviceTime;

                activeTurn.setEstimatedTimeToStart(timeToStart);
                activeTurn.setWindowSelected(canelled.getWindowSelected());




            } else {
                if (GradutionProApplication.numberofEmptyWindows > 0 && GradutionProApplication.turns.size() <= 7) {
                    estimatedTime = serviceTime + turnDto.getArrivingTime() + 2;
                    turn.setWindowSelected(GradutionProApplication.window);
                    GradutionProApplication.numberofEmptyWindows--;
                    GradutionProApplication.window++;

                } else {
                    leastTime = getLeastTime();
                    System.out.println("least is " + leastTime);
                    estimatedTime = serviceTime + turnDto.getArrivingTime() + leastTime + 2;
                    turn.setWindowSelected(GradutionProApplication.window);
                    if (leastTime == 0) {
                        GradutionProApplication.numberofEmptyWindows--;
                    }
                }

                activeTurn.setClientId(turnDto.getClientId());
                activeTurn.setService(turnDto.getSrvice());
                activeTurn.setStatus(Status.waiting);
                activeTurn.setTime(nowTime);
                activeTurn.setDate(nowDate);
                activeTurn.setEstimatedTime((int)estimatedTime);
                activeTurn.setWindowSelected(turn.getWindowSelected());
                int timeToStart = (int) estimatedTime - (int) serviceTime;
                activeTurn.setEstimatedTimeToStart(timeToStart);

                turn.setEstimatedTimeToStart(timeToStart);
            }

            GradutionProApplication.QueueTime = (int) estimatedTime;

            activeTurnRep.save(activeTurn);
            GradutionProApplication.turns.add(activeTurn);
            turn.setClientId(activeTurn.getClientId());
            turn.setEstimatedTime((int) estimatedTime);
            turn.setService(activeTurn.getService());
            turn.setTime(nowTime);
            turn.setDate(nowDate);
            turn.setStatus(activeTurn.getStatus());
            turn.setEstimatedTimeToStart(activeTurn.getEstimatedTimeToStart());
            turnServices.saveTurn(turn);


            String u = "TURN IS RESERVED and it needs to start " + turn.getEstimatedTimeToStart() +
                    " and time picked is " + nowTime + "picked at  "
                    + nowTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                    "\n number of empty windows" + GradutionProApplication.numberofEmptyWindows +
                    " at window " + GradutionProApplication.turns.get(GradutionProApplication.turns.size() - 1).getWindowSelected() +
                    "and the whole process will take" + turn.getEstimatedTime() + " Service Time" + serviceTime;
            sendEmail(turnDto.getClientId(), u);
            sendSmsMessage(turnDto.getClientId(), u);
            return new ResponseEntity<>("TURN IS RESERVED and it needs to start " + turn.getEstimatedTimeToStart() +
                    " and time picked is " + nowTime + "picked at  "
                    + nowTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                    "\n number of empty windows" + GradutionProApplication.numberofEmptyWindows +
                    " at window " + GradutionProApplication.turns.get(GradutionProApplication.turns.size() - 1).getWindowSelected() +
                    "and the whole process will take" + turn.getEstimatedTime() + " Service Time" + serviceTime, HttpStatus.OK);

        } else if (GradutionProApplication.numberOfTurnsInTheQueue == 100) {
            String X = "NO EMPTY PLACES";
            return new ResponseEntity<>(X, HttpStatus.OK);
        } else if (clientExist == 1) {
            String X = "YOU ALREADY HAVE A TURN ";
            return new ResponseEntity<>(X, HttpStatus.BAD_REQUEST);
        } else if (clientExist == -1) {
            String X = "YOU ARE NOT REGISTERD ";
            return new ResponseEntity<>(X, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // function to calculate time of the service
    public long getServiceTime(Service x) {

        // int time =0;
        //for (int i =0; i< x.size()){
        if (x == Service.service1) {
            //time +=15;
            return 15;
        } else if (x == Service.service2) {
            //time +=20;

            return 20;

        } else if (x == Service.service3) {
            //time +=25;

            return 25;
        } else if (x == Service.service4) {
            //time +=30;

            return 30;


        }
        return 0;
        //}
        // return time;
    }

    // function to check if the client have an active turn or not
    public long checkClient(long id) {
        long x = 0;
        User user = userRep.findById(id);
        if (user == null) {
            return -1;
        } else {
            for (int i = 0; i < (GradutionProApplication.turns.size()); i++) {

                if ((GradutionProApplication.turns.get(i).getClientId()) == id) {
                    x = 1;
                    return x;
                }
            }
        }
        return 0;
    }

    // function to calcuate the least time in the queue
    public Long getLeastTime() {
        long remaining, passed;
        long time;
        long x = 0;
        long empty = 0;
        int i = GradutionProApplication.turns.size() - 3;
        int size = GradutionProApplication.turns.size();
        time = Math.abs(Duration.between(LocalTime.now(), GradutionProApplication.turns.get(i).getTime()).toMinutes());
        remaining = GradutionProApplication.turns.get(i).getEstimatedTime() - time;

        long min = remaining;
        System.out.println("min for the first time is" + min);
        int index = 0;


        for (int j = i; j < size; j++) {
            System.out.println("min is" + min);

            System.out.println("j is " + j);
            time = Math.abs(Duration.between(LocalTime.now(), GradutionProApplication.turns.get(j).getTime()).toMinutes());
            System.out.println("time is " + time);
            if (time <= GradutionProApplication.turns.get(j).getEstimatedTime()) {
                remaining = GradutionProApplication.turns.get(j).getEstimatedTime() - time;
                System.out.println("remaning is  " + remaining);
                if (remaining < min) {
                    min = remaining;
                    index = j;
                }

            } else {
                activeTurnRep.delete(GradutionProApplication.turns.get(j));
                GradutionProApplication.turns.remove(GradutionProApplication.turns.get(j));
            }

        }
        GradutionProApplication.window = GradutionProApplication.turns.get(index).getWindowSelected();
        return min;

    }

    @GetMapping(path = "/getAllTurnTime")
    public ResponseEntity<Map<String, String>> getRemaningTimeAndPassedForAllTurns() {

        long remaining, passed;
        long time;
        String state = null;
        String client = null;
        HashMap<String, String> times = new HashMap<String, String>();
        for (int i = 0; i < GradutionProApplication.turns.size(); i++) {

            time = Math.abs(Duration.between(LocalTime.now(), GradutionProApplication.turns.get(i).getTime()).toMinutes());

            if (time <= GradutionProApplication.turns.get(i).getEstimatedTime()) {
                passed = time;
                remaining = GradutionProApplication.turns.get(i).getEstimatedTime() - time;
                state = " his turn number is" + i + "time passed is " + passed + "remaning time is " + remaining;
                client = "the client" + GradutionProApplication.turns.get(i).getClientId();
            } else {
                activeTurnRep.delete(GradutionProApplication.turns.get(i));
                GradutionProApplication.turns.remove(GradutionProApplication.turns.get(i));
            }

            times.put(client, state);

        }
        if (times.isEmpty()) {
            times.put("empty ", "no active turns ");
            return new ResponseEntity<>(times, HttpStatus.OK);
        } else
            return new ResponseEntity<>(times, HttpStatus.OK);

    }

@PutMapping(path = "/cancelTurn")
    public ResponseEntity<String> cancelTurn(@RequestBody TurnDto id) {
System.out.println(id.getClientId());
        ActiveTurns x = activeTurnRep.findByClientId(id.getClientId());
        Turn y = turnRep.findByClientId(id.getClientId());

        if (x == null) {
            return new ResponseEntity<>("fsh turn ya 5fef eldm", HttpStatus.NOT_FOUND);

        } else {
            long time;
            time = Math.abs(Duration.between(LocalTime.now(), x.getTime()).toMinutes());


            x.setStatus(Status.canceled);
            y.setStatus(Status.canceled);
            activeTurnRep.save(x);
            turnRep.save(y);
            initArrayList();
            return new ResponseEntity<>("canceled ya mo7trm", HttpStatus.OK);


        }

    }

    //function to get the history of turns of the client
    @GetMapping("/getMyTurns/{clientId}")
    public ResponseEntity<Turn> getMyTurns(@PathVariable long clientId) {

        return new ResponseEntity<>(turnRep.findByClientId(clientId), HttpStatus.OK);

    }

    // function to send mails
    public void sendEmail(long clientId, String text) {
        User user = userRep.findById(clientId);

        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("queue@amr.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Your turn");
        mailMessage.setText(text);

        // Send mail
        mailSender.send(mailMessage);

    }

    public void sendSmsMessage  (long clientId, String text) {
        User user = userRep.findById(clientId);
        String x = "+970" + user.getPhoneNumber();
        SmsRequest smsRequest = new SmsRequest(x, text);
        smsSender.sendSms(smsRequest);
    }

    // function to initilize the array list
    public void initArrayList() {
        GradutionProApplication.turns = activeTurnRep.findAll();

        long remaining, passed;
        long time;
        String state = null;
        String client = null;
        for (int i = 0; i < GradutionProApplication.turns.size(); i++) {

            time = Math.abs(Duration.between(LocalTime.now(), GradutionProApplication.turns.get(i).getTime()).toMinutes());

            if (time <= GradutionProApplication.turns.get(i).getEstimatedTime()) {
                passed = time;
                remaining = GradutionProApplication.turns.get(i).getEstimatedTime() - time;
                state = " his turn number is" + i + "time passed is " + passed + "remaning time is " + remaining;
                client = "the client" + GradutionProApplication.turns.get(i).getClientId();
            } else {
                activeTurnRep.delete(GradutionProApplication.turns.get(i));
                GradutionProApplication.turns.remove(GradutionProApplication.turns.get(i));
            }


            GradutionProApplication.turns = activeTurnRep.findAll();
        }

    }

    @GetMapping("/getAllTurns")
    public ResponseEntity<List<Turn>> getAllTurns() {
        return new ResponseEntity<>(turnRep.findAll(), HttpStatus.OK);

    }

    public ActiveTurns getCancelledTurn() {
        ActiveTurns cancelled = new ActiveTurns();
        Turn x =new Turn();
        for (int i = 0; i < GradutionProApplication.turns.size(); i++) {
            if (GradutionProApplication.turns.get(i).getStatus() == Status.canceled) {

                cancelled = GradutionProApplication.turns.get(i);
                GradutionProApplication.turns.get(i).setStatus(Status.reused);
                activeTurnRep.save(GradutionProApplication.turns.get(i));
                x=turnRep.findByClientId(GradutionProApplication.turns.get(i).getClientId());
                System.out.println("client id issssssss "+x.getClientId());
                x.setStatus(Status.reused);
                turnRep.save(x);
                break;
            }

            cancelled = null;
        }
        return cancelled;
    }


}
