package com.simongarton.utils;

import com.simongarton.utils.lanterna.Lanterna;

import java.awt.*;
import java.time.LocalTime;

public class Main {

    public static void main(final String[] args) {

        final Main main = new Main();
        main.clock();
    }

    private void clock() {

        final Lanterna lanterna = new Lanterna(80, 24, "Test");

        lanterna.drawString("Hello world !", 10, 10);
        lanterna.drawString("Thinking ...", 10, 12, Color.GREEN, Color.BLUE);

        lanterna.drawString("12345678901234567890123456789012345678901234567890123456789012345678901234567890", 0, 0, Color.YELLOW, Color.GRAY);
        lanterna.drawString("1234567890123456789012345678901234567890", 0, 23, Color.DARK_GRAY, Color.YELLOW);

        for (int i = 0; i < 10; i++) {
            lanterna.drawString(LocalTime.now().toString(), 10, 14);
            lanterna.refreshAndSleep(1000);
        }

        lanterna.close();
    }
}