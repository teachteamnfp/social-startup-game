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

package edu.bsu.cybersec.html;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.bsu.cybersec.core.ui.PlatformSpecificDateFormatter;

public class GWTDateFormatter implements PlatformSpecificDateFormatter {
    private final DateTimeFormat format = DateTimeFormat.getFormat(FORMAT_STRING);

    @Override
    public String format(long ms) {
        java.util.Date javaDate = new java.util.Date(ms);
        return format.format(javaDate);
    }
}
