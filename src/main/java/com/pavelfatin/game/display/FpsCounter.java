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

class FpsCounter {
    private static final int PERIOD = 1;

    private long _last;
    private int _frames;
    private int _fps;


    public int getFps(long time) {
        _frames++;

        int seconds = (int) ((time - _last) / 1000L);

        if (seconds >= PERIOD) {
            _fps = _frames / seconds;

            _frames = 0;
            _last = time;
        }

        return _fps;
    }
}
