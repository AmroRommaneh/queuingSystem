package com.amr.gradutionPro.gradutionPro.Services;

import com.amr.gradutionPro.gradutionPro.GradutionProApplication;
import com.amr.gradutionPro.gradutionPro.Model.Turn;
import com.amr.gradutionPro.gradutionPro.Model.User;
import com.amr.gradutionPro.gradutionPro.Repositry.TurnRep;
import com.amr.gradutionPro.gradutionPro.exceptions.Myexeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnServices {

    @Autowired
    TurnRep turnRep;

    public void saveTurn(Turn turn) {

        turnRep.save(turn);
        GradutionProApplication.numberOfTurnsInTheQueue++;

    }

    public String updateTurn(long id,int add) {
        Turn turn2 =  turnRep.findByClientId(id);
        System.out.println(turn2.getClientId());
        System.out.println(id);

        if(turn2 == null){

            throw new Myexeption("Turn Not Found");

        }else {

           turn2.setEstimatedTime(turn2.getEstimatedTime()+add);
        }
        turnRep.save(turn2);
        return "turn updated";
    }
}
