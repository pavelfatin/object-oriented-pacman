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

package com.pavelfatin.game.behavior;

import com.pavelfatin.game.Direction;
import com.pavelfatin.game.Space;
import com.pavelfatin.game.Utilities;

import java.util.Collection;


public abstract class AbstractBehavior implements Behavior {
    private Direction _intention = Direction.None;
    private Collection<Direction> _availableDirections;
    private Space _space;


    public Direction nextDirection(Space space) {
        _space = space;
        _availableDirections = _space.getAvailableDirections();

        think();

        if (!isIntentionDirectionAvailable()) {
            setIntention(Direction.None);
        }

        return _intention;
    }

    protected boolean isDirectionAvailable(Direction direction) {
        return _availableDirections.contains(direction);
    }

    protected boolean isIntentionDirectionAvailable() {
        return isDirectionAvailable(_intention);
    }

    protected boolean isIntentionPerpendicualrTo(Direction direction) {
        return _intention.isPerpendicular(direction);
    }

    protected boolean isPerpendicularTurnsAvailable() {
        for (Direction direction : _availableDirections) {
            if (_intention.isPerpendicular(direction)) {
                return true;
            }
        }

        return false;
    }

    public void setIntention(Direction intention) {
        _intention = intention;
    }

    protected void turnToRandomDirection() {
        _intention = randomDirection();
    }

    protected Direction randomDirection() {
        return Utilities.chooseElement(_availableDirections, Direction.None);
    }

    protected boolean isMoving() {
        return !Direction.None.equals(_intention);
    }

    protected void turnToward(Class type) {
        setIntention(_space.getDirectionToward(type));
    }

    protected abstract void think();
}
