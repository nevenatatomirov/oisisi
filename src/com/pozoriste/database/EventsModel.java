package com.pozoriste.database;

import com.pozoriste.entities.Predstava;
import com.pozoriste.entities.Seat;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

public class EventsModel {
    public static int create(String name, String description, Date date, float price) {
        String query = String.format("INSERT INTO events (name, description, date, price) VALUES ('%s', '%s', '%s', %f)", name, description, Timestamp.from(date.toInstant()), price);

        return Database.update(query);
    }

    public static Predstava getById(UUID id) {
        String query = String.format("SELECT * FROM events WHERE id = '%s'", id);

        return Database.query(query, EventsModel::mapper).get(0);
    }

    public static ArrayList<Predstava> getAll() {
        String query = "SELECT * FROM events";

        return Database.query(query, EventsModel::mapper);
    }

    private static Predstava mapper(ResultSet rs) {
        try {
            return new Predstava(
                    UUID.fromString(rs.getString("ID")),
                    rs.getString("NAME"),
                    rs.getString("DESCRIPTION"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("DATE")),
                    rs.getFloat("PRICE")
            );
        } catch (SQLException | ParseException e) {
            return null;
        }
    }
}
