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


public class GlancingBehavior extends RoamingBehavior {
    private static final double GLANCE_PROBABILITY = 0.5D;


    @Override
    protected void think() {
        if (isIntentionDirectionAvailable()) {
            if ((Math.random() < GLANCE_PROBABILITY)) {
                glance();
            }
        } else {
            super.think();
        }
    }

    private void glance() {
        if (isPerpendicularTurnsAvailable()) {
            for (Direction direction : Direction.MOVING_DIRECTIONS) {
                if (isIntentionPerpendicualrTo(direction) &&
                    isDirectionAvailable(direction)) {
                    setIntention(direction);
                    return;
                }
            }
        }
    }
}
