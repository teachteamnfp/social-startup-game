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

package edu.bsu.cybersec.core.ui;

import edu.bsu.cybersec.core.SimGame;
import playn.core.Game;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;

public class EndScreen extends ScreenStack.UIScreen {

    @Override
    protected Root createRoot() {
        return iface.createRoot(AxisLayout.vertical(), SimGameStyle.newSheet(game().plat.graphics()), layer)
                .setSize(size())
                .add(new Label("The game is over")
                        .setStyles(Style.COLOR.is(Colors.WHITE)));
    }

    @Override
    public Game game() {
        return SimGame.game;
    }
}
