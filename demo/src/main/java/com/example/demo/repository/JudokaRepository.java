package com.example.demo.repository;


import com.example.demo.model.user.Judoka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * The interface Judoka repository.
 */
public interface JudokaRepository extends JpaRepository<Judoka, Long> {

    /**
     * Find by nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<Judoka> findByNombre(String nombre);

    Optional<Judoka> findByUsername(String username);

}
