/*
 * Copyright (C) 2011 Pavel Fatin <http://pavelfatin.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pavelfatin.game;

import javax.swing.*;
import java.io.File;


public class Game {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            runWith(Settings.getDefault());
        } else if (args.length == 4) {
            runWith(settingsFrom(args));
        } else {
            System.err.println("Usage: game.jar [<maze file> <tick period> <cell size> <step size>]");
        }
    }

    private static void runWith(Settings settings) {
        final MainFrame frame = new MainFrame(settings);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.open();
            }
        });
    }

    private static Settings settingsFrom(String[] args) throws Exception {
        return new Settings(new File(args[0]).toURI().toURL(),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]),
                Integer.parseInt(args[3]));
    }
}
