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

package com.pavelfatin.game.entity;

import com.pavelfatin.game.Layer;
import com.pavelfatin.game.behavior.Behavior;

import java.awt.*;


public class RoamingDot extends Dot {
    public RoamingDot(Behavior behavior) {
        super(behavior, 500);
    }

    @Override
    public Layer getLayer() {
        return Layer.BOTTOM;
    }

    @Override
    public void draw(Graphics2D graphics) {
        int halfCell = Math.round((float) getPosition().width / 2.0F);
        int r = getPosition().width / 7;
        graphics.setColor(Color.BLUE);
        graphics.fillOval(halfCell - r,
                          halfCell - r,
                          r * 2,
                          r * 2);
    }
}
