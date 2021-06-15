package com.pozoriste.pages.EventPage;

import com.pozoriste.entities.Seat;

import javax.swing.*;

public class SeatButton extends JButton {
    public Seat seat;

    public SeatButton(Seat seat) {
        this.seat = seat;
    }

}
