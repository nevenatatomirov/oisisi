package com.pozoriste.entities;

import java.util.ArrayList;
import java.util.UUID;

public class Korisnik {
    public UUID id;
    public String username;
    public String password;
    public String first_name;
    public String last_name;
    public KorisnikRole role;
    public ArrayList<Karta> tickets = new ArrayList<Karta>();

    public Korisnik(UUID id, String username, String password, String first_name, String last_name, KorisnikRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
    }

    public void dodajKartu(Karta karta) {
        tickets.add(karta);
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", role=" + role +
                ", tickets=" + tickets +
                '}';
    }
}
