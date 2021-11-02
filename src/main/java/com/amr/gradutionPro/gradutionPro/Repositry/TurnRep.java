package com.amr.gradutionPro.gradutionPro.Repositry;

import com.amr.gradutionPro.gradutionPro.Model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnRep extends JpaRepository<Turn,Long> {

    Turn findByClientId(long id);

}
