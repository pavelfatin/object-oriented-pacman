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

import com.pavelfatin.game.entity.Entity;

import java.util.Collection;


class SpaceAdapter implements Space {
    private Labyrinth _labyrinth;
    private Entity _entity;


    SpaceAdapter(Labyrinth labyrinth, Entity entity) {
        _labyrinth = labyrinth;
        _entity = entity;
    }

    public Collection<Direction> getAvailableDirections() {
        return _labyrinth.getAvailableDirections(_entity);
    }

    public Direction getDirectionToward(Class type) {
        return _labyrinth.getDirectionToward(_entity, type);
    }
}
