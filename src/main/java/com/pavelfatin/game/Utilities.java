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
import java.util.Collection;
import java.util.Random;


public class Utilities {
    private static final Random _random = new Random();

    private Utilities() {
    }

    public static <T> T chooseElement(T[] elements) {
        return chooseElement(elements, null);
    }

    public static <T> T chooseElement(Collection<T> elements, T alternative) {
        return chooseElement((T[]) (elements.toArray()), alternative);
    }

    public static <T> T chooseElement(T[] elements, T alternative) {
        if (elements.length == 0) {
            return alternative;
        } else {
            int choiceIndex = _random.nextInt(elements.length);
            return elements[choiceIndex];
        }
    }

    public static void centerOnScreen(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = window.getSize();
        window.setLocation(
                (screenSize.width - size.width) / 2,
                (screenSize.height - size.height) / 2);
    }
}
