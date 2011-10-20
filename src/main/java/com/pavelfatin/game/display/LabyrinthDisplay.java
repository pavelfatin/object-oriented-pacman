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

import com.pavelfatin.game.Labyrinth;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


public class LabyrinthDisplay extends JComponent {
    private static final Font INFO_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font RESULT_FONT = new Font("Arial", Font.BOLD, 42);

    private Labyrinth _labyrinth;
    private FpsCounter _fpsCounter = new FpsCounter();


    public LabyrinthDisplay(Labyrinth labyrinth) {
        _labyrinth = labyrinth;

        setFocusable(true);
        setPreferredSize(_labyrinth.getDimensions());
        enableInputMethods(true);
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getVisibleRect().width, getVisibleRect().height);

        _labyrinth.render((Graphics2D) g);

        drawScore(g);
        drawFps(g);

        if (_labyrinth.isWin()) {
            drawResult(g, "Win", Color.ORANGE);
        }

        if (_labyrinth.isLose()) {
            drawResult(g, "Lose", Color.RED);
        }
    }

    private void drawScore(Graphics g) {
        drawInfo(g, String.format("Score: %d", _labyrinth.getScore()), 10, 20);
    }

    private void drawFps(Graphics g) {
        String info = String.format("FPS: %3d", _fpsCounter.getFps(System.currentTimeMillis()));

        Dimension infoSize = getSize(g, info);
        Dimension displaySize = getSize();

        drawInfo(g, info, displaySize.width - infoSize.width - 12, 20);
    }

    private Dimension getSize(Graphics g, String string) {
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(string, g);
        return new Dimension((int) bounds.getWidth(),
                             (int) bounds.getHeight());
    }

    private void drawInfo(Graphics g, String info, int x, int y) {
        g.setFont(INFO_FONT);
        Dimension size = getSize(g, info);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x - 5, y - (int) (size.height / 1.2F) - 1,
                   size.width + 10, size.height);

        g.setColor(Color.BLACK);
        g.drawString(info, x, y);
    }

    private void drawResult(Graphics g, String result, Color color) {
        g.setFont(RESULT_FONT);
        Dimension size = getSize(g, result);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, _labyrinth.getDimensions().height / 2
                      - (int) (size.height / 1.2F),
                   _labyrinth.getDimensions().width, size.height);

        g.setColor(color);
        g.drawString(result, (_labyrinth.getDimensions().width - size.width) / 2,
                     _labyrinth.getDimensions().height / 2);
    }

    @Override
    public void invalidate() {
    }

    @Override
    public void validate() {
    }

    @Override
    public void revalidate() {
    }

    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }
}
