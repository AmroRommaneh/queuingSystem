package com.amr.gradutionPro.gradutionPro.Controller;

import com.amr.gradutionPro.gradutionPro.Constants;
import com.amr.gradutionPro.gradutionPro.DTO.UserDto;
import com.amr.gradutionPro.gradutionPro.Model.User;
import com.amr.gradutionPro.gradutionPro.Repositry.UserRep;
import com.amr.gradutionPro.gradutionPro.Role;
import com.amr.gradutionPro.gradutionPro.Services.UserSerivces;
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
import java.util.List;
import java.util.Map;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/User")
public class UserCont {
    @Autowired
    UserSerivces userSerivces;
    @Autowired
    UserRep userRep;
    String email;
    Role client;
    @PostMapping(path = "/registerClient")
    public ResponseEntity<Map<String, String>> registerClient(@RequestBody UserDto userDto)  {
        System.out.println("reached1");
        User user = new User();

        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());


       userSerivces.registerUser(user);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

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

    @PostMapping("/loginClient")
    public ResponseEntity<Map<String, String>> loginUser (@RequestBody Map<String, Object> clientMap) {
        String email = (String) clientMap.get("email");
        String password = (String) clientMap.get("password");
        User user = userSerivces.validateUser(email, password);
        System.out.println(email);
        System.out.println(password);
        if (user == null)
            throw new Myexeption("No user exsit");

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @RequestMapping(path = "/rt",method =  RequestMethod.POST)
    public ResponseEntity<String> test(@RequestBody String x ){
        System.out.println("reached 1");
        return new ResponseEntity<>("okkkkkkkkk", HttpStatus.OK);
    }

    @PutMapping("/Update")
    public User updateUser(@RequestBody UserDto userDto) {
        User user  = new User();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        user.setId(userDto.getId());
        return userSerivces.updateUser(user);

    }

}
