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

import com.pavelfatin.game.behavior.ControlledBehavior;
import com.pavelfatin.game.entity.Creature;
import com.pavelfatin.game.entity.Dot;
import com.pavelfatin.game.entity.Entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Labyrinth {
    private static final LayerComparator _layerComparator = new LayerComparator();

    private int _cellSize;
    private int _stepSize;
    private Rectangle _bounds = new Rectangle();
    private List<Entity> _entities = new ArrayList<Entity>();
    private IntersectionFinder<Entity> _intersectionFinder;
    private Entity _controlledEntity;


    Labyrinth(int cellSize, int stepSize) {
        _cellSize = cellSize;
        _stepSize = stepSize;
    }

    public void processTick() {
        for (Entity entity : _entities) {
            Direction decision = entity.getNextDirection();

            if (!Direction.None.equals(decision)) {
                move(entity, decision);
                processIntersectionsFor(entity);
            }
        }

        removeEatenEntities();
    }

    private void move(Entity entity, Direction decision) {
        Rectangle target = translate(entity.getPosition(), decision);
        entity.setPosition(target);
        _intersectionFinder.setRectangle(entity, target);
    }

    Rectangle translate(Rectangle rectangle, Direction direction) {
        return direction.translate(rectangle, _stepSize);
    }

    private void processIntersectionsFor(Entity entity) {
        for (Entity another : intersectedEntities(entity.getPosition())) {
            entity.processIntersection(another);
            another.processIntersection(entity);
        }
    }

    public void add(Entity entity, Point point) {
        Rectangle position = new Rectangle(point.x * _cellSize,
                                           point.y * _cellSize,
                                           _cellSize,
                                           _cellSize);
        entity.setSpace(new SpaceAdapter(this, entity));
        entity.setPosition(position);

        _entities.add(entity);
        Collections.sort(_entities, _layerComparator);

        updateBounds(position);

        updateIntersectionFinder();
    }

    private void updateBounds(Rectangle position) {
        Rectangle2D union = _bounds.createUnion(position);
        _bounds = union.getBounds();
    }

    private void updateIntersectionFinder() {
        _intersectionFinder = new IntersectionFinder<Entity>(
                _bounds.getSize(), new Dimension(_cellSize, _cellSize));
        for (Entity each : _entities) {
            _intersectionFinder.add(each, each.getPosition());
        }
    }

    private void removeEatenEntities() {
        Collection<Entity> eatenEntities = new ArrayList<Entity>();

        for (Entity entity : _entities) {
            if (entity.isEaten()) {
                eatenEntities.add(entity);
                _intersectionFinder.remove(entity);
            }
        }

        _entities.removeAll(eatenEntities);
    }

    public Collection<Direction> getAvailableDirections(Entity entity) {
        Collection<Direction> result = new ArrayList<Direction>();

        for (Direction direction : Direction.MOVING_DIRECTIONS) {
            Rectangle target = translate(entity.getPosition(), direction);
            if (canBeAt(entity, target)) {
                result.add(direction);
            }
        }

        return result;
    }

    public boolean canBeAt(Entity entity, Rectangle rectangle) {
        if (_bounds.contains(rectangle)) {
            for (Entity another : intersectedEntities(rectangle)) {
                if (!entity.equals(another)
                    && !entity.canPassThrough(another)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Direction getDirectionToward(Entity entity, Class type) {
        Compass compass = new Compass(this, entity, type);
        return compass.locate();
    }

    public void setControlledEntity(Entity controlledEntity) {
        _controlledEntity = controlledEntity;
    }

    public void navigate(Direction direction) {
        ((ControlledBehavior) _controlledEntity.getBehavior())
                .navigate(direction);
    }

    public boolean isWin() {
        return !isContains(Dot.class);
    }

    public boolean isLose() {
        return !isContains(Creature.class);
    }

    public int getScore() {
        return _controlledEntity.getCollectedScore();
    }

    public boolean isIntersects(Rectangle position, Class type) {
        for (Entity entity : intersectedEntities(position)) {
            if (type.isAssignableFrom(entity.getClass())) {
                return true;
            }
        }
        return false;
    }

    private Collection<Entity> intersectedEntities(Rectangle position) {
        return _intersectionFinder.intersection(position);
    }

    private boolean isContains(Class type) {
        return count(type) > 0;
    }

    private int count(Class type) {
        int result = 0;
        for (Entity object : _entities) {
            if (type.isAssignableFrom(object.getClass())) {
                result++;
            }
        }
        return result;
    }

    public Dimension getDimensions() {
        return _bounds.getSize();
    }

    public void render(Graphics2D graphics) {
        for (Entity entity : _entities) {
            entity.render(graphics);
        }
    }
}