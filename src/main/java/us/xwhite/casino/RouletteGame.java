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
import java.util.Set;

/**
 * Game for playing Roulette. To invoke this game, instantiate the object and
 * call cycle as many times as wished
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class RouletteGame implements Game {

    /**
     * Odds for straight bets (35:1)
     */
    public static final int STRAIGHT_BET_ODDS = 35;

    /**
     * Odds for split bets (17:1)
     */
    public static final int SPLIT_BET_ODDS = 17;

    /**
     * Odds for street bets (11:1)
     */
    public static final int STREET_BET_ODDS = 11;

    /**
     * Odds for corner bets (8:1)
     */
    public static final int CORNER_BET_ODDS = 8;

    /**
     * Odds for line bets (5:1)
     */
    public static final int LINE_BET_ODDS = 5;

    /**
     * Odds for dozen bets (2:1)
     */
    public static final int DOZEN_BET_ODDS = 2;

    /**
     * Odds for column bets (2:1)
     */
    public static final int COLUMN_BET_ODDS = 2;

    /**
     * Odds for even money bets (1:1)
     */
    public static final int EVEN_MONEY_BET_ODDS = 1;

    private final Wheel wheel;

    private final Table table;

    /**
     * Create a new roulette game
     *
     * @param wheel Wheel for the game
     * @param table Table to place bets on
     */
    public RouletteGame(Wheel wheel, Table table) {
        this.wheel = wheel;
        this.table = table;
    }

    /**
     * Play one cycle of the game. This will take the player, ask the player to
     * place their bets, spin the wheel and get a winning bin, then notify the
     * player of all winning and losing bets.
     *
     * @param player Player playing the game
     * @return The winning outcomes. Will return an empty set if the player
     * didn't play
     */
    @Override
    public Set<Outcome> cycle(Player player) {

        if (player == null || !player.playing()) {
            return Collections.emptySet();
        }

        player.placeBets();

        Bin winner = wheel.next();
        Set<Outcome> winningOutcomes = winner.getOutcomes();

        for (Bet bet : table) {

            if (winner.contains(bet.getOutcome())) {
                player.win(bet);
            } else {
                player.lose(bet);
            }
        }

        player.winners(winningOutcomes);
        table.clearBets();
        return winningOutcomes;
    }

    @Override
    public Table getTable() {
        return table;
    }
}
