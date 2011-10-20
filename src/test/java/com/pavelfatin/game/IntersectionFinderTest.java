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

import junit.framework.TestCase;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;


public class IntersectionFinderTest extends TestCase {
    private static final Dimension SIZE = new Dimension(600, 400);
    private static final Dimension CELL_SIZE = new Dimension(22, 33);

    private IntersectionFinder<Object> _field;


    @Override
    protected void setUp() {
        _field = new IntersectionFinder<Object>(SIZE, CELL_SIZE);
    }

    public void testInitialState() {
        assertContentIs();
        assertIntersectionIs(0, 0, SIZE.width, SIZE.height);
    }

    public void testContains() {
        assertNotContains(one());
        assertNotContains(two());

        add(one(), 100, 95, 18, 19);
        assertContains(one());
        assertNotContains(two());

        add(two(), 114, 94, 28, 9);
        assertContains(one());
        assertContains(two());
    }

    public void testAddition() {
        assertExcludes(one(), two());

        add(one(), 0, 200, 11, 50);
        assertContentIs(one());
        assertExcludes(two());

        add(two(), 10, 300, 21, 60);
        assertContentIs(one(), two());
    }

    public void testEqualObjectsAddition() {
        add(one(), 0, 200, 11, 50);
        add(two(), 0, 200, 11, 50);

        add(one(), 10, 100, 15, 60);

        assertContentIs(one(), two());

        assertRectangleIs(one(), 10, 100, 15, 60);
        assertRectangleIs(two(), 0, 200, 11, 50);
    }

    public void testRemoval() {
        add(one(), 114, 94, 80, 19);
        add(two(), 211, 205, 70, 140);

        remove(one());
        assertExcludes(one());
        assertContentIs(two());

        remove(two());
        assertContentIs();
        assertExcludes(one(), two());
    }

    public void testTwoObjectsManagementWithSameRectangles() {
        add(one(), 400, 204, 80, 59);
        add(two(), 400, 204, 80, 59);

        assertContentIs(one(), two());

        remove(one());
        assertExcludes(one());
        assertContentIs(two());

        remove(two());
        assertExcludes(one(), two());
        assertContentIs();
    }

    public void testGetRectangle() {
        add(one(), 400, 204, 80, 59);
        add(two(), 114, 94, 80, 19);

        assertRectangleIs(one(), 400, 204, 80, 59);
        assertRectangleIs(two(), 114, 94, 80, 19);
    }

    public void testMotion() {
        add(one(), 400, 204, 80, 59);
        add(two(), 114, 94, 80, 19);

        move(one(), 114, 94, 80, 19);

        assertRectangleIs(one(), 114, 94, 80, 19);
        assertRectangleIs(two(), 114, 94, 80, 19);
    }

    public void testUnexistedObjects() {
        Object unexisted = new Object();

        try {
            move(unexisted, 0, 0, 0, 0);
            fail();
        } catch (NoSuchElementException e) {
        }

        try {
            remove(unexisted);
            fail();
        } catch (NoSuchElementException e) {
        }
    }

