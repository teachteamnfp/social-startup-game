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

package edu.bsu.cybersec.core;

import com.google.common.collect.ImmutableMap;
import edu.bsu.cybersec.core.ui.ImageCache;
import playn.core.Image;
import tripleplay.entity.Entity;

import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class PlayableWorldFactory {

    private static final int DAYS_UNTIL_GAME_END = 14;

    private final Map<EmployeeProfile, Image> DEVELOPERS;

    private final GameWorld.Systematized world = new GameWorld.Systematized();
    private final GameConfig config;

    public PlayableWorldFactory(ImageCache imageCache, GameConfig config) {
        DEVELOPERS =
                ImmutableMap.of(
                        EmployeeProfile.firstName("Esteban").lastName("Cortez")
                                .withDegree("Bachelors in Computer Science").from("Ball State University")
                                .bio("Esteban worked in a factory until he was 33, then he went to college and decided to get involved in software development."),
                        imageCache.ESTEBAN,

                        EmployeeProfile.firstName("Nancy").lastName("Stevens")
                                .withDegree("Bachelors in Computer Science").from("Georgetown University")
                                .withDegree("Masters in Computer Security").from("Purdue University")
                                .bio("Nancy has a popular podcast about being a woman in technology."),
                        imageCache.NANCY,

                        EmployeeProfile.firstName("Jerry").lastName("Chen")
                                .bio("Jerry interned at a local company in high school and has been working as a software developer ever since."),
                        imageCache.JERRY);
        this.config = checkNotNull(config);
    }

    public GameWorld.Systematized createPlayableGameWorld() {
        initializeWorld();
        return world;
    }

    private void initializeWorld() {
        world.gameTimeSystem.setGameTimeUnitsPerRealClockUnits(ClockUtils.SECONDS_PER_HOUR * 2);
        world.featureGenerationSystem.nextFeatureNumber(1);
        world.featureDevelopmentSystem.inefficiencyFactor.update(1 / 3f);
        world.exposure.update(0.10f);
        world.users.update(1000f);
        makeExistingFeature();
        makeDevelopers(3);
        setEndTime();
        new DefaultNarrativeScript().createIn(world, config);
    }

    private void setEndTime() {
        Entity end = world.create(true).add(world.timeTrigger, world.event);
        // SECONDS_PER_DAY = 60*60*24 = 8640.
        // DAYS_UNTIL_GAME_ENDS = 14
        // Product of these is 120,960, which is well within integers. The warning must be because
        // it doesn't know what the range of "now" is. So, we suppress the warning.
        //noinspection NumericOverflow
        int gameEnd = world.gameTime.get().now + ClockUtils.SECONDS_PER_DAY * DAYS_UNTIL_GAME_END;
        world.timeTrigger.set(end.id, gameEnd);
        world.event.set(end.id, new Runnable() {
            @Override
            public void run() {
                world.onGameEnd.emit();
            }
        });
        world.gameEnd.update(gameEnd);
    }

    private void makeDevelopers(int number) {
        checkArgument(number >= 0);
        Iterator<EmployeeProfile> profiles = DEVELOPERS.keySet().iterator();
        for (int i = 0; i < number; i++) {
            Entity e = makeDeveloper(i, profiles.next());
            world.workers.add(e);
        }
    }

    private Entity makeDeveloper(final int number, final EmployeeProfile profile) {
        Entity developer = world.create(true)
                .add(world.employeeNumber,
                        world.developmentSkill,
                        world.tasked,
                        world.maintenanceSkill,
                        world.profile,
                        world.image);
        world.employeeNumber.set(developer.id, number);
        world.tasked.set(developer.id, Task.MAINTENANCE);
        world.developmentSkill.set(developer.id, 5);
        world.maintenanceSkill.set(developer.id, 5);
        world.profile.set(developer.id, profile);
        world.image.set(developer.id, DEVELOPERS.get(profile));
        return developer;
    }

    private void makeExistingFeature() {
        Entity userGeneratingEntity = FeatureFactory.in(world).makeCompletedFeature(0);
        world.usersPerHour.set(userGeneratingEntity.id, 1);
        world.vulnerability.set(userGeneratingEntity.id, 10);
    }
}
