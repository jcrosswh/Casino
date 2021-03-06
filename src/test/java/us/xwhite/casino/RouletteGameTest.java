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

import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import us.xwhite.casino.Wheel.WheelBuilder;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class RouletteGameTest {

    private RouletteGame game;
    private Table table;
    private Wheel wheel;

    @Before
    public void setUp() {

        Random rng = new NonRandom();
        rng.setSeed(4);
        wheel = Mockito.spy(new WheelBuilder().rng(rng).build());

        table = new Table(1000);

        game = new RouletteGame(wheel, table);
    }

    @Test
    public void cycleTest() throws InvalidBetException {

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.playing()).thenReturn(true);

        // next two lines will be handled by player.placeBets()
        Bet bet = new Bet(100, Wheel.getOutcome("Black"), player);
        table.placeBet(bet);

        Set<Outcome> outcomes = game.cycle(player);
        Mockito.verify(wheel, Mockito.times(1)).next();
        Mockito.verify(player, Mockito.times(1)).win(Mockito.any(Bet.class));
        Assert.assertNotNull(outcomes);
        Assert.assertEquals(14, outcomes.size());
        Assert.assertTrue(outcomes.contains(Wheel.getOutcome("Black")));
        Mockito.verify(player, Mockito.times(1)).winners(outcomes);
    }

    @Test
    public void cycleNullPlayerTest() {

        Set<Outcome> outcomes = game.cycle(null);
        Mockito.verify(wheel, Mockito.times(0)).next();
        Assert.assertEquals(0, outcomes.size());
    }

    @Test
    public void cyclePlayerNotPlayingTest() {

        Player player = Mockito.mock(Player.class);

        Mockito.when(player.playing()).thenReturn(false);

        Set<Outcome> outcomes = game.cycle(player);
        Mockito.verify(wheel, Mockito.times(0)).next();
        Assert.assertEquals(0, outcomes.size());
    }
}
