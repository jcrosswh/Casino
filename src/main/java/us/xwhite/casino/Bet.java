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

/**
 * Bet object to contain the amount, outcome, and the player making the bet
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public final class Bet {

    private final int amountBet;

    private final Outcome outcome;

    private final Player player;

    /**
     * Create a bet with the amount on the outcome
     *
     * @param amount Amount to wager
     * @param outcome Outcome to bet on
     * @param player Player placing the bet
     */
    public Bet(int amount, Outcome outcome, Player player) {

        if (amount == 0 || outcome == null || player == null) {
            throw new IllegalArgumentException("Amount cannot be 0, outcome and player cannot be null");
        }

        this.amountBet = amount;
        this.outcome = outcome;
        this.player = player;
    }

    /**
     * Amount to add to players stake if the bet wins
     *
     * @return Amount to add
     */
    public int winAmount() {
        return amountBet + outcome.winAmount(amountBet);
    }

    /**
     * Amount to reduce players stake by if the bet loses
     *
     * @return Amount to remove
     */
    public int loseAmount() {
        return amountBet;
    }

    /**
     * Get the player that placed this bet
     *
     * @return Player who placed this bet
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the outcome for this bet
     *
     * @return The outcome
     */
    public Outcome getOutcome() {
        return outcome;
    }

    /**
     * Returns the string representation of this object
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Player ").append(player).append(" bet ").append(amountBet).append(" on ").append(outcome.toString());
        return result.toString();
    }
}
