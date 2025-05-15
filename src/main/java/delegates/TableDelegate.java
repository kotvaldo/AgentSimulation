package delegates;

import GUI.Models.FurnitureTableModel;
import GUI.Models.OrdersTableModel;
import GUI.Models.WorkPlacesTableModel;
import GUI.Models.WorkersTableModel;
import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import entities.Worker;
import simulation.MySimulation;

import javax.swing.*;
import java.util.ArrayList;

public class TableDelegate implements ISimDelegate {
    private OrdersTableModel ordersTableModel;
    private FurnitureTableModel furnitureTableModel;
    private WorkPlacesTableModel workPlacesTableModel;
    private WorkersTableModel workersTableModel;

    public TableDelegate(OrdersTableModel ordersTableModel, FurnitureTableModel furnitureTableModel, WorkPlacesTableModel workPlacesTableModel, WorkersTableModel workersTableModel) {
        this.ordersTableModel = ordersTableModel;
        this.furnitureTableModel = furnitureTableModel;
        this.workPlacesTableModel = workPlacesTableModel;
        this.workersTableModel = workersTableModel;
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {

    }

    @Override
    public void refresh(Simulation simulation) {
        MySimulation sim = (MySimulation) simulation;
        if(sim.isSlowMode()) {
            SwingUtilities.invokeLater(() -> {
                this.ordersTableModel.setOrders(new ArrayList<>(sim.getOrderArrayList()));
                this.furnitureTableModel.setFurniture(new ArrayList<>(sim.getFurnitureArrayList()));
                this.workPlacesTableModel.setWorkPlaces(new ArrayList<>(sim.getWorkPlacesArrayList()));
                ArrayList<Worker> workersArrayList = new ArrayList<>();
                workersArrayList.addAll(sim.getWorkersAArrayList());
                workersArrayList.addAll(sim.getWorkersBArrayList());
                workersArrayList.addAll(sim.getWorkersCArrayList());

                this.workersTableModel.setWorkers(workersArrayList);

                ordersTableModel.fireTableDataChanged();
                furnitureTableModel.fireTableDataChanged();
                workPlacesTableModel.fireTableDataChanged();
                workersTableModel.fireTableDataChanged();
            });

        }


    }
}
