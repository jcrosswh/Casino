/*
 * Copyright (c) 2018, Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package us.xwhite.casino;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Simulator class that will gather information about number of rounds and
 * maximum stake for a player
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class Simulator {

    /**
     * Initial number of rounds a player will play
     */
    public static final int INIT_DURATION = 250;

    /**
     * Initial stake for players in betting units
     */
    public static final int INIT_STAKE = 100;

    /**
     * Number or rounds of games to play
     */
    public static final int SAMPLES = 50;

    private final List<Integer> durations;

    private int totalDurations;

    private final List<Integer> maxima;

    private int totalMaxima;

    private final List<Integer> finalStakes;

    private int totalFinalStakes;

    private final Player.Type playerType;

    private final Game game;
    
    private BiFunction<List<Integer>, Integer, Integer> average = 
        (list, total) -> {

            if (list.isEmpty()) {
                return 0;
            }

            return total / list.size();
        };
    
    private BiFunction<List<Integer>, Integer, Integer> nthPercentile = 
        (list, percentile) -> {
                if (percentile < 0 || percentile > 100) {
                    throw new IllegalArgumentException("Please use a value between 0 and 100");
                }

                Collections.sort(list);
                int location = (100 - percentile) * list.size() / 100;
                return list.get(location);
            };

    /**
     * Create a new simulator
     *
     * @param game Game for player to play
     * @param player Betting strategy player
     */
    public Simulator(Game game, Player.Type player) {
        this.playerType = player;
        this.game = game;

        durations = new LinkedList<>();
        totalDurations = 0;
        maxima = new LinkedList<>();
        totalMaxima = 0;
        finalStakes = new LinkedList<>();
        totalFinalStakes = 0;
    }

    /**
     * Play one session of the game and capture statistics
     *
     * @return List of player stakes after one round at the table. The last
     * value will contain the player's ending stake.
     */
    private List<Integer> session() {

        List<Integer> stakeValues = new LinkedList<>();
        Player player = new Player.PlayerBuilder()
                .type(playerType)
                .table(game.getTable())
                .stake(INIT_STAKE)
                .roundsToGo(INIT_DURATION)
                .build();
        while (player.playing()) {
            game.cycle(player);
            stakeValues.add(player.getStake());
        }

        stakeValues.add(player.getStake());
        totalFinalStakes += player.getStake();
        return stakeValues;
    }

    /**
     * Run a number of simulations
     */
    public void gather() {

        for (int session = 0; session < SAMPLES; session++) {
            List<Integer> sessionInfo = session();

            finalStakes.add((Integer) ((LinkedList) sessionInfo).removeLast());

            durations.add(sessionInfo.size());
            totalDurations += sessionInfo.size();

            int max = Collections.max(sessionInfo);
            maxima.add(max);
            totalMaxima += max;
        }
    }

    /**
     * Get the maximum durations that a player sat at a table for
     *
     * @return Maximum durations
     */
    public int getMaxDurations() {
        return Collections.max(durations);
    }

    /**
     * Get the maximum stake that a player finished with
     *
     * @return Maximum stake
     */
    public int getMaxStake() {
        return Collections.max(maxima);
    }

    /**
     * Get the maximum value of player's final stakes
     *
     * @return Maximum ending values
     */
    public int getMaxFinalStake() {
        return Collections.max(finalStakes);
    }

    /**
     * Get the average time the player sat at the table
     *
     * @return Average time the player sat at a table
     */
    public int getAverageDurations() {

        return average.apply(durations, totalDurations);
    }

    /**
     * Get the average maximum stake a player held
     *
     * @return Average maximum stake
     */
    public int getAverageMaximumStake() {
        
        return average.apply(maxima, totalMaxima);
    }

    /**
     * Get the average final stake a player held
     *
     * @return Average final stake
     */
    public int getAverageFinalStake() {

        return average.apply(finalStakes, totalFinalStakes);
    }

    /**
     * Get the nth percentile of the durations
     *
     * @param percentile The percentile to find
     * @return The percentile from the durations
     * @throws IllegalArgumentException Thrown if percentile is not between 0
     * and 100
     */
    public int getNthPercentileDurations(int percentile) {

        return nthPercentile.apply(durations, percentile);
    }

    /**
     * Get the nth percentile of the maximum stakes
     *
     * @param percentile The percentile to find
     * @return The percentile from the maximum stakes
     * @throws IllegalArgumentException Thrown if percentile is not between 0
     * and 100
     */
    public int getNthPercentileMaximumStake(int percentile) {

        return nthPercentile.apply(maxima, percentile);
    }
    
    /**
     * Get the nth percentile of the final stakes
     *
     * @param percentile The percentile to find
     * @return The percentile from the final stakes
     * @throws IllegalArgumentException Thrown if percentile is not between 0
     * and 100
     */
    public int getNthPercentileFinalStake(int percentile) {

        return nthPercentile.apply(finalStakes, percentile);
    }
}
