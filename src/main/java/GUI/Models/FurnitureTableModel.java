package GUI.Models;

import Enums.FurnitureStateValues;
import entities.Furniture;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class FurnitureTableModel extends AbstractTableModel {

    private final String[] columns = {
            "Furniture ID", "Worker ID", "Type", "WorkplaceId", "State", "Order ID"
    };

    private ArrayList<Furniture> furnitures;

    public FurnitureTableModel(ArrayList<Furniture> furnitures) {
        this.furnitures = furnitures;
    }

    public void setFurniture(ArrayList<Furniture> furnitures) {
        this.furnitures = furnitures;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return furnitures.size();
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
        Furniture f = furnitures.get(rowIndex);
        String typeName = switch (f.getType()) {
            case 1 -> "Table";
            case 2 -> "Chair";
            case 3 -> "Wardrobe";
            default -> "Unknown";
        };

        return switch (columnIndex) {
            case 0 -> "ID: " + f.getId();
            case 1 -> f.getWorkPlace() != null
                    ? f.getWorkPlace().getActualWorkingWorker() != null
                    ? (f.getWorkPlace().getActualWorkingWorker().getId() + "," + f.getWorkPlace().getActualWorkingWorker().getType())
                    : "None"
                    : "None";

            case 2 -> typeName;
            case 3 -> f.getWorkPlace() != null ? f.getWorkPlace().getId() : "None";
            case 4 -> FurnitureStateValues.getNameByValue(f.getState());
            case 5 -> f.getOrder().getId();
            //case 10 -> f.getTotalTime() == -1 ? "None" : f.getTotalTime();

            default -> null;
        };
    }

    private String formatTime(double start, double end) {
        if (end == -1 || start == -1) {
            return "Not finished";
        }
        return String.format("%.2f", end - start);
    }
}
