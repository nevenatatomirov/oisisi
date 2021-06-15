package com.pozoriste.pages;

import com.pozoriste.Router;
import com.pozoriste.State;
import com.pozoriste.entities.KorisnikRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Layout {
    private JPanel panelRoot;
    private JButton buttonBack;
    private JPanel panelContent;
    private JLabel labelUsername;
    private JPanel panelTools;
    private Router router;

    public Layout(Router router) {
        this.router = router;

        buttonBack.addActionListener(this::handleBackButtonClick);
    }


    private void handleBackButtonClick(ActionEvent e) {
        router.pop();
    }

    public JPanel getPanel() {
        return panelRoot;
    }

    public void setContent(Page page) {
        panelContent.removeAll();
        panelContent.add(page.getPanel());

        setupBackButton();
        setupUsernameLabel();
        setupTools(page);
    }

    private void setupBackButton() {
        if (State.current_user == null) {
            buttonBack.setVisible(false);
            return;
        }

        buttonBack.setVisible(true);
    }

    private void setupUsernameLabel() {
        if (State.current_user == null) {
            labelUsername.setText("");
            return;
        }

        labelUsername.setText(State.current_user.first_name + " " + State.current_user.last_name);
    }

    private void setupTools(Page page) {
        panelTools.removeAll();
        panelTools.setLayout(new GridLayout());

        ArrayList<JComponent> tools = page.getTools();

        if (tools == null) {
            return;
        }

        for (JComponent c : tools) {
            panelTools.add(c);
        }
    }
}
