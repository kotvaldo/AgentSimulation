package org.example;

import Enums.PresetSimulationValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import simulation.MySimulation;
import Statistics.Average;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ConfigMain {

    public static void main(String[] args) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            InputStream input = ConfigMain.class.getClassLoader().getResourceAsStream("config.json");
            if (input == null) {
                throw new IllegalArgumentException("Súbor config.json nebol nájdený v resources/org/example/");
            }

            int repCount = 0;

            ConfigData config = mapper.readValue(input, ConfigData.class);

            MySimulation sim = new MySimulation();
            sim.setCountWorkerA(config.workersA);
            sim.setCountWorkerB(config.workersB);
            sim.setCountWorkerC(config.workersC);
            sim.setWorkPlacesCount(config.workplaces);
            sim.setReplicationsCount(config.replications);
            sim.setBurnInCount(config.burnIn);
            sim.onReplicationDidFinish(s -> {
                Average avg = sim.getTimeOfWorkAverage();
                avg.confidenceInterval();

                double mean = avg.mean();
                double lower = avg.getLowerBound();
                double upper = avg.getUpperBound();

                double margin = (upper - lower) / 2.0;
                if (margin < 0.02 * mean && sim.getActualRepCount() >= config.burnIn) {
                    System.out.println("Dosiahnutá presnosť: ukončujem simuláciu po " + sim.getActualRepCount() + " replikáciách.");
                    sim.stopSimulation();
                }
                System.out.println("Replikácia : " + sim.getActualRepCount());

            });

            sim.simulate(config.replications, PresetSimulationValues.END_OF_SIMULATION.asDouble());

            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            saveResults(sim, "vystup_" + timestamp + ".txt");

        } catch (IOException e) {
            System.err.println("Chyba pri načítaní alebo spracovaní: " + e.getMessage());
        }
    }

    private static void saveResults(MySimulation sim, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Metric;Mean;CI_Lower;CI_Upper\n");

            writeAverage(writer, "Utilisation A", sim.getUtilisationA());
            writeAverage(writer, "Utilisation B", sim.getUtilisationB());
            writeAverage(writer, "Utilisation C", sim.getUtilisationC());
            writeAverage(writer, "Utilisation All", sim.getUtilisationAll());
            writeAverage(writer, "Finished Orders", sim.getFinishedOrdersAverage());
            writeAverage(writer, "All Orders", sim.getAllOrdersAverage());
            writeAverage(writer, "Cutting QL", sim.getCuttingQueueLengthAverage());
            writeAverage(writer, "Staining QL", sim.getStainingQueueLengthAverage());
            writeAverage(writer, "Assembly QL", sim.getAssemblyQueueLengthAverage());
            writeAverage(writer, "Montage QL", sim.getMontageQueueLengthAverage());
            writeAverage(writer, "Average Time of Work(s)", sim.getTimeOfWorkAverage());
            writeAverageHours(writer, "Average Time of Work(s)", sim.getTimeOfWorkAverage());
            System.out.println("Výsledky boli uložené do " + filename);
        } catch (IOException e) {
            System.err.println("Chyba pri zápise výsledkov: " + e.getMessage());
        }
    }

    private static void writeAverage(FileWriter writer, String label, Average avg) throws IOException {
        avg.confidenceInterval();
        writer.write(String.format(
                "%s=>  %.4f;   [ %.4f ; %.4f ]\n",
                label,
                avg.mean(),
                avg.getLowerBound(),
                avg.getUpperBound()
        ));
    }

    private static void writeAverageHours(FileWriter writer, String label, Average avg) throws IOException {
        avg.confidenceInterval();
        writer.write(String.format(
                "%s=>  %.4f;   [ %.4f ; %.4f ]\n",
                label,
                avg.mean() / 3600,
                avg.getLowerBound() / 3600,
                avg.getUpperBound() / 3600
        ));
    }

    public static class ConfigData {
        public int workersA;
        public int workersB;
        public int workersC;
        public int workplaces;
        public int replications;
        public int burnIn;
    }
}
