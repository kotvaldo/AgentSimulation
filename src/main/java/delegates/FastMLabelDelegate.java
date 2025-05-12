package delegates;

import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import simulation.MySimulation;

import javax.swing.*;

public class FastMLabelDelegate implements ISimDelegate {

    private final JLabel utilisationALabel;
    private final JLabel utilisationBLabel;
    private final JLabel utilisationCLabel;
    private final JLabel utilisationAllLabel;

    private final JLabel utilisationAIntervalLabel;
    private final JLabel utilisationBIntervalLabel;
    private final JLabel utilisationCIntervalLabel;
    private final JLabel utilisationAllIntervalLabel;

    public FastMLabelDelegate(JLabel utilisationALabel,
                              JLabel utilisationBLabel,
                              JLabel utilisationCLabel,
                              JLabel utilisationAllLabel,
                              JLabel utilisationAIntervalLabel,
                              JLabel utilisationBIntervalLabel,
                              JLabel utilisationCIntervalLabel,
                              JLabel utilisationAllIntervalLabel) {
        this.utilisationALabel = utilisationALabel;
        this.utilisationBLabel = utilisationBLabel;
        this.utilisationCLabel = utilisationCLabel;
        this.utilisationAllLabel = utilisationAllLabel;
        this.utilisationAIntervalLabel = utilisationAIntervalLabel;
        this.utilisationBIntervalLabel = utilisationBIntervalLabel;
        this.utilisationCIntervalLabel = utilisationCIntervalLabel;
        this.utilisationAllIntervalLabel = utilisationAllIntervalLabel;
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {
    }

    @Override
    public void refresh(Simulation simulation) {
        MySimulation mySim = (MySimulation) simulation;

        if(!mySim.isSlowMode()) {
            SwingUtilities.invokeLater(() -> {
                utilisationALabel.setText(String.format("%.4f", mySim.getUtilisationA().mean()));
                utilisationBLabel.setText(String.format("%.4f", mySim.getUtilisationB().mean()));
                utilisationCLabel.setText(String.format("%.4f", mySim.getUtilisationC().mean()));
                utilisationAllLabel.setText(String.format("%.4f", mySim.getUtilisationAll().mean()));

                utilisationAIntervalLabel.setText(mySim.getUtilisationA().confidenceInterval());
                utilisationBIntervalLabel.setText(mySim.getUtilisationB().confidenceInterval());
                utilisationCIntervalLabel.setText(mySim.getUtilisationC().confidenceInterval());
                utilisationAllIntervalLabel.setText(mySim.getUtilisationAll().confidenceInterval());
            });
        }

    }
}
