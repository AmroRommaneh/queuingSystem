package com.amr.gradutionPro.gradutionPro.Controller;

import com.amr.gradutionPro.gradutionPro.*;
import com.amr.gradutionPro.gradutionPro.DTO.TurnDto;
import com.amr.gradutionPro.gradutionPro.DTO.UserDto;
import com.amr.gradutionPro.gradutionPro.Model.ActiveTurns;
import com.amr.gradutionPro.gradutionPro.Model.Turn;
import com.amr.gradutionPro.gradutionPro.Model.User;
import com.amr.gradutionPro.gradutionPro.Repositry.ActiveTurnRep;
import com.amr.gradutionPro.gradutionPro.Repositry.TurnRep;
import com.amr.gradutionPro.gradutionPro.Repositry.UserRep;
import com.amr.gradutionPro.gradutionPro.Services.TurnServices;
import com.amr.gradutionPro.gradutionPro.Services.UserSerivces;
import com.amr.gradutionPro.gradutionPro.Sms.SmsRequest;
import com.amr.gradutionPro.gradutionPro.Sms.SmsSender;
import com.amr.gradutionPro.gradutionPro.exceptions.Myexeption;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import java.util.Map;

@Controller
@RequestMapping("api/User")
public class UserCont {
    private EmailCfg emailCfg;
    SmsSender smsSender;

    @Autowired
    UserSerivces userSerivces;
    @Autowired
    UserRep userRep;
    @Autowired
    TurnServices turnServices;
    @Autowired
    TurnRep turnRep;
    @Autowired
    ActiveTurnRep activeTurnRep;
    @Autowired
    TurnCont turnCont;
    String email;
    Role client;

    public UserCont(EmailCfg emailCfg, SmsSender smsSender) {
        this.emailCfg = emailCfg;
        this.smsSender = smsSender;
    }

    // function to register client "sign up "
    @PostMapping(path = "/registerClient")
    public ResponseEntity<Map<String, String>> registerClient(@RequestBody UserDto userDto) {
        System.out.println("reached1");
        User user = new User();

        user.setRole(Role.client);
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());


        userSerivces.registerUser(user);
        sendSmsMessage(userDto.getPhoneNumber(),"registerd ya baby");

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    //function to generate tokens
    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("clientId", user.getId())
                .claim("email", user.getEmail())
                .claim("Name", user.getName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
    // function to log in client

    @PostMapping("/loginClient")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> clientMap) {
        String email = (String) clientMap.get("email");
        String password = (String) clientMap.get("password");
        User user = userSerivces.validateUser(email, password);
        System.out.println(email);
        System.out.println(password);
        if (user == null)
            throw new Myexeption("No user exsit");

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @RequestMapping(path = "/rt", method = RequestMethod.POST)
    public ResponseEntity<String> test(@RequestBody String x) {
        System.out.println("reached 1");
        return new ResponseEntity<>("okkkkkkkkk", HttpStatus.OK);
    }

    // function to update user information

    @PutMapping("/Update")
    public User updateUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        user.setId(userDto.getId());
        return userSerivces.updateUser(user);

    }

    // function to add additional time for specifec turn and notify other clients have turns on that window
    @PutMapping("/ServiceProviderUpadteTime")
    public ResponseEntity<String> updateTurn(@RequestBody TurnDto turnDto) {
        ActiveTurns activeTurns = activeTurnRep.findByClientId(turnDto.getClientId());

        String x = turnServices.updateTurn(turnDto.getClientId(), turnDto.getAdditonalTime());
        Turn turn = turnRep.findByClientId(turnDto.getClientId());
        activeTurns.setEstimatedTime(turn.getEstimatedTime());
        activeTurnRep.save(activeTurns);
//notify other users
        return new ResponseEntity<>("new time for service is " + (turn.getEstimatedTime() - turn.getEstimatedTimeToStart()),
                HttpStatus.OK);

    }

    @GetMapping("/getClients")
    public ResponseEntity<ArrayList<User>> getAllClients() {

        return new ResponseEntity<>(userRep.findByRole(Role.client), HttpStatus.OK);

    }

    @GetMapping("/getNumberOfActiveTurns")
    public ResponseEntity<Integer> getNumberOfActiveTurns() {
        turnCont.initArrayList();
        return new ResponseEntity<>(GradutionProApplication.turns.size(), HttpStatus.OK);

    }


    @GetMapping("/getDiscription")
    public ResponseEntity<String> getDiscription(@RequestBody TurnDto turnDto) {

        System.out.println("service is "+turnDto.getSrvice());


System.out.println("fuck");
        if (turnDto.getSrvice() == Service.service1) {
            System.out.println("desss "+descriptions.d1.name());
            return new ResponseEntity<>(descriptions.d1.name(), HttpStatus.OK);
        } else if (turnDto.getSrvice() == Service.service2) {

            return new ResponseEntity<>(descriptions.d2.name(), HttpStatus.OK);

        } else if (turnDto.getSrvice() == Service.service3) {

            return new ResponseEntity<>(descriptions.d3.name(), HttpStatus.OK);
        } else if (turnDto.getSrvice() == Service.service4) {

            return new ResponseEntity<>(descriptions.d4.name(), HttpStatus.OK);
        }
        return null;
    }

    public void sendSmsMessage(long phone, String text) {
        String x = "+970" + phone;
        SmsRequest smsRequest = new SmsRequest(x, text);
        smsSender.sendSms(smsRequest);
    }

    @GetMapping("/getContactInformation")
    public ResponseEntity<String> getContactInformation (){

        String x ="Our address is "+Constants.Address+
                "\n Contact us at "+Constants.Email+
                "\n phone Number" +Constants.phoneNumber;

        return new ResponseEntity<>(x,HttpStatus.OK);


    }

}
