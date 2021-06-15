package com.pozoriste.pages;

import javax.swing.*;
import java.util.ArrayList;

public interface Page {
    JPanel getPanel();
    String getPageName();
    ArrayList<JComponent> getTools();
}
