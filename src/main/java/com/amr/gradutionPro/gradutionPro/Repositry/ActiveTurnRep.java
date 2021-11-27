package com.amr.gradutionPro.gradutionPro.Repositry;

import com.amr.gradutionPro.gradutionPro.Model.ActiveTurns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface ActiveTurnRep extends JpaRepository<ActiveTurns,Long> {
    ArrayList<ActiveTurns> findAll();

    ActiveTurns findByClientId(long clientId);
}
