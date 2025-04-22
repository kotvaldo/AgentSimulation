package GUI.Models;



import Enums.OrderStateValues;
import entities.Order;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class OrdersTableModel extends AbstractTableModel {

    private final String[] columns = {"Order ID", "FurnitureCount", "State", "Process Time"};
    private ArrayList<Order> orders;

    public OrdersTableModel(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> "Order ID: " + order.getId();
            case 1 -> order.getFurnitureList().size();
            case 2 -> OrderStateValues.getNameByValue(order.getState());
            case 3 -> order.getTimeOfWork() < 0 ? "NotProcessed" : order.getTimeOfWork();
            default -> null;
        };
    }
}
