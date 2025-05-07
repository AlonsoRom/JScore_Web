package com.JScore.model.ranking;



import com.JScore.model.example.LoggerManager;
import com.JScore.model.gestor.GestorJudoka;
import com.JScore.model.user.Judoka;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ranking {

    private static final Logger logger = LoggerManager.getLogger(Ranking.class);

    private GestorJudoka gestorJudokas;

    public Ranking(GestorJudoka gestorJudokass) {
        this.gestorJudokas = gestorJudokass;
    }

    public void mostrarEstadisticasDesdeConsola(Scanner scanner) {
        logger.log(Level.INFO, "Nombre del Judoka: ");
        String nombre = scanner.nextLine();

        Judoka judoka = gestorJudokas.obtenerJudoka(nombre);
        if (judoka != null) {
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, judoka.mostrarInformacion());
            }
        } else {
            logger.log(Level.INFO, "El judoka no está registrado.");
        }
    }

    public void calcularRanking() {
        List<Judoka> judokas = gestorJudokas.getListaJudokas();

        judokas.sort(Comparator.comparingInt(Judoka::getVictorias).reversed());

        logger.log(Level.INFO, "Ranking de judokas (por número de victorias):");
        for (Judoka a : judokas) {
            logger.log(Level.INFO, "{0} {1} - Victorias: {2}, Derrotas: {3}, Empates: {4}",
                    new Object[]{a.getNombre(), a.getApellido(), a.getVictorias(), a.getDerrotas(), a.getEmpates()});
        }
    }
}