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


public class Dike extends Entity {
    public Dike(Behavior behavior) {
        super(behavior, 0);
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return !(entity instanceof Wall
                 || entity instanceof Creature
                 || entity instanceof Enemy
                 || entity instanceof Dike
                 || entity instanceof RoamingDot);
    }

    @Override
    public boolean canEat(Entity entity) {
        return false;
    }

    @Override
    public Layer getLayer() {
        return Layer.TOP;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(4, 4, getPosition().width - 8, getPosition().height - 8);
    }
}
