package com.pozoriste;

import com.pozoriste.pages.EventPage.EventPage;
import com.pozoriste.pages.EventsListPage.EventsListPage;
import com.pozoriste.pages.Layout;
import com.pozoriste.pages.LoginPage.LoginPage;
import com.pozoriste.pages.Page;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        Router router = new Router();
        Layout layout = new Layout(router);
        JFrame frame = new JFrame("Pozoriste");

        router.subscribe(new_route -> {
            Page new_page = new_route.getPage();

            layout.setContent(new_page);
            frame.setTitle(String.format("Pozoriste - %s", new_page.getPageName()));
            frame.setVisible(true);
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(layout.getPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        router.push(LoginPage.class);
    }
}
