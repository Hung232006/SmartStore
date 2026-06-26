package org.ra.util;

import org.ra.config.DBConnection;
import org.ra.presentation.MainMenu;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.menu();
    }
}