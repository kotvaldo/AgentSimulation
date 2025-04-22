package GUI.Models;

import Enums.FurnitureStateValues;
import Enums.OrderStateValues;
import entities.Furniture;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class FurnitureTableModel extends AbstractTableModel {


    private final String[] columns = {"Furniture ID", "OrderId", "Type", "WorkplaceId", "State"};
    private ArrayList<Furniture> furnitures;

    public FurnitureTableModel(ArrayList<Furniture> furnitures) {
        this.furnitures = furnitures;
    }

    public void setFurniture(ArrayList<Furniture> orders) {
        this.furnitures = orders;
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
        Furniture furniture = furnitures.get(rowIndex);
        String furnitureType =
         switch (furniture.getType()) {
            case 1 -> "Table";
            case 2 -> "Chair";
            case 3 -> "Wardrobe";
            default -> "Unknown";
        };



        return switch (columnIndex) {
            case 0 -> "ID: " + furniture.getId();
            case 1 -> furniture.getOrder() != null ? furniture.getOrder().getId() : "None";
            case 2 -> furnitureType;
            case 3 -> furniture.getWorkPlace() != null ? furniture.getWorkPlace().getId() : "None";
            case 4 -> FurnitureStateValues.getNameByValue(furniture.getState());
            default -> null;
        };
    }
}


