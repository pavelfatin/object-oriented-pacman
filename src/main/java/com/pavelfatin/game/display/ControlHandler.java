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

package com.pavelfatin.game.display;

import com.pavelfatin.game.Direction;
import com.pavelfatin.game.Labyrinth;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ControlHandler extends KeyAdapter {
    private Labyrinth _labyrinth;


    public ControlHandler(Labyrinth labyrinth) {
        _labyrinth = labyrinth;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                _labyrinth.navigate(Direction.Left);
                break;
            case KeyEvent.VK_RIGHT:
                _labyrinth.navigate(Direction.Right);
                break;
            case KeyEvent.VK_UP:
                _labyrinth.navigate(Direction.Up);
                break;
            case KeyEvent.VK_DOWN:
                _labyrinth.navigate(Direction.Down);
                break;
            default:
                // do nothing
        }
    }
}
