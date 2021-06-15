package com.pozoriste.pages.SignupPage;

import com.pozoriste.Router;
import com.pozoriste.State;
import com.pozoriste.database.UsersModel;
import com.pozoriste.entities.Korisnik;
import com.pozoriste.pages.EventsListPage.EventsListPage;
import com.pozoriste.pages.LoginPage.LoginPage;
import com.pozoriste.pages.Page;
import com.pozoriste.utils.JHyperLink;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class SignupPage implements Page {
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldUsername;
    private JButton registrujButton;
    private JPanel panelRoot;
    private JHyperLink JHyperLink1;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    private Router router;

    public SignupPage(Router router, String[] args) {
        this.router = router;

        registrujButton.addActionListener(this::handleSignupClick);
    }

    private void handleSignupClick(ActionEvent actionEvent) {
        try {
            String first_name_input = textFieldFirstName.getText();
            String last_name_input = textFieldLastName.getText();
            String username_input = textFieldUsername.getText();
            String password_input = String.valueOf(passwordField.getPassword());
            String confirm_password_input = String.valueOf(passwordConfirmField.getPassword());

            if (
                first_name_input.length() < 1 ||
                last_name_input.length() < 1 ||
                username_input.length() < 1 ||
                password_input.length() < 1 ||
                confirm_password_input.length() < 1
            ) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Sva polja moraju biti ispunjena!", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password_input.equals(confirm_password_input)) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Sifre se ne poklapaju!", "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int create_result = UsersModel.create(username_input, password_input, first_name_input, last_name_input);

            if (create_result < 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), String.format("Korisnicko ime '%s' je zauzeto", username_input), "Greska", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Korisnik korisnik = UsersModel.getByUsername(username_input);

            State.current_user = korisnik;
            router.push(EventsListPage.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public JPanel getPanel() {
        return panelRoot;
    }

    @Override
    public String getPageName() {
        return "Registracija";
    }

    @Override
    public ArrayList<JComponent> getTools() {
        return null;
    }

    private void createUIComponents() {
        JHyperLink1 = new JHyperLink("Uloguj se", e -> router.push(LoginPage.class));
    }
}
