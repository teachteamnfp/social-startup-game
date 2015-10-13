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

import com.google.common.collect.Sets;
import edu.bsu.cybersec.core.GameWorld;
import edu.bsu.cybersec.core.SystemPriority;
import playn.core.Clock;
import tripleplay.entity.Entity;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;

import java.util.Comparator;
import java.util.SortedSet;

public class FeatureGroup extends InteractionAreaGroup {
    public FeatureGroup(final GameWorld gameWorld) {
        super(AxisLayout.vertical());
        new tripleplay.entity.System(gameWorld, SystemPriority.UI_LEVEL.value) {

            @Override
            protected boolean isInterested(Entity entity) {
                return entity.has(gameWorld.featureNumber);
            }

            @Override
            protected void update(Clock clock, Entities entities) {
                super.update(clock, entities);
                removeAll();
                addLabelsSortedByFeatureNumber(entities);
            }

            private void addLabelsSortedByFeatureNumber(Entities entities) {
                SortedSet<Integer> sortedFeatures = Sets.newTreeSet(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer id1, Integer id2) {
                        return gameWorld.featureNumber.get(id1) - gameWorld.featureNumber.get(id2);
                    }
                });
                for (int i = 0, limit = entities.size(); i < limit; i++) {
                    sortedFeatures.add(entities.get(i));
                }
                for (Integer id : sortedFeatures) {
                    add(makeLabelFor(id));
                }
            }

            private Label makeLabelFor(int id) {
                final Entity entity = gameWorld.entity(id);
                final int number = gameWorld.featureNumber.get(id);
                final String initial = "Feature " + number + " (" + gameWorld.name.get(id) + ") ";

                if (entity.has(gameWorld.developmentProgress)) {
                    final int progress = (int) (gameWorld.developmentProgress.get(id) / gameWorld.goal.get(id) * 100);
                    return new Label(initial + " progress : " + progress + " %");
                } else {
                    return new Label(initial + " done!");
                }
            }
        };
    }
}