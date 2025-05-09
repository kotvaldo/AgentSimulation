package delegates;

import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import Utility.Utility;
import simulation.MySimulation;

import javax.swing.*;
import java.lang.classfile.Label;

public class SimulationTimeDelegate implements ISimDelegate {

    private JLabel label;
    private JLabel dayCountLabel;
    public SimulationTimeDelegate(JLabel label, JLabel dayCountLabel) {
        this.label = label;
        this.dayCountLabel = dayCountLabel;
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {
    }

    @Override
    public void refresh(Simulation simulation) {
        MySimulation mySimulation = (MySimulation) simulation;
        if(mySimulation.isSlowMode()) {
            SwingUtilities.invokeLater(() -> {
                label.setText("Simulation time : " + Utility.fromSecondsToTime(simulation.currentTime()));
                int newDay = (int) Math.round(simulation.currentTime() / (8.0 * 3600.0));
                dayCountLabel.setText("Day : " + newDay);
            });
        }
    }

}

