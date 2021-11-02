package com.amr.gradutionPro.gradutionPro.Controller;

import com.amr.gradutionPro.gradutionPro.DTO.TurnDto;
import com.amr.gradutionPro.gradutionPro.GradutionProApplication;
import com.amr.gradutionPro.gradutionPro.Model.Turn;
import com.amr.gradutionPro.gradutionPro.Repositry.TurnRep;
import com.amr.gradutionPro.gradutionPro.Services.TurnServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.amr.gradutionPro.gradutionPro.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/Turn")
public class TurnCont {

    @Autowired
    TurnServices turnServices;
    @Autowired
    TurnRep turnRep;

    @PostMapping(path = "/pickTurn")
    public ResponseEntity<String> creatTurn(@RequestBody TurnDto turnDto) {
        System.out.println("reached1");
        long estimatedTime = 0;
//if else statments
        long clientExist = checkClient(turnDto.getClientId());
        LocalTime nowTime = LocalTime.now();
        LocalDate nowDate = LocalDate.now();
        Turn turn = new Turn();
        Long leastTime;
        long serviceTime = getServiceTime(turnDto.getSrvice());

        if (GradutionProApplication.turns.size() <= 100 && clientExist == 0) {
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


            GradutionProApplication.QueueTime = (int)estimatedTime;
            turn.setClientId(turnDto.getClientId());
            turn.setEstimatedTime((int)estimatedTime);
            turn.setService(turnDto.getSrvice());
            turn.setTime(nowTime);
            turn.setDate(nowDate);
            GradutionProApplication.turns.add(turn);
            int timeToStart=(int)estimatedTime-(int)serviceTime;

turn.setEstimatedTimeToStart(timeToStart);
            turnServices.saveTurn(turn);
            return new ResponseEntity<>("TURN IS RESERVED and it needs to start " + turn.getEstimatedTimeToStart() + " and time picked is " + nowTime + "picked at  "
                    + nowTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n number of empty windows" + GradutionProApplication.numberofEmptyWindows + " at window " + GradutionProApplication.turns.get(GradutionProApplication.turns.size() - 1).getWindowSelected()+"and the whole process will take"+turn.getEstimatedTime()+" Service Time"+serviceTime, HttpStatus.OK);

        } else if (GradutionProApplication.numberOfTurnsInTheQueue == 100) {
            String X = "NO EMPTY PLACES";
            return new ResponseEntity<>(X, HttpStatus.OK);
        } else if (clientExist == 1) {
            String X = "YOU ALREADY HAVE A TURN YA 5RAA";
            return new ResponseEntity<>(X, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
public long getServiceTime(Service x){
        if(x == Service.eee)
            return 15;
        else
        if(x == Service.fff)
            return 20;

        else
        if(x == Service.sss)
            return 25;

        else
        if(x == Service.qqq)
            return 20;

        return 0;
}
    public long checkClient(long id) {
        long x = 0;

        for (int i = 0; i < (GradutionProApplication.turns.size()); i++) {

            if ((GradutionProApplication.turns.get(i).getClientId()) == id) {
                x = 1;
                return x;
            }
        }
        return 0;
    }

    public Long getLeastTime() {
        long remaining,passed;
        long time;
        long x = 0;
        long empty = 0;
        int i=GradutionProApplication.turns.size()-3;
        int size=GradutionProApplication.turns.size();
        time=Math.abs(Duration.between(LocalTime.now(),GradutionProApplication.turns.get(i).getTime()).toMinutes());
        remaining = GradutionProApplication.turns.get(i).getEstimatedTime() - time;

        long min =remaining;
        System.out.println("min for the first time is"+min);
        int index = 0;


        for (int j=i;j<size ; j++) {
            System.out.println("min is" + min);

            System.out.println("j is "+j);
            time = Math.abs(Duration.between(LocalTime.now(), GradutionProApplication.turns.get(j).getTime()).toMinutes());
System.out.println("time is "+time);
            if (time <= GradutionProApplication.turns.get(j).getEstimatedTime()) {
                remaining = GradutionProApplication.turns.get(j).getEstimatedTime() - time;
                System.out.println("remaning is  "+ remaining);
                if(remaining < min  ){
                    min=remaining;
                    index=j;
                }

            }
            else {

                GradutionProApplication.turns.remove(GradutionProApplication.turns.get(j));
            }

        }
        GradutionProApplication.window=GradutionProApplication.turns.get(index).getWindowSelected();
        return min;

    }
@GetMapping(path = "/getAllTurnTime")
        public ResponseEntity<Map<String,String>> getRemaningTimeAndPassedForAllTurns() {

        long remaining,passed;
        long time;
String state = null;
String client = null;
        HashMap<String, String> times = new HashMap<String, String>();
        for (int i=0;i<GradutionProApplication.turns.size();i++){

            time=Math.abs(Duration.between(LocalTime.now(),GradutionProApplication.turns.get(i).getTime()).toMinutes());

            if (time <= GradutionProApplication.turns.get(i).getEstimatedTime()){
                passed=time;
                remaining=GradutionProApplication.turns.get(i).getEstimatedTime()-time;
                state=" his turn number is"+i+"time passed is "+passed +"remaning time is "+remaining;
                client="the client"+GradutionProApplication.turns.get(i).getClientId();
            }else {
                GradutionProApplication.turns.remove(GradutionProApplication.turns.get(i));
            }

            times.put(client,state);

        }

return new ResponseEntity<>(times,HttpStatus.OK);

        }


        public ResponseEntity<String> cancelTurn(@PathVariable long id){

        Turn x=turnRep.getById(id);

        if(x==null){
            return new ResponseEntity<>("fsh turn ya 5fef eldm",HttpStatus.OK);

        }else {
            long time;
            time = Duration.between(LocalTime.now(), x.getTime()).toMinutes();

            if (time <= 15) {

                turnRep.delete(x);

                return new ResponseEntity<>("deleted ya mo7trm", HttpStatus.OK);

            } else {


            }
        }


            return new ResponseEntity<>("deleted ya mo7trm",HttpStatus.OK);

        }


    }
