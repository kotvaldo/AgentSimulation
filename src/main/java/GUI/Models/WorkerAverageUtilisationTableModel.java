package GUI.Models;


import Statistics.Average;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class WorkerAverageUtilisationTableModel extends AbstractTableModel {

    private final String[] columns = {"Worker ID", "Group", "Utilisation"};
    private ArrayList<Average> workersAUtilisationAverage;
    private ArrayList<Average> workersBUtilisationAverage;
    private ArrayList<Average> workersCUtilisationAverage;

    public WorkerAverageUtilisationTableModel(
            ArrayList<Average> workersAUtilisationAverage,
            ArrayList<Average> workersBUtilisationAverage,
            ArrayList<Average> workersCUtilisationAverage
    ) {
        this.workersAUtilisationAverage = workersAUtilisationAverage;
        this.workersBUtilisationAverage = workersBUtilisationAverage;
        this.workersCUtilisationAverage = workersCUtilisationAverage;
    }

    @Override
    public int getRowCount() {
        return workersAUtilisationAverage.size() + workersBUtilisationAverage.size() + workersCUtilisationAverage.size();
    }

    public void setNewData(ArrayList<Average> workersAUtilisationAverage, ArrayList<Average> workersBUtilisationAverage, ArrayList<Average> workersCUtilisationAverage) {
        this.workersAUtilisationAverage = workersAUtilisationAverage;
        this.workersBUtilisationAverage = workersBUtilisationAverage;
        this.workersCUtilisationAverage = workersCUtilisationAverage;
        SwingUtilities.invokeLater(this::fireTableDataChanged);

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
        String group;
        int localIndex;

        if (rowIndex < workersAUtilisationAverage.size()) {
            group = "A";
            localIndex = rowIndex;
        } else if (rowIndex < workersAUtilisationAverage.size() + workersBUtilisationAverage.size()) {
            group = "B";
            localIndex = rowIndex - workersAUtilisationAverage.size();
        } else {
            group = "C";
            localIndex = rowIndex - workersAUtilisationAverage.size() - workersBUtilisationAverage.size();
        }

        Average avg = switch (group) {
            case "A" -> workersAUtilisationAverage.get(localIndex);
            case "B" -> workersBUtilisationAverage.get(localIndex);
            case "C" -> workersCUtilisationAverage.get(localIndex);
            default -> null;
        };

        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> group;
            case 2 -> avg != null ? String.format("%.4f", avg.mean()) : "-";
            default -> null;
        };
    }
}
