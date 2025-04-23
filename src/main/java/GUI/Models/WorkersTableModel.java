package GUI.Models;



import Enums.WorkerBussyState;
import entities.Worker;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WorkersTableModel extends AbstractTableModel {

    private final String[] columns = {"Worker ID", "Group", "State", "Order ID", "WorkPlace ID"};
    private List<Worker> workers;

    public WorkersTableModel(List<Worker> workers) {
        this.workers = workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return workers.size();
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
        Worker worker = workers.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> worker.getId();
            case 1 -> worker.getType();
            case 2 -> WorkerBussyState.getNameByValue(worker.getState());
            case 3 -> worker.getFurniture() != null ? "" + worker.getFurniture().getId() : "No Order";
            case 4 -> worker.getCurrentWorkPlace() != null ? "" + worker.getCurrentWorkPlace().getId() : "Storage";
            default -> null;
        };
    }
}

