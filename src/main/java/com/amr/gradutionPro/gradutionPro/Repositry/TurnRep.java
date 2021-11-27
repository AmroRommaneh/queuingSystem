package com.amr.gradutionPro.gradutionPro.Repositry;

import com.amr.gradutionPro.gradutionPro.Model.Turn;
import com.amr.gradutionPro.gradutionPro.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TurnRep extends JpaRepository<Turn,Long> {

    Turn findByClientId(long id);
    Turn findById(long id) ;

}
