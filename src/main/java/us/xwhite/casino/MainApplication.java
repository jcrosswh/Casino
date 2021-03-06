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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Main entry point to application
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class MainApplication {

    /**
     * Main entry point to application
     *
     * @param args No command line arguments are used in this application
     */
    public static void main(String[] args) {

        Wheel wheel = new Wheel.WheelBuilder().rng(new Random()).build();
        Table table = new Table((int) (Simulator.INIT_STAKE * .75));
        Game game = new RouletteGame(wheel, table);

        Simulator simulator = new Simulator(game, Player.Type.Martingale);
        simulator.gather();
        List<Integer> durations = new ArrayList<>(simulator.getDurations());
        List<Integer> maxStakes = new ArrayList<>(simulator.getMaxima());
        List<Integer> finalStakes = new ArrayList<>(simulator.getFinalStakes());

        System.out.println(new StringBuilder()
                .append("Starting durations: ").append(Simulator.INIT_DURATION).append(System.lineSeparator())
                .append("Starting stake: ").append(Simulator.INIT_STAKE).append(System.lineSeparator())
                .append("Number of simulations: ").append(Simulator.SAMPLES).append(System.lineSeparator())
                .append("Max durations: ").append(Collections.max(durations)).append(System.lineSeparator())
                .append("Max maximum stake: ").append(Collections.max(maxStakes)).append(System.lineSeparator())
                .append("Max final stake: ").append(Collections.max(finalStakes)).append(System.lineSeparator())
                .append("Average duration: ").append(IntegerStatistics.MEAN.apply(durations)).append(System.lineSeparator())
                .append("Average maximum stake: ").append(IntegerStatistics.MEAN.apply(maxStakes)).append(System.lineSeparator())
                .append("Average final stake: ").append(IntegerStatistics.MEAN.apply(finalStakes)).append(System.lineSeparator())
                .append("50th percentile duration: ").append(IntegerStatistics.NTH_PERCENTILE.apply(durations, 50)).append(System.lineSeparator())
                .append("50th percentile maximum stake: ").append(IntegerStatistics.NTH_PERCENTILE.apply(maxStakes, 50)).append(System.lineSeparator())
                .append("50th percentile final stake: ").append(IntegerStatistics.NTH_PERCENTILE.apply(finalStakes, 50)).append(System.lineSeparator())
                .append("90th percentile duration: ").append(IntegerStatistics.NTH_PERCENTILE.apply(durations, 90)).append(System.lineSeparator())
                .append("90th percentile maximum stake: ").append(IntegerStatistics.NTH_PERCENTILE.apply(maxStakes, 90)).append(System.lineSeparator())
                .append("90th percentile final stake: ").append(IntegerStatistics.NTH_PERCENTILE.apply(finalStakes, 90)).append(System.lineSeparator())
                .append("Standard deviation for durations: ").append(new BigDecimal(IntegerStatistics.STANDARD_DEVIATION.apply(durations)).setScale(2, RoundingMode.HALF_UP)).append(System.lineSeparator())
                .append("Standard deviation for maximum stakes: ").append(new BigDecimal(IntegerStatistics.STANDARD_DEVIATION.apply(maxStakes)).setScale(2, RoundingMode.HALF_UP)).append(System.lineSeparator())
                .append("Standard deviation for final stakes: ").append(new BigDecimal(IntegerStatistics.STANDARD_DEVIATION.apply(finalStakes)).setScale(2, RoundingMode.HALF_UP)).append(System.lineSeparator())
                .toString());
    }
}
