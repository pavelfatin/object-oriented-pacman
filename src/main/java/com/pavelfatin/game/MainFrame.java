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

import com.pavelfatin.game.display.ControlHandler;
import com.pavelfatin.game.display.LabyrinthDisplay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class MainFrame extends JFrame {
    private Labyrinth _labyrinth;
    private LabyrinthDisplay _display;
    private Timer _timer;


    MainFrame(Settings settings) {
        super("Game");

        _labyrinth = new LabyrinthLoader().load(settings.getURL(), settings.getCellSize(), settings.getStepSize());
        _display = new LabyrinthDisplay(_labyrinth);
        getContentPane().add(_display);

        _display.addKeyListener(new ControlHandler(_labyrinth));

        _timer = new Timer(settings.getTickPeriod(), new TimerListener());
    }

    public void open() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        Utilities.centerOnScreen(this);
        setVisible(true);
        _timer.start();
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            _labyrinth.processTick();
            _display.repaint();

            if (_labyrinth.isWin() || _labyrinth.isLose()) {
                _timer.stop();
            }
        }
    }
}
