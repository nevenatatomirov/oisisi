package com.pozoriste.entities;

import java.util.UUID;

public class Karta {
    public UUID id;
    public Predstava predstava;
    public Korisnik korisnik;
    public int row;
    public int col;
    public float price;

    public Karta (UUID id, Predstava predstava, Korisnik korisnik, int row, int col, float price) {
        this.id = id;
        this.predstava = predstava;
        this.korisnik = korisnik;
        this.row = row;
        this.col = col;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Karta{" +
                "id=" + id +
                ", row=" + row +
                ", col=" + col +
                ", price=" + price +
                '}';
    }
}