    public void testBoundConstraint() {
        add("foo", 0, 0, 1, 1);
        remove("foo");

        add("foo", SIZE.width - 1, 0, 1, 1);
        remove("foo");

        add("foo", SIZE.height - 1, 0, 1, 1);
        remove("foo");

        try {
            add(one(), -1, 0, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            add(one(), 0, -1, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            add(one(), SIZE.width, 0, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            add(one(), 0, SIZE.height, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertExcludes(one());
        assertContentIs();

        add(one(), 0, 0, 1, 1);

        try {
            move(one(), SIZE.width, 0, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            add(one(), SIZE.width, 0, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertContentIs(one());

        assertRectangleIs(one(), 0, 0, 1, 1);
    }

    public void testObjectFound() {
        assertIntersectionIs(0, 0, 50, 60);
        assertIntersectionIs(100, 200, 50, 60);

        add(one(), 10, 15, 10, 20);

        assertIntersectionIs(0, 0, 50, 60, one());
        assertIntersectionIs(100, 200, 50, 60);
    }

    public void testObjectFoundAfterMoved() {
        add(one(), 10, 15, 10, 20);

        move(one(), 100, 150, 10, 20);

        assertIntersectionIs(100, 120, 50, 60, one());
        assertIntersectionIs(0, 0, 50, 60);
    }

    public void testIntersection() {
        add(one(), 10, 15, 10, 20);

        assertIntersectionIs(18, 20, 10, 10, one());
        assertIntersectionIs(19, 20, 10, 10, one());

        assertIntersectionIs(0, 20, 10, 10);
        assertIntersectionIs(20, 20, 10, 10);

        assertIntersectionIs(15, 33, 50, 50, one());
        assertIntersectionIs(15, 34, 50, 50, one());

        assertIntersectionIs(15, 5, 50, 10);
        assertIntersectionIs(15, 35, 50, 50);
    }

    public void testExceptionalSearchDimensions() {
        add(one(), 10, 15, 10, 20);

        assertIntersectionIs(15, 0, 0, 50);
        assertIntersectionIs(0, 20, 50, 0);
        assertIntersectionIs(15, 20, 0, 0);

        assertIntersectionIs(15, 0, 1, 50, one());
        assertIntersectionIs(0, 20, 50, 1, one());
        assertIntersectionIs(15, 20, 1, 1, one());
    }

    public void testExceptionalDimensions() {
        add(one(), 10, 15, 0, 20);
        assertContains(one());
        assertIntersectionIs(0, 0, 50, 50);

        move(one(), 10, 15, 15, 0);
        assertIntersectionIs(0, 0, 50, 50);

        move(one(), 10, 15, 0, 0);
        assertIntersectionIs(0, 0, 50, 50);

        move(one(), 10, 15, 1, 20);
        assertIntersectionIs(0, 0, 50, 50, one());

        move(one(), 10, 15, 15, 1);
        assertIntersectionIs(0, 0, 50, 50, one());

        move(one(), 10, 15, 1, 1);
        assertIntersectionIs(0, 0, 50, 50, one());
    }

    public void testNoSearchBound() {
        add(one(), 10, 15, 20, 20);

        assertIntersectionIs(-20, -15, 50, 50, one());
        assertIntersectionIs(5, 5, SIZE.width + 20, SIZE.height + 50, one());
    }

    public void testMultiplyObjectSearch() {
        int objectWidth = SIZE.width / 10;
        int objectHeight = SIZE.height / 10;


        add(one(), SIZE.width / 4 - objectWidth / 2,
            SIZE.height / 4 - objectHeight / 2,
            objectWidth, objectHeight);

        add(two(), SIZE.width / 2 - objectWidth / 2,
            SIZE.height / 2 - objectHeight / 2,
            objectWidth, objectHeight);

        add(three(), SIZE.width / 4 * 3 - objectWidth / 2,
            SIZE.height / 4 * 3 - objectHeight / 2,
            objectWidth, objectHeight);

        assertIntersectionIs(0, 0, 2 * SIZE.width / 3, 2 * SIZE.height / 3,
                             one(),
                             two());

        assertIntersectionIs(SIZE.width - 2 * SIZE.width / 3,
                             SIZE.height - 2 * SIZE.height / 3,
                             2 * SIZE.width / 3, 2 * SIZE.height / 3, two(),
                             three());

        assertIntersectionIs(0, 2 * SIZE.height / 5, SIZE.width, 5);
        assertIntersectionIs(2 * SIZE.width / 5, 0, 5, SIZE.height);
    }

    public void testSearchOnEdges() {
        add(one(), SIZE.width - 1, 50, 1, 1);
        add(two(), 50, SIZE.height - 1, 1, 1);
        add(three(), 0, 50, 1, 1);
        add("four", 50, 0, 1, 1);

        assertIntersectionIs(SIZE.width - 1, 50, 1, 1, one());
        assertIntersectionIs(50, SIZE.height - 1, 1, 1, two());
        assertIntersectionIs(0, 50, 1, 1, three());
        assertIntersectionIs(50, 0, 1, 1, "four");
    }

    public void testLogicalPositions() {
        _field = new IntersectionFinder<Object>(
                new Dimension(600, 400), new Dimension(30, 20));

        add(one(), 0, 0, 30, 20);
        add(two(), 30, 0, 30, 20);
        add(three(), 0, 20, 30, 20);
        add("corner", SIZE.width - 30, SIZE.height - 20, 30, 20);

        assertLogicalPositionsIs(one(), new Point(0, 0));
        assertLogicalPositionsIs(two(), new Point(1, 0));
        assertLogicalPositionsIs(three(), new Point(0, 1));
        assertLogicalPositionsIs("corner",
                                 new Point(SIZE.width / 30 - 1,
                                           SIZE.height / 20 - 1));
    }

    public void testCompoundLogicalPosition() {
        _field = new IntersectionFinder<Object>(
                new Dimension(600, 400), new Dimension(30, 20));

        add("big", 30, 40, 30 * 3, 20 * 2);

        assertLogicalPositionsIs("big",
                                 new Point(1, 2),
                                 new Point(1, 3),
                                 new Point(2, 2),
                                 new Point(2, 3),
                                 new Point(3, 2),
                                 new Point(3, 3));
    }

    public void testIntermediateLogicalPositions() {
        _field = new IntersectionFinder<Object>(
                new Dimension(600, 400), new Dimension(10, 10));

        add(one(), 0, 0, 1, 1);
        assertLogicalPositionsIs(one(), new Point(0, 0));

        move(one(), 9, 9, 1, 1);
        assertLogicalPositionsIs(one(), new Point(0, 0));

        move(one(), 10, 10, 1, 1);
        assertLogicalPositionsIs(one(), new Point(1, 1));
    }

    public void testIntermediateLogicalDimensions() {
        _field = new IntersectionFinder<Object>(
                new Dimension(600, 400), new Dimension(10, 10));

        add(one(), 0, 0, 10, 10);
        assertLogicalPositionsIs(one(), new Point(0, 0));

        add(one(), 0, 0, 21, 10);
        assertLogicalPositionsIs(one(),
                                 new Point(0, 0),
                                 new Point(1, 0),
                                 new Point(2, 0));

        add(one(), 0, 0, 11, 11);
        assertLogicalPositionsIs(one(),
                                 new Point(0, 0),
                                 new Point(1, 0),
                                 new Point(0, 1),
                                 new Point(1, 1));
    }

    private Object one() {
        return "one";
    }

    private Object two() {
        return "two";
    }

    private Object three() {
        return "three";
    }

    private Collection<Object> intersection(Rectangle rectangle) {
        return _field.intersection(rectangle);
    }

    private void assertLogicalPositionsIs(Object object, Point... points) {
        Collection<Point> foundPoints = _field.logicalPointsFor(object);

        assertEquals(points.length, foundPoints.size());

        for (Point point : points) {
            assertTrue(foundPoints.contains(point));
        }
    }

    private void assertIntersectionIs(int x, int y, int width, int height,
                                      Object... expected) {
        Collection<Object> foundObjects
                = intersection(new Rectangle(x, y, width, height));
        assertEquals(expected.length, foundObjects.size());
        assertTrue(foundObjects.containsAll(Arrays.asList(expected)));
    }

    private void add(Object object, int x, int y, int width, int height) {
        _field.add(object, new Rectangle(x, y, width, height));
    }

    private void remove(Object object) {
        _field.remove(object);
    }

    private void move(Object object, int x, int y, int width, int height) {
        _field.setRectangle(object, new Rectangle(x, y, width, height));
    }

    private void assertSizeIs(int size) {
        assertEquals(size, _field.matrixObjects().size());
    }

    private void assertContentIs(Object... objects) {
        assertSizeIs(objects.length);

        for (Object object : objects) {
            assertContains(object);
        }
    }

    private void assertExcludes(Object... objects) {
        for (Object object : objects) {
            assertNotContains(object);
        }
    }

    private void assertContains(Object object) {
        assertTrue(_field.matrixObjects().contains(object));
    }

    private void assertNotContains(Object object) {
        assertFalse(_field.matrixObjects().contains(object));
    }

    private void assertRectangleIs(Object object, int x, int y,
                                   int width, int height) {
        assertEquals(new Rectangle(x, y, width, height),
                     _field.rectangleFor(object));
    }
}
