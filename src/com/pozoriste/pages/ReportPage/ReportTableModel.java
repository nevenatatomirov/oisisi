package com.pozoriste.pages.ReportPage;

import javax.swing.table.AbstractTableModel;

class ReportTableModel extends AbstractTableModel {
    private String[] column_names;
    private Object[][] data;

    public ReportTableModel (String[] column_names, Object[][] data) {
        this.column_names = column_names;
        this.data = data;
    }

    public int getColumnCount() {
        return column_names.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return column_names[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}