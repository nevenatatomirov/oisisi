package com.pozoriste.utils;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class JHyperLink extends JLabel {
    private String url;
    private String html = "<html><a href=''>%s</a></html>";


    public JHyperLink(String text, Consumer<MouseEvent> onClick) {
        super(text);
        this.url = url;

        setForeground(Color.BLUE.darker());

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setText(String.format(html, text));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setText(text);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.accept(e);
            }

        });

    }
}