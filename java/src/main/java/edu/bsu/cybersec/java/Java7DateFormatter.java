/*
 * Copyright 2015 Paul Gestwicki
 *
 * This file is part of The Social Startup Game
 *
 * The Social Startup Game is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Social Startup Game is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with The Social Startup Game.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.bsu.cybersec.java;

import edu.bsu.cybersec.core.ui.PlatformSpecificDateFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Java7DateFormatter implements PlatformSpecificDateFormatter {

    private final SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING);

    public Java7DateFormatter() {
        final GregorianCalendar now = new GregorianCalendar();
        format.setCalendar(now);
    }

    @Override
    public String format(long ms) {
        Calendar c = GregorianCalendar.getInstance();
        c.setTimeInMillis(ms);
        return format.format(c.getTime());
    }
}
