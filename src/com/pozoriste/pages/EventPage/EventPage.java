package com.pozoriste.pages.EventPage;

import com.pozoriste.Router;
import com.pozoriste.State;
import com.pozoriste.database.EventsModel;
import com.pozoriste.database.TicketsModel;
import com.pozoriste.entities.Karta;
import com.pozoriste.entities.KorisnikRole;
import com.pozoriste.entities.Predstava;
import com.pozoriste.entities.Seat;
import com.pozoriste.pages.Page;
import com.pozoriste.pages.ReportPage.EventStrategy;
import com.pozoriste.pages.ReportPage.ReportPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class EventPage implements Page {
    private JPanel rootPanel;
    private JLabel labelName;
    private JLabel labelTime;
    private JLabel labelDescription;
    private JLabel labelPrice;
    private JTextField fieldName;
    private JTextField fieldTime;
    private JTextArea textAreaDescription;
    private JTextField fieldPrice;
    private JPanel panelSeats;
    private JPanel panelBuy;
    private UUID event_id;
    private Router router;
    private Predstava event;
    private ArrayList<Karta> tickets;

    private boolean[][] selected_seats;

    public EventPage(Router router, String[] args) {
        this.router = router;
        this.event_id = UUID.fromString(args[0]);

        fetchData();

        this.selected_seats = new boolean[event.getNumberOfRows()][event.getNumberOfSeatsPerRow()];

        render();
    }

    public void fetchData() {
        this.event = EventsModel.getById(event_id);
        this.tickets = TicketsModel.getByEventId(event_id);

        for (Karta ticket: tickets) {
            event.dodajKartu(ticket);
        }
    }

    public void render() {
        panelSeats.removeAll();

        fieldName.setText(event.name);
        fieldTime.setText(String.valueOf(event.date));
        textAreaDescription.setText(event.description);
        fieldPrice.setText(String.format("%sRSD", event.price));

        int rows = event.getNumberOfRows();
        int cols = event.getNumberOfSeatsPerRow();
        panelSeats.setLayout(new SeatsLayout(rows, cols));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Karta karta = event.seats[row][col];

                SeatButton button = new SeatButton(new Seat(row, col));
                button.addActionListener(this::handleSeatButtonClick);

                if (karta == null) {
                    button.setBackground(Color.GREEN);
                } else {
                    button.setEnabled(false);
                    button.setBackground(Color.RED);
                }

                if (selected_seats[row][col]) {
                    button.setBackground(Color.BLUE);
                }

                panelSeats.add(button);
            }
        }
        panelSeats.doLayout();

        panelBuy.removeAll();
        panelBuy.setLayout(new GridLayout());
        int selected_seat_count = getSelectedSeatCount();
        if (selected_seat_count > 0) {
            String karta_string;
            switch (selected_seat_count) {
                case 1:
                    karta_string = "karta";
                    break;
                case 2:
                case 3:
                case 4:
                    karta_string = "karte";
                    break;
                default:
                    karta_string = "karata";
                    break;
            }

            String buy_button_text = String.format("Kupi - %s %s (%.2f RSD)", selected_seat_count, karta_string, selected_seat_count * event.price);
            JButton buy_button = new JButton(buy_button_text);
            buy_button.addActionListener(this::handleBuyButtonClick);
            panelBuy.add(buy_button);
        }
        panelBuy.doLayout();
    }

    private void handleSeatButtonClick(ActionEvent e) {
        SeatButton button = (SeatButton) e.getSource();

        int row = button.seat.row;
        int col = button.seat.col;

        selected_seats[row][col] = !selected_seats[row][col];
        render();
    }

    private void handleBuyButtonClick(ActionEvent e) {
        for (int row = 0; row < event.getNumberOfRows(); row++) {
            for (int col = 0; col < event.getNumberOfSeatsPerRow(); col++) {
                if (selected_seats[row][col]) {
                    selected_seats[row][col] = false;
                    TicketsModel.create(event_id, State.current_user.id, new Seat(row, col), event.price);
                }
            }
        }

        fetchData();
        render();
    }

    private int getSelectedSeatCount() {
        int count = 0;
        for (int row = 0; row < event.getNumberOfRows(); row++) {
            for (int col = 0; col < event.getNumberOfSeatsPerRow(); col++) {
                if (selected_seats[row][col]) {
                    count += 1;
                }
            }
        }

        return count;
    }

    @Override
    public JPanel getPanel() {
        return rootPanel;
    }

    @Override
    public String getPageName() {
        return event.name;
    }

    @Override
    public ArrayList<JComponent> getTools() {
        ArrayList<JComponent> tools = new ArrayList();

        JButton report_button = new JButton("Izvestaj");
        report_button.addActionListener(e -> new ReportPage(new EventStrategy(event_id)));


        if (State.current_user.role == KorisnikRole.ADMIN) {
            tools.add(report_button);
        }

        return tools;
    }
}
