package com.amr.gradutionPro.gradutionPro.Services;

import com.amr.gradutionPro.gradutionPro.GradutionProApplication;
import com.amr.gradutionPro.gradutionPro.Model.Turn;
import com.amr.gradutionPro.gradutionPro.Repositry.TurnRep;
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
}
