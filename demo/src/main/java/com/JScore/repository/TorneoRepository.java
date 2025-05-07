package com.JScore.repository;

import com.JScore.model.torneo.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    List<Torneo> findByNombre(String nombre);

}
