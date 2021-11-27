package com.amr.gradutionPro.gradutionPro.Repositry;

import com.amr.gradutionPro.gradutionPro.Model.User;
import com.amr.gradutionPro.gradutionPro.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRep extends JpaRepository<User, Long> {



    // User findByEmailAndPassword(String email, String password);
    User findByEmailAndPassword(String email,String password);

     User findByEmail(String email);

     User findById(long id) ;

    ArrayList<User> findByRole(Role role);
}
