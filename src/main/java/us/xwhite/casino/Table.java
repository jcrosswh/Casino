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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Table to contain all bets for a spin of the wheel
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class Table implements Iterable<Bet> {

    private final int limit;

    private final List<Bet> bets;

    /**
     * Create a table with the specified table limit
     *
     * @param tableLimit Upper limit for bet amounts that one player may make
     */
    public Table(int tableLimit) {
        bets = new LinkedList<>();
        this.limit = tableLimit;
    }

    /**
     * Check that the bet being made has not exceeded the table limit
     *
     * @param bet Bet to check
     * @return True if the bet would be valid, false otherwise
     */
    public boolean isValid(Bet bet) {

        if (bet == null) {
            return false;
        }

        int total = 0;
        total = bets.stream()
                .filter((placedBets) -> (bet.getPlayer().equals(placedBets.getPlayer())))
                .map((placedBets) -> placedBets.loseAmount())
                .reduce(total, Integer::sum);

        return (total + bet.loseAmount() <= limit);
    }

    /**
     * Place the bet on the table
     *
     * @param bet Bet to place
     * @throws InvalidBetException Thrown if bet would exceed table limit or bet
     * has already been placed
     */
    public void placeBet(Bet bet) throws InvalidBetException {

        if (!isValid(bet) || bets.contains(bet)) {
            throw new InvalidBetException();
        }

        bets.add(bet);
    }

    /**
     * Get an iterator for the placed bets
     *
     * @return Iterator for bets
     */
    @Override
    public Iterator<Bet> iterator() {
        return bets.iterator();
    }

    /**
     * Returns the string representation of this object
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Table limit: ").append(limit)
                .append(", Placed bets: ").append(bets.toString());
        return result.toString();
    }

}
