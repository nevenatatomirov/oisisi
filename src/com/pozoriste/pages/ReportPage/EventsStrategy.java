package com.pozoriste.pages.ReportPage;

import com.pozoriste.database.EventsModel;
import com.pozoriste.database.TicketsModel;
import com.pozoriste.entities.Karta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EventsStrategy implements ReportStrategy {
    private UUID event_id;
    HashMap<UUID, Float> data;

    public EventsStrategy() {
        ArrayList<Karta> tickets = TicketsModel.getAll();

        HashMap<UUID, Float> data = new HashMap<UUID, Float>();

        for (Karta ticket : tickets) {
            float prev = data.containsKey(ticket.predstava.id) ? data.get(ticket.predstava.id) : 0;

            data.put(ticket.predstava.id, prev + ticket.price);
        }

        this.data = data;
    }

    public HashMap<UUID, Float> getData() {
        return data;
    }

    @Override
    public String getName() {
        return  "Sve Predstave";
    }
}
