package com.pozoriste.entities;


import com.pozoriste.entities.Karta;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

public class Predstava {
    public UUID id;
    public String name;
    public String description;
    public Date date;
    public float price;
    private final int rows = 5;
    private final int cols = 6;
    public Karta[][] seats = new Karta[rows][cols];

    public Predstava(UUID id, String name, String description, Date date, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.price = price;
    }

    public int getNumberOfRows() {
        return rows;
    }

    public int getNumberOfSeatsPerRow() {
        return cols;
    }

    public boolean seatAvailable (int row, int col) {
        if (seats[row][col] == null) {
            return true;
        }

        return false;
    }

    public void dodajKartu(Karta karta) {
        seats[karta.row][karta.col] = karta;
    }

    @Override
    public String toString() {
        return "Predstava{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", seats=" + Arrays.stream(seats).map(Arrays::toString).collect(Collectors.joining(System.lineSeparator())) +
                '}';
    }
}
