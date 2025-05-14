package delegates;

import GUI.Models.WorkerAverageUtilisationTableModel;
import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import Statistics.Average;
import simulation.MySimulation;

import javax.swing.*;
import java.util.ArrayList;

public class FastMLabelDelegate implements ISimDelegate {

    private final JLabel utilisationALabel;
    private final JLabel utilisationBLabel;
    private final JLabel utilisationCLabel;
    private final JLabel utilisationAllLabel;

    private final JLabel utilisationAIntervalLabel;
    private final JLabel utilisationBIntervalLabel;
    private final JLabel utilisationCIntervalLabel;
    private final JLabel utilisationAllIntervalLabel;

    private final JLabel finishedOrdersLabel;
    private final JLabel allOrdersLabel;
    private final JLabel cuttingQLLabel;
    private final JLabel stainingQLLabel;
    private final JLabel assemblyQLLabel;
    private final JLabel montageQLLabel;
    private final JLabel orderTimeLabel;
    private final JLabel orderIntervalLabel;

    private final WorkerAverageUtilisationTableModel workerAverageUtilisationTableModel;
    public FastMLabelDelegate(JLabel utilisationALabel,
                              JLabel utilisationBLabel,
                              JLabel utilisationCLabel,
                              JLabel utilisationAllLabel,
                              JLabel utilisationAIntervalLabel,
                              JLabel utilisationBIntervalLabel,
                              JLabel utilisationCIntervalLabel,
                              JLabel utilisationAllIntervalLabel,
                              JLabel finishedOrdersLabel,
                              JLabel allOrdersLabel,
                              JLabel cuttingQLLabel,
                              JLabel stainingQLLabel,
                              JLabel assemblyQLLabel,
                              JLabel montageQLLabel,
                              WorkerAverageUtilisationTableModel workerAverageUtilisationTableModel,
                              JLabel orderTimeLabel,
                              JLabel orderIntervalLabel
                              ) {
        this.utilisationALabel = utilisationALabel;
        this.utilisationBLabel = utilisationBLabel;
        this.utilisationCLabel = utilisationCLabel;
        this.utilisationAllLabel = utilisationAllLabel;
        this.utilisationAIntervalLabel = utilisationAIntervalLabel;
        this.utilisationBIntervalLabel = utilisationBIntervalLabel;
        this.utilisationCIntervalLabel = utilisationCIntervalLabel;
        this.utilisationAllIntervalLabel = utilisationAllIntervalLabel;
        this.allOrdersLabel = allOrdersLabel;
        this.finishedOrdersLabel = finishedOrdersLabel;
        this.cuttingQLLabel = cuttingQLLabel;
        this.stainingQLLabel = stainingQLLabel;
        this.assemblyQLLabel = assemblyQLLabel;
        this.montageQLLabel = montageQLLabel;
        this.workerAverageUtilisationTableModel = workerAverageUtilisationTableModel;
        this.orderTimeLabel = orderTimeLabel;
        this.orderIntervalLabel = orderIntervalLabel;

    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {
    }

    @Override
    public void refresh(Simulation simulation) {
        MySimulation mySim = (MySimulation) simulation;

        if(!mySim.isSlowMode()) {
            SwingUtilities.invokeLater(() -> {
                int repCount = mySim.getActualRepCount();

                setUtilisation(utilisationALabel, utilisationAIntervalLabel, mySim.getUtilisationA(), repCount, "Utilisation A");
                setUtilisation(utilisationBLabel, utilisationBIntervalLabel, mySim.getUtilisationB(), repCount, "Utilisation B");
                setUtilisation(utilisationCLabel, utilisationCIntervalLabel, mySim.getUtilisationC(), repCount, "Utilisation C");
                setUtilisation(utilisationAllLabel, utilisationAllIntervalLabel, mySim.getUtilisationAll(), repCount, "Utilisation All");

                updateStatWithCI(finishedOrdersLabel, mySim.getFinishedOrdersAverage(), repCount, "Finished Orders");
                updateStatWithCI(allOrdersLabel, mySim.getAllOrdersAverage(), repCount, "All Orders");
                updateStatWithCI(cuttingQLLabel, mySim.getCuttingQueueLengthAverage(), repCount, "Cutting QL");
                updateStatWithCI(stainingQLLabel, mySim.getStainingQueueLengthAverage(), repCount, "Staining QL");
                updateStatWithCI(assemblyQLLabel, mySim.getAssemblyQueueLengthAverage(), repCount, "Assembly QL");
                updateStatWithCI(montageQLLabel, mySim.getMontageQueueLengthAverage(), repCount, "Montage QL");

                if (repCount > 30) {
                    workerAverageUtilisationTableModel.setNewData(
                            new ArrayList<>(mySim.getWorkersAUtilisationAverage()),
                            new ArrayList<>(mySim.getWorkersBUtilisationAverage()),
                            new ArrayList<>(mySim.getWorkersCUtilisationAverage())
                    );
                }

                double seconds = mySim.getTimeOfWorkAverage().mean();
                double hours = seconds / 3600.0;
                orderTimeLabel.setText("Average time of Work: " + String.format("%.4f s", seconds) + "        " + String.format("%.4f", hours) + " h");

                if (repCount > 30) {
                    Average avg = mySim.getTimeOfWorkAverage();
                    avg.confidenceInterval();
                    orderIntervalLabel.setText(String.format(
                            "CI: [ %.4f ; %.4f ]        [ %.4f ; %.4f ] h",
                            avg.getLowerBound(), avg.getUpperBound(),
                            avg.getLowerBound() / 3600.0, avg.getUpperBound() / 3600.0
                    ));
                } else {
                    orderIntervalLabel.setText("CI: (nedostupné pred 30. replikáciou)");
                }
            });

        }

    }
    private void setUtilisation(JLabel meanLabel, JLabel ciLabel, Average avg, int repCount, String label) {
        meanLabel.setText(label + ": " + String.format("%.4f", avg.mean()));
        if (repCount > 30) {
            avg.confidenceInterval();
            ciLabel.setText("CI: " + String.format("[%.4f ; %.4f]", avg.getLowerBound(), avg.getUpperBound()));
        } else {
            ciLabel.setText("CI: (n/a)");
        }
    }

    private void updateStatWithCI(JLabel label, Average avg, int repCount, String title) {
        if (repCount > 30) {
            avg.confidenceInterval();
            label.setText(String.format("%s: %.4f [%.4f ; %.4f]", title, avg.mean(), avg.getLowerBound(), avg.getUpperBound()));
        } else {
            label.setText(String.format("%s: %.4f [CI: (n/a)]", title, avg.mean()));
        }
    }

}
