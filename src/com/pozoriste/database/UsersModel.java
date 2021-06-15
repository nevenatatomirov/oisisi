package com.pozoriste.database;

import com.pozoriste.entities.Korisnik;
import com.pozoriste.entities.KorisnikRole;
import com.pozoriste.entities.Seat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UsersModel {
    public static int create(String username, String password, String first_name, String last_name) {
        String query = String.format("INSERT INTO users (username, password, first_name, last_name, role) VALUES ('%s', '%s', '%s', '%s', 'KORISNIK')", username, password, first_name, last_name);

        return Database.update(query);
    }

    public static Korisnik getById(UUID query_id) {
        String query = String.format("SELECT * FROM users WHERE id = '%s'", query_id.toString());

        return Database.query(query, UsersModel::mapper).get(0);
    }

    public static Korisnik getByUsername(String username) {
        String query = String.format("SELECT * FROM users WHERE username = '%s'", username);

        return Database.query(query, UsersModel::mapper).get(0);
    }


    private static Korisnik mapper(ResultSet rs) {
        try {
            return new Korisnik(
                    UUID.fromString(rs.getString("ID")),
                    rs.getString("USERNAME"),
                    rs.getString("PASSWORD"),
                    rs.getString("FIRST_NAME"),
                    rs.getString("LAST_NAME"),
                    KorisnikRole.valueOf(rs.getString("ROLE"))
            );
        } catch (SQLException e) {
            return null;
        }
    }

}
