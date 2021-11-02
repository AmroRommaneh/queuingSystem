package com.amr.gradutionPro.gradutionPro.Services;

import com.amr.gradutionPro.gradutionPro.Model.User;
import com.amr.gradutionPro.gradutionPro.Repositry.UserRep;
import com.amr.gradutionPro.gradutionPro.exceptions.Myexeption;
import org.mindrot.jbcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserSerivces {

    @Autowired
    UserRep userRep;

    public User registerUser(User c)  {
        String email = null;
        System.out.println("reached 2");
        System.out.println("reached");

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(c.getEmail() != null) {
            email = c.getEmail().toLowerCase();
        }
        if(!pattern.matcher(email).matches())
            throw new Myexeption("Invalid email format");
        User count = userRep.findByEmail(email);
        if(count != null)
            throw new Myexeption("Email already in use");
        System.out.println("hehehehe");
        String hashedPassword = BCrypt.hashpw(c.getPassword(), BCrypt.gensalt(10));
        c.setPassword(hashedPassword);
        userRep.save(c);
        long x= c.getId();
        User user =userRep.findById(x);
        return user;
    }

    public User validateUser(String email, String password) throws Myexeption {
        if(email != null)
            email = email.toLowerCase();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        User user= userRep.findByEmail(email);
        System.out.println(user.getEmail()+"\n"+user.getName());
        System.out.println(user.getPassword()+"\n"+hashedPassword);
        if (BCrypt.checkpw(password, user.getPassword()))
        return user;
        else return null;
    }

    public User updateUser(User updatedUser) {
        User user =  userRep.findById(updatedUser.getId());
        if(user == null){
            throw new Myexeption("User Not Found");

        }else {

            if (updatedUser.getName() != null)
                user.setName(updatedUser.getName());
            if (updatedUser.getEmail() != null)
                user.setEmail(updatedUser.getEmail());
            if (updatedUser.getPhoneNumber() != 0)
                user.setPhoneNumber(updatedUser.getPhoneNumber());
            if (updatedUser.getName() != null)
                user.setName(updatedUser.getName());
        }
        return userRep.save(user);
    }

}
