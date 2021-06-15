package com.pozoriste.pages.ReportPage;

import com.pozoriste.database.EventsModel;
import com.pozoriste.database.TicketsModel;
import com.pozoriste.entities.Karta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EventStrategy implements ReportStrategy {
    private UUID event_id;
    HashMap<UUID, Float> data;

    public EventStrategy(UUID event_id) {
        this.event_id = event_id;

        ArrayList<Karta> tickets = TicketsModel.getByEventId(event_id);

        HashMap<UUID, Float> data = new HashMap<UUID, Float>();

        for (Karta ticket : tickets) {
            float prev = data.containsKey(ticket.id) ? data.get(ticket.id) : 0;

            data.put(ticket.id, prev + ticket.price);
        }

        this.data = data;
    }

    @Override
    public HashMap<UUID, Float> getData() {
        return data;
    }

    @Override
    public String getName() {
        return  EventsModel.getById(event_id).name;
    }
}
