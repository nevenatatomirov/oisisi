package com.pozoriste.pages.EventsListPage;

import com.pozoriste.State;
import com.pozoriste.entities.KorisnikRole;
import com.pozoriste.pages.EventPage.EventPage;
import com.pozoriste.pages.NewEventPage.NewEventPage;
import com.pozoriste.pages.Page;
import com.pozoriste.entities.Predstava;
import com.pozoriste.Router;
import com.pozoriste.pages.ReportPage.EventStrategy;
import com.pozoriste.pages.ReportPage.EventsStrategy;
import com.pozoriste.pages.ReportPage.ReportPage;
import com.pozoriste.utils.ButtonColumn;
import com.pozoriste.utils.TableColumnAdjuster;
import com.pozoriste.database.EventsModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

public class EventsListPage implements Page {
    private JPanel rootPanel;
    private JTable eventsTable;
    private Router router;

    public EventsListPage(Router router, String[] args) {
        this.router = router;

        render();
    }

    private void render() {
        ArrayList<Predstava> events = EventsModel.getAll();

        String[] column_names = {"Naziv", "Datum", "Cena", ""};

        String[][] data = events
                .stream()
                .map(event -> new String[]{ event.name, String.valueOf(event.date), String.valueOf(event.price), "Detaljnije" })
                .toArray(String[][]::new);

        EventsTableModel table_model = new EventsTableModel(column_names, data);

        eventsTable.setModel(table_model);

        JTableHeader header = eventsTable.getTableHeader();

        Font current_header_font = eventsTable.getFont();
        header.setFont(new Font(current_header_font.getName(), current_header_font.getStyle(), 2 * current_header_font.getSize()));

        header.setBackground(Color.black);
        header.setForeground(Color.yellow);

        Font current_font = eventsTable.getFont();
        eventsTable.setFont(new Font(current_font.getName(), current_font.getStyle(), 2 * current_font.getSize()));
        eventsTable.setRowHeight(2 * eventsTable.getRowHeight());

        Action handleButtonClick = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());

                System.out.println("Row in table clicked: " + row);
            }
        };

        new ButtonColumn(eventsTable, handleButtonClick, 3);
    }


    @Override
    public JPanel getPanel() {
        return rootPanel;
    }

    @Override
    public String getPageName() {
        return "Predstave";
    }

    @Override
    public ArrayList<JComponent> getTools() {
        ArrayList<JComponent> tools = new ArrayList();

        JButton new_event_button = new JButton("Kreiraj Predstavu");
        new_event_button.addActionListener(e -> router.push(NewEventPage.class));

        JButton report_button = new JButton("Izvestaj");
        report_button.addActionListener(e -> new ReportPage(new EventsStrategy()));

        if (State.current_user.role == KorisnikRole.ADMIN) {
            tools.add(new_event_button);
            tools.add(report_button);
        }

        return tools;
    }
}
