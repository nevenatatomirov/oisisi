package com.pozoriste.pages.ReportPage;

import com.pozoriste.database.EventsModel;
import com.pozoriste.entities.Predstava;
import com.pozoriste.utils.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ReportPage {
    private JTable table1;
    private JPanel panel1;
    private JLabel labelSum;
    private JTextArea textAreaSum;

    public ReportPage(ReportStrategy strategy) {

        ArrayList<Predstava> events = EventsModel.getAll();

        String[] column_names = {"ID", "Cena" };

        ArrayList<String[]> data = new ArrayList<>();

        for (Map.Entry<UUID, Float> entry : strategy.getData().entrySet()) {
            data.add(new String[]{ String.valueOf(entry.getKey()), String.valueOf(entry.getValue()) });
        }

        TableModel table_model = new ReportTableModel(column_names, data.toArray(new String[0][]));

        table1.setModel(table_model);

        JTableHeader header = table1.getTableHeader();

        Font current_header_font = table1.getFont();
        header.setFont(new Font(current_header_font.getName(), current_header_font.getStyle(), 2 * current_header_font.getSize()));

        header.setBackground(Color.black);
        header.setForeground(Color.yellow);

        Font current_font = table1.getFont();
        table1.setFont(new Font(current_font.getName(), current_font.getStyle(), 2 * current_font.getSize()));
        table1.setRowHeight(2 * table1.getRowHeight());
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table1);
        tca.adjustColumns();

        float sum =  data.stream().map(ticket -> Float.parseFloat(ticket[1])).reduce(0f, Float::sum);
        textAreaSum.setText(String.format("Ukupno: %s%nUkupno RSD: %.2f", strategy.getData().size(), sum));
        textAreaSum.setEditable(false);
        textAreaSum.setBackground(panel1.getBackground());

        JFrame frame = new JFrame("Izvestaj - " + strategy.getName());
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(table1.getSize().width + (Integer) UIManager.get("ScrollBar.width") + 1, frame.getHeight());


    }
}
