package com.pozoriste.pages.NewEventPage;

import com.github.lgooddatepicker.components.DateTimePicker;
import com.pozoriste.Router;
import com.pozoriste.database.EventsModel;
import com.pozoriste.pages.EventsListPage.EventsListPage;
import com.pozoriste.pages.Page;

import javax.swing.*;
import javax.swing.text.InternationalFormatter;
import java.awt.event.ActionEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class NewEventPage implements Page {
    private JPanel panel1;
    private JTextField fieldName;
    private JTextArea textAreaDescription;
    private DateTimePicker dateTimePicker;
    private JButton kreirajButton;
    private JFormattedTextField fieldPrice;
    private Router router;

    public NewEventPage(Router router, String[] args) {
        this.router = router;

        fieldPrice.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {

            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                format.setRoundingMode(RoundingMode.HALF_UP);
                InternationalFormatter formatter = new InternationalFormatter(format);
                formatter.setAllowsInvalid(false);
                formatter.setMinimum(0.0);
                return formatter;
            }
        });

        kreirajButton.addActionListener(this::handleSubmit);

        dateTimePicker.getDatePicker().setDateToToday();
        dateTimePicker.getTimePicker().setTimeToNow();
    }

    private void handleSubmit(ActionEvent e) {
            String name = fieldName.getText();
            String description = textAreaDescription.getText();
            Date date = Date.from(LocalDateTime.of(dateTimePicker.getDatePicker().getDate(), dateTimePicker.getTimePicker().getTime()).atZone(ZoneId.systemDefault()).toInstant());

            if (
                name.length() < 1 ||
                description.length() < 1 ||
                fieldPrice.getText().length() < 1
            ) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Sva polja moraju biti ispunjena!", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ( date.before(new Date())) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Vreme odrzavanja predstave ne moze biti u proslosti!", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float price = Float.parseFloat(fieldPrice.getText().replace(",", ""));

            EventsModel.create(name, description, date, price);

            router.push(EventsListPage.class);
    }

    @Override
    public JPanel getPanel() {
        return panel1;
    }

    @Override
    public String getPageName() {
        return "Nova Predstava";
    }

    @Override
    public ArrayList<JComponent> getTools() {
        return null;
    }
}
