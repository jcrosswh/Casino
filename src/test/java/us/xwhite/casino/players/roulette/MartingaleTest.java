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

import java.util.Random;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import us.xwhite.casino.Bet;
import us.xwhite.casino.InvalidBetException;
import us.xwhite.casino.Player;
import us.xwhite.casino.Table;
import us.xwhite.casino.Wheel;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class MartingaleTest {
    
    private Player player;
    
    private Table table;
    
    @Before
    public void setUp() {
        
        new Wheel.WheelBuilder().rng(new Random()).build();
        
        table = Mockito.spy(new Table(1000));
        player = new Martingale(table);
    }
    
    @Test
    public void placeBetsTest() throws InvalidBetException {
        
        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);
        
        player.placeBets();

        Mockito.verify(table, Mockito.times(1)).placeBet(betCaptor.capture());
        Bet bet = betCaptor.getValue();
        Assert.assertEquals(bet.getOutcome(), Wheel.getOutcome(Wheel.BinBuilder.BETS.getString("bet.black")));
        Assert.assertEquals(10, bet.loseAmount());
    }
    
    @Test
    public void winTest() throws InvalidBetException {
        
        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);
        
        player.placeBets();

        Mockito.verify(table, Mockito.times(1)).placeBet(betCaptor.capture());
        Bet bet = betCaptor.getValue();
        Assert.assertEquals(10, bet.loseAmount());
        
        player.lose(bet);
        player.placeBets();
        Mockito.verify(table, Mockito.times(2)).placeBet(betCaptor.capture());
        bet = betCaptor.getValue();
        Assert.assertEquals(20, bet.loseAmount());
        
        player.win(bet);
        player.placeBets();
        Mockito.verify(table, Mockito.times(3)).placeBet(betCaptor.capture());
        bet = betCaptor.getValue();
        Assert.assertEquals(10, bet.loseAmount());
    }
    
    @Test
    public void loseTest() throws InvalidBetException {
        
        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);
        
        player.placeBets();

        Mockito.verify(table, Mockito.times(1)).placeBet(betCaptor.capture());
        Bet bet = betCaptor.getValue();
        Assert.assertEquals(10, bet.loseAmount());
        
        player.lose(bet);
        player.placeBets();
        Mockito.verify(table, Mockito.times(2)).placeBet(betCaptor.capture());
        bet = betCaptor.getValue();
        Assert.assertEquals(20, bet.loseAmount());
        
        player.lose(bet);
        player.placeBets();
        Mockito.verify(table, Mockito.times(3)).placeBet(betCaptor.capture());
        bet = betCaptor.getValue();
        Assert.assertEquals(40, bet.loseAmount());
    }
}
