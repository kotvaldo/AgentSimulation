package delegates;

import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import agents.agentnabytku.ManagerNabytku;
import simulation.MySimulation;

import javax.swing.*;
import java.lang.classfile.Label;

public class LabelDelegate implements ISimDelegate {

    private final JLabel queueCutting;
    private final JLabel queueStaining;
    private final JLabel queuePainting;
    private final JLabel queueAssembly;
    private final JLabel queueMontage;

    private final JLabel allOrdersLabel;
    private final JLabel countOfFinishedOrdersLabel;
    private final JLabel replicationsCountLabel;

    public LabelDelegate(JLabel queueCutting, JLabel queueStaining, JLabel queuePainting,
                         JLabel queueAssembly, JLabel queueMontage,
                         JLabel allOrdersLabel, JLabel countOfFinishedOrdersLabel, JLabel replicationsCountLabel) {
        this.queueCutting = queueCutting;
        this.queueStaining = queueStaining;
        this.queuePainting = queuePainting;
        this.queueAssembly = queueAssembly;
        this.queueMontage = queueMontage;
        this.allOrdersLabel = allOrdersLabel;
        this.countOfFinishedOrdersLabel = countOfFinishedOrdersLabel;
        this.replicationsCountLabel = replicationsCountLabel;

    }




    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {

    }

    @Override
    public void refresh(Simulation simulation) {
        MySimulation mySimulation = (MySimulation) simulation;
        ManagerNabytku managerNabytku = (ManagerNabytku) mySimulation.agentNabytku().myManager();
        if(mySimulation.isSlowMode()) {

            SwingUtilities.invokeLater(() -> {
                queueCutting.setText("Cutting QL : " + managerNabytku.getQueueNonProcessed().size());
                queueStaining.setText("Staining QL : " + managerNabytku.getQueueStaining().size());
                queuePainting.setText("Painting QL : " + managerNabytku.getQueuePainting().size());
                queueAssembly.setText("Assembly QL : " + managerNabytku.getQueueAssembly().size());
                queueMontage.setText("Montage QL : " + managerNabytku.getQueueMontage().size());
                allOrdersLabel.setText("All Orders : " + mySimulation.getOrderArrayList().size());
                countOfFinishedOrdersLabel.setText("Finished Orders : " + mySimulation.getFinishedOrders().size());
            });
        }

         SwingUtilities.invokeLater(() -> {
             replicationsCountLabel.setText("Replication Count : " + mySimulation.getActualRepCount());
         });
    }
}
