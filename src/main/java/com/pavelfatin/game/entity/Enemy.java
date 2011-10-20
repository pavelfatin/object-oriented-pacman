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


public class Enemy extends Entity {
    private Color _color;


    public Enemy(Behavior behavior, Color color) {
        super(behavior, 5000);
        _color = color;
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return !(entity instanceof Wall
                 || entity instanceof Dike);
    }

    @Override
    public boolean canEat(Entity entity) {
        return entity instanceof Creature &&
               !((Creature) entity).isBoosted();
    }

    @Override
    public void draw(Graphics2D graphics) {
        int halfCell = Math.round((float) getPosition().width / 2.0F);
        int r = getPosition().width / 3;
        graphics.setColor(_color);
        graphics.fillOval(+ halfCell - r,
                          + halfCell - r,
                          r * 2,
                          r * 2);

        int crossRadius = r / 2;
        graphics.setColor(Color.BLACK);
        graphics.drawLine(halfCell - crossRadius,
                          halfCell - crossRadius,
                          halfCell + crossRadius,
                          halfCell + crossRadius);
        graphics.drawLine(halfCell - crossRadius - 1,
                          halfCell - crossRadius + 1,
                          halfCell + crossRadius + 1,
                          halfCell + crossRadius - 1);

        graphics.drawLine(halfCell + crossRadius,
                          halfCell - crossRadius,
                          halfCell - crossRadius,
                          halfCell + crossRadius);
        graphics.drawLine(halfCell + crossRadius - 1,
                          halfCell - crossRadius - 1,
                          halfCell - crossRadius + 1,
                          halfCell + crossRadius + 1);
    }

    @Override
    public Layer getLayer() {
        return Layer.TOP;
    }
}
