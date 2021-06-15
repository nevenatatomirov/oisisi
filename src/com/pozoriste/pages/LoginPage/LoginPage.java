package com.pozoriste.pages.LoginPage;

import com.pozoriste.State;
import com.pozoriste.entities.Korisnik;
import com.pozoriste.pages.Page;
import com.pozoriste.Router;
import com.pozoriste.database.UsersModel;
import com.pozoriste.pages.EventsListPage.EventsListPage;
import com.pozoriste.pages.SignupPage.SignupPage;
import com.pozoriste.utils.JHyperLink;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.*;
import java.util.ArrayList;

public class LoginPage implements Page {
    private JPanel rootPanel;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JHyperLink JHyperLink1;
    private Router router;

    public LoginPage(Router router, String[] args) {
        this.router = router;

        if (State.current_user != null) {
            router.push(EventsListPage.class);
        }

        loginButton.addActionListener(this::handleLoginClick);
    }

    private void handleLoginClick(ActionEvent actionEvent) {
        try {
            String username_input = usernameTextField.getText();
            String password_input = String.valueOf(passwordField.getPassword());

            Korisnik korisnik = UsersModel.getByUsername(username_input);

            if (korisnik.password.equals(password_input)) {
                State.current_user = korisnik;
                router.push(EventsListPage.class);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public JPanel getPanel() {
        return rootPanel;
    }

    @Override
    public String getPageName() {
        return "Login";
    }

    @Override
    public ArrayList<JComponent> getTools() {
        return null;
    }

    private void createUIComponents() {
        JHyperLink1 = new JHyperLink("Registruj se", e -> router.push(SignupPage.class));
    }
}

