package com.pozoriste.database;

import com.pozoriste.entities.Karta;
import com.pozoriste.entities.Seat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class TicketsModel {
    public static void create(UUID event_id, UUID user_id, Seat seat, float price) {
        String query = String.format("INSERT INTO tickets (event_id, user_id, seat_row, seat_col, price) VALUES ('%s', '%s', %d, %d, %f)", event_id, user_id, seat.row, seat.col, price);

        Database.update(query);
    }

    public static ArrayList<Karta> getByEventId(UUID id) {
        String query = String.format("SELECT * FROM tickets WHERE event_id = '%s'", id);

        return Database.query(query, TicketsModel::mapper);
    }

    public static ArrayList<Karta> getAll() {
        String query = String.format("SELECT * FROM tickets");

        return Database.query(query, TicketsModel::mapper);
    }

    private static Karta mapper(ResultSet rs) {
        try {
            return new Karta(
                    UUID.fromString(rs.getString("ID")),
                    EventsModel.getById(UUID.fromString(rs.getString("EVENT_ID"))),
                    null,
                    rs.getInt("SEAT_ROW"),
                    rs.getInt("SEAT_COL"),
                    rs.getFloat("PRICE")
            );
        } catch (SQLException e) {
            return null;
        }
    }
}
