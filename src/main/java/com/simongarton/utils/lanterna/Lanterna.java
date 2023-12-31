package com.simongarton.utils.lanterna;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

import java.awt.*;
import java.io.IOException;

public class Lanterna {

    // found by trial and error to get the correct sizes.
    private static final int CHAR_WIDTH = 11;
    private static final int CHAR_HEIGHT = 20;

    private final TerminalScreen screen;
    private final int pixelWidth;
    private final int pixelHeight;
    private final int width;
    private final int height;

    // note : Lanterna will wrap text around if too wide for screen

    public Lanterna(final int characterWidth, final int characterHeight, final String title) {

        try {

            this.width = characterWidth;
            this.height = characterHeight;

            this.pixelWidth = characterWidth * CHAR_WIDTH;
            this.pixelHeight = characterHeight * CHAR_HEIGHT;

            final Terminal terminal = new DefaultTerminalFactory().createTerminal();
            ((SwingTerminalFrame) terminal).setTitle(title);
            ((SwingTerminalFrame) terminal).setSize(this.pixelWidth, this.pixelHeight);

            this.screen = new TerminalScreen(terminal);
            this.screen.setCursorPosition(null);
            this.screen.startScreen();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawString(final String s, final int x, final int y) {

        for (int i = 0; i < s.length(); i++) {
            this.drawChar(s.charAt(i), x + i, y, TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK);
        }
    }

    public void drawString(final String s, final int x, final int y, final Color background) {

        for (int i = 0; i < s.length(); i++) {
            this.drawChar(s.charAt(i), x + i, y, TextColor.ANSI.WHITE_BRIGHT, this.mapColor(background));
        }
    }

    public void drawString(final String s, final int x, final int y, final Color foreground, final Color background) {

        for (int i = 0; i < s.length(); i++) {
            this.drawChar(s.charAt(i), x + i, y, this.mapColor(foreground), this.mapColor(background));
        }
    }

    public void drawString(final String s, final int x, final int y, final String ansiBackground) {

        for (int i = 0; i < s.length(); i++) {
            this.drawChar(s.charAt(i), x + i, y, TextColor.ANSI.WHITE_BRIGHT, this.mapColor(ansiBackground));
        }
    }

    public void drawString(final String s, final int x, final int y, final String ansiForeground, final String ansiBackground) {

        for (int i = 0; i < s.length(); i++) {
            this.drawChar(s.charAt(i), x + i, y, this.mapColor(ansiForeground), this.mapColor(ansiBackground));
        }
    }

    private void drawChar(final char c, final int x, final int y, final TextColor foreground, final TextColor background) {
        final TextCharacter textCharacter = new TextCharacter(c, foreground, background);
        this.screen.setCharacter(new TerminalPosition(x, y), textCharacter);
    }

    // doesn't seem to be working right now ...
    public void closeAfterEscape() {
        while (true) {
            final KeyStroke keyStroke;
            try {
                keyStroke = this.screen.pollInput();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
            if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF)) {
                break;
            }
        }
    }

    public void refreshAndSleep() {
        this.refreshAndSleep(0);
    }

    public void refreshAndSleep(final int delayMillis) {
        try {
            this.screen.refresh();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(delayMillis);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private TextColor mapColor(final String color) {
        switch (color) {
            case "BLACK":
                return TextColor.ANSI.BLACK;
            case "WHITE":
                return TextColor.ANSI.WHITE;
            case "RED":
                return TextColor.ANSI.RED;
            case "GREEN":
                return TextColor.ANSI.GREEN;
            case "BLUE":
                return TextColor.ANSI.BLUE;
            case "YELLOW":
                return TextColor.ANSI.YELLOW;
            case "MAGENTA":
                return TextColor.ANSI.MAGENTA;
            case "CYAN":
                return TextColor.ANSI.CYAN;
            case "BLACK_BRIGHT":
                return TextColor.ANSI.BLACK_BRIGHT;
            case "WHITE_BRIGHT":
                return TextColor.ANSI.WHITE_BRIGHT;
            case "RED_BRIGHT":
                return TextColor.ANSI.RED_BRIGHT;
            case "GREEN_BRIGHT":
                return TextColor.ANSI.GREEN_BRIGHT;
            case "BLUE_BRIGHT":
                return TextColor.ANSI.BLUE_BRIGHT;
            case "YELLOW_BRIGHT":
                return TextColor.ANSI.YELLOW_BRIGHT;
            case "MAGENTA_BRIGHT":
                return TextColor.ANSI.MAGENTA_BRIGHT;
            case "CYAN_BRIGHT":
                return TextColor.ANSI.CYAN_BRIGHT;
            default:
                throw new RuntimeException("Unknown ANSI color " + color);
        }
    }

    private TextColor mapColor(final Color color) {
        return new TextColor.RGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public void close() {
        try {
            this.screen.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
