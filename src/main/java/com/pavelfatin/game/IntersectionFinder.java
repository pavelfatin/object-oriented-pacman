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

import java.awt.*;
import java.util.*;


public class IntersectionFinder<T> {
    private Collection<Entry>[][] _matrix;
    private int _width;
    private int _height;
    private int _logicalHeight;
    private int _logicalWidth;


    public IntersectionFinder(Dimension size, Dimension cellSize) {
        _width = size.width;
        _height = size.height;

        _logicalHeight = (int) Math.ceil((double) size.height / cellSize.height);
        _logicalWidth = (int) Math.ceil((double) size.width / cellSize.width);

        initMatrix();
    }

    private void initMatrix() {
        _matrix = (Collection<Entry>[][])
                new Collection[_logicalWidth][_logicalHeight];

        for (int y = 0; y < _logicalHeight; y++) {
            for (int x = 0; x < _logicalWidth; x++) {
                _matrix[x][y] = new ArrayList<Entry>();
            }
        }
    }

    public void add(T object, Rectangle rectangle) {
        if (outOfBounds(rectangle)) {
            throw new IllegalArgumentException();
        }

        removeFromMatrix(object);

        addToMatrix(object, rectangle);
    }

    public void remove(T object) {
        if (matrixContains(object)) {
            removeFromMatrix(object);
        } else {
            throw new NoSuchElementException(
                    "Can't remove unexisted object: " + object);
        }
    }

    private boolean removeFromMatrix(T object) {
        boolean removed = false;

        for (int y = 0; y < _logicalHeight; y++) {
            for (int x = 0; x < _logicalWidth; x++) {
                Collection<Entry> entries
                        = new ArrayList<Entry>(_matrix[x][y]);
                for (Entry entry : entries) {
                    if (object.equals(entry.object)) {
                        _matrix[x][y].remove(entry);
                        removed = true;
                    }
                }
            }
        }

        return removed;
    }

    public Collection<T> intersection(Rectangle rectangle) {
        Rectangle logical = toLogical(rectangle.intersection(
                new Rectangle(0, 0, _width, _height)));

        Set<T> resultList = new HashSet<T>();

        for (int y = logical.y; y <= logical.y + logical.height; y++) {
            for (int x = logical.x; x <= logical.x + logical.width; x++) {
                for (Entry entry : _matrix[x][y]) {
                    if (rectangle.intersects(entry.rectangle)) {
                        resultList.add(entry.object);
                    }
                }
            }
        }

        return resultList;
    }

    public void setRectangle(T object, Rectangle rectangle) {
        if (outOfBounds(rectangle)) {
            throw new IllegalArgumentException();
        }

        if (removeFromMatrix(object)) {
            addToMatrix(object, rectangle);
        } else {
            throw new NoSuchElementException(
                    "Can't set rectangle for unexisted object: " + object);
        }
    }

    private boolean matrixContains(T object) {
        return matrixObjects().contains(object);
    }

    private boolean outOfBounds(Rectangle rectangle) {
        return (rectangle.x + rectangle.width > _width)
               || (rectangle.y + rectangle.height > _height)
               || (rectangle.x < 0)
               || (rectangle.y < 0);
    }

    private Rectangle toLogical(Rectangle rectangle) {
        int left = rectangle.x;
        int right = rectangle.x + rectangle.width - 1;
        int bottom = rectangle.y;
        int top = bottom + rectangle.height - 1;

        int logicalLeft = (left * _logicalWidth / _width);
        int logicalRight = (right * _logicalWidth / _width);
        int logicalBottom = (bottom * _logicalHeight / _height);
        int logicalTop = (top * _logicalHeight / _height);

        return new Rectangle(logicalLeft, logicalBottom,
                             logicalRight - logicalLeft,
                             logicalTop - logicalBottom);
    }

    private void addToMatrix(T object, Rectangle rectangle) {
        Rectangle logical = toLogical(rectangle);

        for (int y = logical.y; y <= logical.y + logical.height; y++) {
            for (int x = logical.x; x <= logical.x + logical.width; x++) {
                _matrix[x][y].add(new Entry(object, rectangle));
            }
        }
    }

    private Collection<Entry> matrixEntries() {
        Collection<Entry> result = new ArrayList<Entry>();

        for (int y = 0; y < _logicalHeight; y++) {
            for (int x = 0; x < _logicalWidth; x++) {
                for (Entry entry : _matrix[x][y]) {
                    result.add(entry);
                }
            }
        }

        return result;
    }

    Collection<T> matrixObjects() {
        Set<T> result = new HashSet<T>();
        for (Entry entry : matrixEntries()) {
            result.add(entry.object);
        }
        return result;
    }

    Rectangle rectangleFor(T object) {
        Collection<Entry> entries = matrixEntries();
        for (Entry entry : entries) {
            if (object.equals(entry.object)) {
                return entry.rectangle;
            }
        }
        throw new NoSuchElementException();
    }

    Collection<Point> logicalPointsFor(T object) {
        Collection<Point> result = new ArrayList<Point>();

        for (int y = 0; y < _logicalHeight; y++) {
            for (int x = 0; x < _logicalWidth; x++) {
                for (Entry entry : _matrix[x][y]) {
                    if (object.equals(entry.object)) {
                        result.add(new Point(x, y));
                    }
                }
            }
        }

        return result;
    }


    private class Entry {
        T object;
        Rectangle rectangle;


        Entry(T object, Rectangle rectangle) {
            this.object = object;
            this.rectangle = rectangle;
        }
    }
}