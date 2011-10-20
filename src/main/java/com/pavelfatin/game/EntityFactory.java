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

import com.pavelfatin.game.behavior.*;
import com.pavelfatin.game.entity.*;

import java.awt.*;


class EntityFactory {
    private static final Class[] BEHAVIORS
            = new Class[]{RoamingBehavior.class,
                          RandomBehavior.class,
                          GlancingBehavior.class};

    private static final Color[] COLORS
            = new Color[]{Color.RED, Color.GREEN, Color.YELLOW,
                          Color.CYAN, Color.PINK, Color.MAGENTA};


    public Creature createCreature() {
        return new Creature(new ControlledBehavior());
    }

    public Dot createDot() {
        return new Dot();
    }

    public RoamingDot createRoamingDot() {
        return new RoamingDot(createRandomBehavior(BEHAVIORS));
    }

    public Booster createBooster() {
        return new Booster();
    }

    public Dike createDike() {
        return new Dike(createRandomBehavior(BEHAVIORS));
    }

    public Fruit createFruit() {
        return new Fruit(Utilities.chooseElement(COLORS));
    }

    public Wall createWall() {
        return new Wall();
    }

    public Enemy createEnemy() {
        Color color = Utilities.chooseElement(COLORS);
        return new Enemy(createRandomBehavior(BEHAVIORS), color);
    }

    public Enemy createImmortalEnemy() {
        Color color = Utilities.chooseElement(COLORS);
        return new ImmortalEnemy(createRandomBehavior(BEHAVIORS), color);
    }

    public Enemy createSmartEnemy() {
        Color color = Utilities.chooseElement(COLORS);
        return new Enemy(new DirectedBehavior(), color);
    }

    private Behavior createRandomBehavior(Class[] behaviors) {
        try {
            Class behaviorClass = Utilities.chooseElement(behaviors);
            return (Behavior) behaviorClass.newInstance();
        } catch (InstantiationException
                e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException
                e) {
            throw new RuntimeException(e);
        }
    }
}
