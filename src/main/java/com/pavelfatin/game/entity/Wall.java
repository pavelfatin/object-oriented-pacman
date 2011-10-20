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


public class Wall extends Entity {
    public Wall() {
        super(StaticBehavior.getInstance(), 0);
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
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1,
                          1,
                          getPosition().width - 2,
                          getPosition().height - 2);
    }
}
