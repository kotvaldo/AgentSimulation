package delegates;

import GUI.Models.FurnitureTableModel;
import GUI.Models.OrdersTableModel;
import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import simulation.MySimulation;

import javax.swing.*;
import java.util.ArrayList;

public class TableDelegate implements ISimDelegate {
    private OrdersTableModel ordersTableModel;
    private FurnitureTableModel furnitureTableModel;


    public TableDelegate(OrdersTableModel ordersTableModel, FurnitureTableModel furnitureTableModel) {
        this.ordersTableModel = ordersTableModel;
        this.furnitureTableModel = furnitureTableModel;
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {

    }

    @Override
    public void refresh(Simulation simulation) {

        MySimulation sim = (MySimulation) simulation;
        SwingUtilities.invokeLater(() -> {
            this.ordersTableModel.setOrders(new ArrayList<>(sim.getOrderArrayList()));
            this.furnitureTableModel.setFurniture(new ArrayList<>(sim.getFurnitureArrayList()));
            ordersTableModel.fireTableDataChanged();
            furnitureTableModel.fireTableDataChanged();
        });


    }
}
