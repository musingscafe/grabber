package com.musingscafe.grabber.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayadav on 11/12/16.
 */
public class ColumnFamilyConfig implements Serializable {

    private String channel;
    private List<String> activeColumns;
    private int columnCount;

    public void setChannel(String channel){
        this.channel = channel;
    }

    public void addNewColumn(String column) {
        createColumnListIfNull();
        activeColumns.add(column);
        setColumnCount(activeColumns.size());
    }

    public void removeColumn(String column) {
        if(activeColumns != null){
            activeColumns.remove(column);
            setColumnCount(activeColumns.size());
        }
    }

    public List<String> getActiveColumns() {
        return activeColumns;
    }

    public void setActiveColumns(List<String> columns) {
        this.activeColumns = columns;

        if(activeColumns != null) {
            setColumnCount(activeColumns.size());
        }
        else {
            setColumnCount(0);
        }
    }

    private void createColumnListIfNull() {
        if(activeColumns == null){
            activeColumns = new ArrayList<>();
        }

        setColumnCount(0);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}
