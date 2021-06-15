package com.pozoriste;

import com.pozoriste.entities.Korisnik;

public class State {
    public static Korisnik current_user = null;//new Korisnik(UUID.randomUUID(), "veljko", "Password.123", "Veljko", "Tornjanski", KorisnikRole.ADMIN);

    private State() {}
}
