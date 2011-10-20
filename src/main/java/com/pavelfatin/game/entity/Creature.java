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

import com.pavelfatin.game.Direction;
import com.pavelfatin.game.Layer;
import com.pavelfatin.game.behavior.Behavior;

import java.awt.*;
import java.util.Date;


public class Creature extends Entity {
    private static final int SECONDS_TO_BOOST = 5;

    private boolean _boosted;
    private boolean _halfBoosted;
    private Date _boostStartedTime;


    public Creature(Behavior behavior) {
        super(behavior, 1000);
    }

    @Override
    public boolean canPassThrough(Entity entity) {
        return !(entity instanceof Wall
                 || entity instanceof Dike);
    }

    @Override
    public boolean canEat(Entity entity) {
        boolean isEatableThing = entity instanceof Booster
                                 || entity instanceof Dot
                                 || entity instanceof Fruit;
        boolean isEatableEnemy = _boosted
                                 && entity instanceof Enemy
                                 && !(entity instanceof ImmortalEnemy);
        return isEatableThing || isEatableEnemy;
    }

    @Override
    public void eat(Entity entity) {
        super.eat(entity);

        if (entity instanceof Booster) {
            setBoosted(true);
        }
    }

    private int getBoostedSeconds() {
        if (_boosted) {
            return (int) ((new Date().getTime() - _boostStartedTime.getTime()) / 1000);
        } else {
            return 0;
        }
    }

    @Override
    public Direction getNextDirection() {
        handleBoostedState();
        return super.getNextDirection();
    }

    private void handleBoostedState() {
        if (_boosted) {
            if (getBoostedSeconds() > SECONDS_TO_BOOST) {
                setBoosted(false);
            }

            if (getBoostedSeconds() >= SECONDS_TO_BOOST / 2) {
                if (isHalfBoosted()) {
                    redraw();
                }
                setHalfBoosted(false);
            }
        }
    }

    public boolean isBoosted() {
        return _boosted;
    }

    private void setBoosted(boolean boosted) {
        _boosted = boosted;
        _halfBoosted = boosted;

        _boostStartedTime = new Date();

        redraw();
    }

    private boolean isHalfBoosted() {
        return _halfBoosted;
    }

    private void setHalfBoosted(boolean value) {
        _halfBoosted = value;
    }

    @Override
    public void draw(Graphics2D graphics) {
        int halfCell = Math.round((float) getPosition().width / 2.0F);
        int r = getPosition().width / 3;

        graphics.setColor(Color.ORANGE);
        graphics.fillOval(halfCell - r, halfCell - r, r * 2, r * 2);

        graphics.setColor(isBoosted() ? Color.RED : Color.BLACK);
        graphics.fillOval(halfCell - (int) (r / 2.0F),
                          halfCell - (int) (r / 2.5F),
                          3, 3);
        graphics.fillOval(halfCell + (int) (r / 2.0F) - 3,
                          halfCell - (int) (r / 2.5F),
                          3, 3);

        if (!isHalfBoosted()) {
            graphics.setColor(Color.BLACK);
        }

        graphics.drawArc(halfCell - r + 3,
                         halfCell - r + 2,
                         r * 2 - 6,
                         r * 2 - 6,
                         90 * 2 + 20, 180 - 40 + 1);
    }

    @Override
    public Layer getLayer() {
        return Layer.MIDDLE;
    }
}
