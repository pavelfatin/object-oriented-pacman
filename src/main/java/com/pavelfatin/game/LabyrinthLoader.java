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

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


class LabyrinthLoader {
    private static final EntityFactory _factory = new EntityFactory();
    private Entity _controlledEntity;


    Labyrinth load(URL url, int cellSize, int stepSize) {
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                return read(reader, cellSize, stepSize);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Labyrinth read(BufferedReader reader, int cellSize, int stepSize) throws IOException {
        Labyrinth labyrinth = new Labyrinth(cellSize, stepSize);

        int row = 0;
        while (reader.ready()) {
            char[] chars = reader.readLine().toCharArray();

            int column = 0;
            for (char aChar : chars) {
                Entity entity = createEntity(aChar);
                labyrinth.add(entity, new Point(column, row));

                column++;
            }

            row++;
        }

        labyrinth.setControlledEntity(_controlledEntity);

        return labyrinth;
    }

    private Entity createEntity(char character) {
        switch (character) {
            case 'C':
                _controlledEntity = _factory.createCreature();
                return _controlledEntity;
            case 'E':
                return _factory.createEnemy();
            case 'I':
                return _factory.createImmortalEnemy();
            case 'S':
                return _factory.createSmartEnemy();
            case 'F':
                return _factory.createFruit();
            case '.':
                return _factory.createDot();
            case 'D':
                return _factory.createDike();
            case 'R':
                return _factory.createRoamingDot();
            case 'B':
                return _factory.createBooster();
            case '#':
                return _factory.createWall();
            default:
                throw new RuntimeException(
                        "Unknown entity: '" + character + "'");
        }
    }
}
