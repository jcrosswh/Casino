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

import java.util.logging.Level;
import java.util.logging.Logger;
import us.xwhite.casino.Bet;
import us.xwhite.casino.InvalidBetException;
import us.xwhite.casino.Player;
import us.xwhite.casino.Table;
import us.xwhite.casino.Wheel;

/**
 * Create a Roulette player who only bets on black and uses the Martingale
 * betting strategy. Using this betting strategy, this player will bet their
 * normal betting units whenever they are winning. On a loss, they will double
 * their betting units, and continue to do so until they either reach the table
 * maximum, or they win.
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public final class Martingale extends Player {

    private int loseCount;
    
    private boolean donePlaying;

    /**
     * Create a new Martingale player on this table
     *
     * @param table
     * @param stake Player's starting stake in betting units
     * @param roundsToGo Player's starting number of rounds they are willing to
     * play
     */
    public Martingale(Table table, int stake, int roundsToGo) {
        super(table, stake, roundsToGo);
        loseCount = 0;
        donePlaying = false;
    }

    @Override
    public void win(Bet bet) {
        super.win(bet);
        loseCount = 0;
    }

    @Override
    public void lose(Bet bet) {
        loseCount++;
    }
    
    @Override
    public boolean playing() {
        
        if (donePlaying) {
            return false;
        }
        
        return super.playing();
    }

    private int getBetMultiple() {
        return Math.min((int) Math.pow(2.0, loseCount), getStake());
    }

    @Override
    public void placeBets() {
        try {
            placeBet(getBetMultiple(), Wheel.getOutcome(Wheel.BinBuilder.BETS.getString("bet.black")), this);
        } catch (InvalidBetException ex) {
            loseCount--;
            try {
                placeBet(getBetMultiple(), Wheel.getOutcome(Wheel.BinBuilder.BETS.getString("bet.black")), this);
            } catch (InvalidBetException ex1) {
                donePlaying = true;
            }
        }
    }

}
