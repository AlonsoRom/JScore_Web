package com.JScore.model.gestor;

import com.JScore.model.example.LoggerManager;
import com.JScore.model.torneo.Torneo;
import com.JScore.model.user.Judoka;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorTorneo {

    private static final Logger logger = LoggerManager.getLogger(GestorTorneo.class);
    private List<Torneo> torneos;
    private GestorJudoka gestorJudoka;

    public GestorTorneo(GestorJudoka gestorJudoka) {
        this.gestorJudoka = gestorJudoka;
        this.torneos = new ArrayList<>();
    }

    public void agregarTorneoDesdeConsola(Scanner scanner) {
        try {
            logger.log(Level.INFO, "Nombre del Torneo: ");
            String nombre = scanner.nextLine();
            logger.log(Level.INFO, "Fecha del Torneo (YYYY-MM-DD): ");
            String fecha = scanner.nextLine();

            logger.log(Level.INFO, "Ingrese los nombres de los participantes (separados por comas): ");
            String participantesInput = scanner.nextLine();
            List<String> nombresParticipantes = Arrays.asList(participantesInput.split(","));
            List<Judoka> participantes = new ArrayList<>();

            for (String nombreParticipante : nombresParticipantes) {
                try {
                    Judoka judoka = gestorJudoka.obtenerJudoka(nombreParticipante.trim());
                    participantes.add(judoka);
                } catch (IllegalArgumentException e) {
                    logger.log(Level.WARNING, "Judoka no encontrado: {0}", nombreParticipante.trim());
                }
            }

            if (participantes.isEmpty()) {
                logger.log(Level.WARNING, "No se agregaron participantes válidos, torneo no creado.");
                return;
            }

            Torneo torneo = new Torneo(nombre, fecha, participantes);
            torneos.add(torneo);
            logger.log(Level.INFO, "Torneo agregado con éxito.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al agregar torneo: {0}", e);
        }
    }

    public void registrarGanadorDesdeConsola(Scanner scanner) {
        logger.log(Level.INFO, "Nombre del Torneo: ");
        String nombreTorneo = scanner.nextLine();
        logger.log(Level.INFO, "Nombre del Ganador: ");
        String nombreGanador = scanner.nextLine();

        for (Torneo torneo : torneos) {
            if (torneo.getNombre().equalsIgnoreCase(nombreTorneo)) {
                torneo.registrarGanador(nombreGanador);
                logger.log(Level.INFO, "Ganador registrado");
                return;
            }
        }

        logger.log(Level.WARNING, "Torneo no encontrado");
    }

    public List<Torneo> getTorneos() {
        return torneos;
    }
}
