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


import java.net.URL;


class Settings {
    private URL _url;
    private int _tickPeriod;
    private int _cellSize;
    private int _stepSize;

    
    Settings(URL url, int tickPeriod, int cellSize, int stepSize) {
        _url = url;
        _tickPeriod = tickPeriod;
        _cellSize = cellSize;
        _stepSize = stepSize;
    }

    public URL getURL() {
        return _url;
    }

    public int getTickPeriod() {
        return _tickPeriod;
    }

    public int getCellSize() {
        return _cellSize;
    }

    public int getStepSize() {
        return _stepSize;
    }
    
    static Settings getDefault() {
        return new Settings(MainFrame.class.getResource("/maze.txt"), 10, 36, 2);
    }
}
