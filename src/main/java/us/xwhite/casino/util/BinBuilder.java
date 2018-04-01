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
package us.xwhite.casino.util;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import us.xwhite.casino.Outcome;
import us.xwhite.casino.RouletteGame;
import us.xwhite.casino.Wheel;

/**
 * Helper class to generate all the outcomes for a Roulette game. The
 * {@link #buildBins(Wheel) buildBins} method is a useful entry method to call
 * all other methods.
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class BinBuilder {

    private final static ResourceBundle BETS;

    static {
        BETS = ResourceBundle.getBundle("BetNames", Locale.ENGLISH);
    }

    /**
     * Build all outcomes and place into bins for a wheel
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void buildBins(Wheel wheel) {

        generateStraightBets(wheel);
        generateSplitBets(wheel);
        generateStreetBets(wheel);
        generateConerBets(wheel);
        generateLineBets(wheel);
        generateDozenBets(wheel);
        generateColumnBets(wheel);
        generateEvenMoneyBets(wheel);
    }

    /**
     * Generate bets on individual bins
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateStraightBets(Wheel wheel) {

        for (int i = 0; i < Wheel.MAX_BINS - 1; i++) {
            wheel.add(i, new Outcome(Integer.toString(i), RouletteGame.STRAIGHT_BET_ODDS));
        }

        wheel.add(Wheel.MAX_BINS - 1, new Outcome("00", RouletteGame.STRAIGHT_BET_ODDS));
    }

    /**
     * Generate bets between individual bins
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateSplitBets(Wheel wheel) {

        for (int row = 0; row < 12; row++) {

            int n = 3 * row + 1;
            Outcome outcome = new Outcome(MessageFormat.format(BETS.getString("bet.split"), n, n + 1), RouletteGame.SPLIT_BET_ODDS);
            wheel.add(n, outcome);
            wheel.add(n + 1, outcome);

            n = 3 * row + 2;
            outcome = new Outcome(MessageFormat.format(BETS.getString("bet.split"), n, n + 1), RouletteGame.SPLIT_BET_ODDS);
            wheel.add(n, outcome);
            wheel.add(n + 1, outcome);
        }
        
        for (int bin = 1; bin < 34; bin++) {
            
            Outcome outcome = new Outcome(MessageFormat.format(BETS.getString("bet.split"), bin, bin + 3), RouletteGame.SPLIT_BET_ODDS);
            wheel.add(bin, outcome);
            wheel.add(bin + 3, outcome);
        }
    }

    /**
     * Generate street bets on bins
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateStreetBets(Wheel wheel) {

        for (int row = 0; row < 12; row++) {

            int n = 3 * row + 1;
            Outcome outcome = new Outcome(MessageFormat.format(BETS.getString("bet.street"), n, n + 1, n + 2), RouletteGame.STREET_BET_ODDS);
            wheel.add(n, outcome);
            wheel.add(n + 1, outcome);
            wheel.add(n + 2, outcome);
        }
    }

    /**
     * Generate bets on corners of bins
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateConerBets(Wheel wheel) {

        for (int row = 0; row < 11; row++) {

            int n = 3 * row + 1;
            Outcome outcome = new Outcome(MessageFormat.format(BETS.getString("bet.corner"), n, n + 1, n + 3, n + 4), RouletteGame.CORNER_BET_ODDS);
            wheel.add(n, outcome);
            wheel.add(n + 1, outcome);
            wheel.add(n + 3, outcome);
            wheel.add(n + 4, outcome);

            n = 3 * row + 2;
            outcome = new Outcome(MessageFormat.format(BETS.getString("bet.corner"), n, n + 1, n + 3, n + 4), RouletteGame.CORNER_BET_ODDS);
            wheel.add(n, outcome);
            wheel.add(n + 1, outcome);
            wheel.add(n + 3, outcome);
            wheel.add(n + 4, outcome);
        }
    }

    /**
     * Generate line bets
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateLineBets(Wheel wheel) {

        for (int row = 0; row < 11; row++) {

            int n = 3 * row + 1;
            Outcome outcome = new Outcome(MessageFormat.format(BETS.getString("bet.line"), n, n + 1, n + 2, n + 3, n + 4, n + 5), RouletteGame.LINE_BET_ODDS);
            for (int i = 0; i < 6; i++) {
                wheel.add(n + i, outcome);
            }
        }
    }

    /**
     * Generate dozen bets
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateDozenBets(Wheel wheel) {

        for (int dozen = 0; dozen < 3; dozen++) {

            Outcome outcome = new Outcome(MessageFormat.format(BETS.getString("bet.dozen"), dozen + 1), RouletteGame.DOZEN_BET_ODDS);
            for (int i = 0; i < 12; i++) {
                wheel.add(12 * dozen + i + 1, outcome);
            }
        }
    }

    /**
     * Generate column bets
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateColumnBets(Wheel wheel) {

        for (int column = 0; column < 3; column++) {

            Outcome outcome = new Outcome(MessageFormat.format(BETS.getString("bet.column"), column + 1), RouletteGame.COLUMN_BET_ODDS);
            for (int i = 0; i < 12; i++) {
                wheel.add(3 * i + column + 1, outcome);
            }
        }
    }

    /**
     * Generate all even money bets
     *
     * @param wheel Wheel to place outcomes into
     */
    public static void generateEvenMoneyBets(Wheel wheel) {

        Outcome redOutcome = new Outcome(BETS.getString("bet.red"), RouletteGame.EVEN_MONEY_BET_ODDS);
        Outcome blackOutcome = new Outcome(BETS.getString("bet.black"), RouletteGame.EVEN_MONEY_BET_ODDS);
        Outcome evenOutcome = new Outcome(BETS.getString("bet.even"), RouletteGame.EVEN_MONEY_BET_ODDS);
        Outcome oddOutcome = new Outcome(BETS.getString("bet.odd"), RouletteGame.EVEN_MONEY_BET_ODDS);
        Outcome highOutcome = new Outcome(BETS.getString("bet.high"), RouletteGame.EVEN_MONEY_BET_ODDS);
        Outcome lowOutcome = new Outcome(BETS.getString("bet.low"), RouletteGame.EVEN_MONEY_BET_ODDS);

        for (int i = 1; i < 37; i++) {

            if (i < 19) {
                wheel.add(i, lowOutcome);
            } else {
                wheel.add(i, highOutcome);
            }

            if (i % 2 == 0) {
                wheel.add(i, evenOutcome);
            } else {
                wheel.add(i, oddOutcome);
            }

            if (Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36).contains(i)) {
                wheel.add(i, redOutcome);
            } else {
                wheel.add(i, blackOutcome);
            }
        }
    }
}
