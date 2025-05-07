package com.JScore.demo.model.torneo;

import com.JScore.demo.model.example.LoggerManager;
import com.JScore.demo.model.user.Judoka;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String fecha;
    @ManyToMany
    @JoinTable(
            name = "competencia_judoka",
            joinColumns = @JoinColumn(name = "torneo_id"),
            inverseJoinColumns = @JoinColumn(name = "judoka_id")
    )
    private List<Judoka> participantes;

    @ManyToOne
    @JoinColumn(name = "ganador_id")
    @JsonBackReference // Sirve para que no sea infinito
    private Judoka ganador;

    private static final Logger logger = LoggerManager.getLogger(Torneo.class);
    public Torneo(String nombre, String fecha, List<Judoka> participantes) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.participantes = participantes;
    }

    public void registrarGanador(String nombreJudoka) {
        for (Judoka judoka : participantes) {
            if (judoka.getNombre().equalsIgnoreCase(nombreJudoka)) {
                this.ganador = judoka;
                return;
            }
        }
        logger.log(Level.INFO,"El judoka no esta inscrito en este Torneo");
    }
}