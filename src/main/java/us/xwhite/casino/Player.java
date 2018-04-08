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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Template for all players
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public abstract class Player {

    private final Table table;

    private int stake;

    private int roundsToGo;

    /**
     * Initialize this player with a table
     *
     * @param table Table to associate player to
     * @param stake Player's starting stake in betting units
     * @param roundsToGo Player's starting number of rounds they are willing to
     * play
     */
    public Player(Table table, int stake, int roundsToGo) {
        this.table = table;
        this.stake = stake;
        this.roundsToGo = roundsToGo;
    }

    /**
     * Inform the player when it is time to place the bets. These bets should be
     * placed on the table.
     */
    public abstract void placeBets();

    /**
     * Inform the player that the bet has won
     *
     * @param bet Winning bet
     */
    public void win(Bet bet) {
        stake += bet.winAmount();
        roundsToGo--;
    }

    /**
     * Inform the player that the bet has lost
     *
     * @param bet Losing bet
     */
    public void lose(Bet bet) {
        roundsToGo--;
    }

    /**
     * Controls when a player leaves a table
     *
     * @return True if the player is still at the table, false otherwise
     */
    public boolean playing() {
        return (stake > 0 && roundsToGo > 0);
    }

    /**
     * Get the players current stake
     *
     * @return Player's stake
     */
    public int getStake() {
        return stake;
    }

    protected void placeBet(int amount, Outcome outcome, Player player) throws InvalidBetException {
        placeBet(new Bet(amount, outcome, player));
    }

    protected void placeBet(Bet bet) throws InvalidBetException {
        try {
            table.placeBet(bet);
            stake -= bet.loseAmount();
        } catch (InvalidBetException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
