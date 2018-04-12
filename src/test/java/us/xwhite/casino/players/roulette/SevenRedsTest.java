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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import us.xwhite.casino.Bet;
import us.xwhite.casino.InvalidBetException;
import us.xwhite.casino.Outcome;
import us.xwhite.casino.Player;
import us.xwhite.casino.Simulator;
import us.xwhite.casino.Table;
import us.xwhite.casino.Wheel;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class SevenRedsTest {

    private Player player;

    private Table table;

    private Set<Outcome> redOutcomes;

    @Before
    public void setUp() {

        
        new Wheel.WheelBuilder().rng(new Random()).build();

        table = Mockito.spy(new Table(1000));
        player = new SevenReds(table, Simulator.INIT_STAKE, Simulator.INIT_DURATION);
        redOutcomes = new HashSet<>(Arrays.asList(Wheel.getOutcome("Red")));
    }

    @Test
    public void placeBetsTest() throws InvalidBetException {

        player.placeBets();

        Mockito.verify(table, Mockito.times(0)).placeBet(Mockito.any(Bet.class));
    }

    @Test
    public void winnersTest() throws InvalidBetException {

        Set<Outcome> blackOutcomes = new HashSet<>(Arrays.asList(Wheel.getOutcome("Black")));
        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);

        player.placeBets();
        Mockito.verify(table, Mockito.times(0)).placeBet(Mockito.any(Bet.class));

        winRedSevenTimes(player);
        player.placeBets();
        Mockito.verify(table, Mockito.times(1)).placeBet(betCaptor.capture());
        Bet bet = betCaptor.getValue();
        Assert.assertEquals(bet.getOutcome(), Wheel.getOutcome(Wheel.BinBuilder.BETS.getString("bet.black")));
        Assert.assertEquals(1, bet.loseAmount());
        
        // assume the player won the first round
        player.win(bet);
        player.winners(redOutcomes);

        player.placeBets();
        Mockito.verify(table, Mockito.times(2)).placeBet(betCaptor.capture());
        bet = betCaptor.getValue();
        Assert.assertEquals(bet.getOutcome(), Wheel.getOutcome(Wheel.BinBuilder.BETS.getString("bet.black")));
        Assert.assertEquals(1, bet.loseAmount());

        // assume the player lost the second round
        player.lose(bet);
        player.winners(blackOutcomes);

        // should not have placed any bets
        player.placeBets();
        Mockito.verify(table, Mockito.times(2)).placeBet(Mockito.any(Bet.class));
        
        // waits patiently for their turn
        winRedSevenTimes(player);
        
        // now strike - using martingale system
        player.placeBets();
        Mockito.verify(table, Mockito.times(3)).placeBet(betCaptor.capture());
        bet = betCaptor.getValue();
        Assert.assertEquals(bet.getOutcome(), Wheel.getOutcome(Wheel.BinBuilder.BETS.getString("bet.black")));
        Assert.assertEquals(2, bet.loseAmount());
    }

    private void winRedSevenTimes(Player player) {

        for (int i = 0; i < 7; i++) {
            player.winners(redOutcomes);
        }
    }
}
