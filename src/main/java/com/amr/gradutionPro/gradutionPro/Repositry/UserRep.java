package com.amr.gradutionPro.gradutionPro.Repositry;

import com.amr.gradutionPro.gradutionPro.Model.Client;
import com.amr.gradutionPro.gradutionPro.Model.User;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep extends JpaRepository<User, Long> {



    // User findByEmailAndPassword(String email, String password);
    User findByEmailAndPassword(String email,String password);

     User findByEmail(String email);

     User findById(long id) ;

}
