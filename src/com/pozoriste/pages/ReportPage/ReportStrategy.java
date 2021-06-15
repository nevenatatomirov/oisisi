package com.pozoriste.pages.ReportPage;

import com.pozoriste.entities.Karta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface ReportStrategy {
    public HashMap<UUID, Float> getData();
    public String getName();
}
