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
                utilisationALabel.setText("Utilisation A: " + String.format("%.4f", mySim.getUtilisationA().mean()));
                utilisationAIntervalLabel.setText("CI: " + mySim.getUtilisationA().confidenceInterval());

                utilisationBLabel.setText("Utilisation B: " + String.format("%.4f", mySim.getUtilisationB().mean()));
                utilisationBIntervalLabel.setText("CI: " + mySim.getUtilisationB().confidenceInterval());

                utilisationCLabel.setText("Utilisation C: " + String.format("%.4f", mySim.getUtilisationC().mean()));
                utilisationCIntervalLabel.setText("CI: " + mySim.getUtilisationC().confidenceInterval());

                utilisationAllLabel.setText("Utilisation All: " + String.format("%.4f", mySim.getUtilisationAll().mean()));
                utilisationAllIntervalLabel.setText("CI: " + mySim.getUtilisationAll().confidenceInterval());

                finishedOrdersLabel.setText("Finished Orders: " + String.format("%.4f", mySim.getFinishedOrdersAverage().mean()));
                allOrdersLabel.setText("All Orders: " + String.format("%.4f", mySim.getAllOrdersAverage().mean()));

                cuttingQLLabel.setText("Cutting QL : " + String.format("%.4f", mySim.getCuttingQueueLengthAverage().mean()));
                stainingQLLabel.setText("Staining QL : " + String.format("%.4f", mySim.getStainingQueueLengthAverage().mean()));
                assemblyQLLabel.setText("Assembly QL : " + String.format("%.4f", mySim.getAssemblyQueueLengthAverage().mean()));
                montageQLLabel.setText("Montage QL : " + String.format("%.4f", mySim.getMontageQueueLengthAverage().mean()));

                ArrayList<Average> workersAUtilisationAverage = new ArrayList<>(mySim.getWorkersAUtilisationAverage());
                ArrayList<Average> workersBUtilisationAverage = new ArrayList<>(mySim.getWorkersBUtilisationAverage());
                ArrayList<Average> workersCUtilisationAverage = new ArrayList<>(mySim.getWorkersCUtilisationAverage());

                if(((MySimulation) simulation).getActualRepCount() > 30) {
                    workerAverageUtilisationTableModel.setNewData(workersAUtilisationAverage, workersBUtilisationAverage, workersCUtilisationAverage);
                }

                double seconds = mySim.getTimeOfWorkAverage().mean();
                double hours = seconds / 3600.0;
                mySim.getTimeOfWorkAverage().confidenceInterval();
                if(mySim.getActualRepCount() > 30) {
                    double lowerS = mySim.getTimeOfWorkAverage().getLowerBound();
                    double upperS = mySim.getTimeOfWorkAverage().getUpperBound();
                    double lowerH = lowerS / 3600.0;
                    double upperH = upperS / 3600.0;
                    orderIntervalLabel.setText("CI: " +
                            String.format("[ %.4f ", lowerS) + "; " + String.format("%.4f ]", upperS) + "        " +
                            String.format("[ %.4f ", lowerH) + "; " + String.format("%.4f ]", upperH) + " h");

                }

                orderTimeLabel.setText("Average time of Work: " + String.format("%.4f s", seconds) + "        " +  String.format("%.4f", hours) + " h");

            });
        }

    }
}
