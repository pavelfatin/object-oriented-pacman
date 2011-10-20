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

import com.pavelfatin.game.behavior.StaticBehavior;

import java.awt.*;


public class Fruit extends Entity {
    private Color _color;


    public Fruit(Color color) {
        super(StaticBehavior.getInstance(), 1000);
        _color = color;
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return false;
    }

    @Override
    public boolean canEat(Entity entity) {
        return false;
    }

    @Override
    public void draw(Graphics2D graphics) {
        int xSize = getPosition().width / 2;
        int ySize = getPosition().height / 3;
        graphics.setColor(_color);
        graphics.fillOval(getPosition().x + (getPosition().width - xSize) / 2,
                          getPosition().y + (getPosition().height - ySize) / 2,
                          xSize,
                          ySize);
    }
}
