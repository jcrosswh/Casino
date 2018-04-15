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
package us.xwhite.casino.players.roulette;

import java.util.Set;
import us.xwhite.casino.Outcome;
import us.xwhite.casino.Table;
import us.xwhite.casino.Wheel;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class SevenReds extends Martingale {

    private int redCount;

    private final Outcome redOutcome;

    /**
     * Create a new SevenReds player on this table
     *
     * @param table
     * @param stake Player's starting stake in betting units
     * @param roundsToGo Player's starting number of rounds they are willing to
     * play
     */
    public SevenReds(Table table, int stake, int roundsToGo) {
        super(table, stake, roundsToGo);
        redCount = 7;
        redOutcome = Wheel.getOutcome(Wheel.BinBuilder.BETS.getString("bet.red"));
    }

    @Override
    public void winners(Set<Outcome> outcomes) {
        if (outcomes.contains(redOutcome)) {
            redCount--;
        } else {
            redCount = 7;
        }
    }

    @Override
    public void placeBets() {
        if (redCount <= 0) {
            super.placeBets();
        } else {
            reduceRoundsToGo();
        }
    }

}
