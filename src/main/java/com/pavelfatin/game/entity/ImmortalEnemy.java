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

import com.pavelfatin.game.behavior.Behavior;

import java.awt.*;


public class ImmortalEnemy extends Enemy {
    public ImmortalEnemy(Behavior behavior, Color color) {
        super(behavior, color);
    }

    @Override
    public boolean canEat(Entity entity) {
        return entity instanceof Creature;
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        graphics.setColor(Color.RED);

        final int inset = 4;
        graphics.drawOval(inset, inset,
                          getPosition().width - inset * 2,
                          getPosition().height - inset * 2);
    }
}
