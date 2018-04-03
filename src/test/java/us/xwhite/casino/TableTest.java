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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class TableTest {

    private Table table;

    @Before
    public void setUp() {
        table = new Table(100);

    }

    @Test
    public void isValidWithValidBetsOnePlayerTest() {

        Player player = Mockito.mock(Player.class);
        Bet bet = new Bet(100, new Outcome("Test outcome 1", RouletteGame.EVEN_MONEY_BET_ODDS), player);

        Assert.assertTrue(table.isValid(bet));
    }

    @Test
    public void isValidWithValidBetsTwoPlayersTest() {

        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);

        Bet bet1 = new Bet(100, new Outcome("Test outcome 1", RouletteGame.EVEN_MONEY_BET_ODDS), player1);
        try {
            table.placeBet(bet1);
        } catch (InvalidBetException ibe) {
            Assert.fail("Not expecting any exceptions");
        }

        Bet bet2 = new Bet(100, new Outcome("Test outcome 1", RouletteGame.EVEN_MONEY_BET_ODDS), player2);
        Assert.assertTrue(table.isValid(bet2));
    }

    @Test
    public void isValidWithNullBetTest() {
        Assert.assertFalse(table.isValid(null));
    }

    @Test
    public void isValidInvalidWithOnePlayerTest() {

        Player player = Mockito.mock(Player.class);

        Bet bet1 = new Bet(100, new Outcome("Test outcome 1", RouletteGame.EVEN_MONEY_BET_ODDS), player);
        try {
            table.placeBet(bet1);
        } catch (InvalidBetException ibe) {
            Assert.fail("Not expecting any exceptions");
        }

        Bet bet2 = new Bet(10, new Outcome("Test outcome 2", RouletteGame.EVEN_MONEY_BET_ODDS), player);
        Assert.assertFalse(table.isValid(bet2));
    }

    @Test
    public void placeBetValidBetTest() {
        try {
            table.placeBet(new Bet(100, new Outcome("Test outcome 1", RouletteGame.EVEN_MONEY_BET_ODDS), Mockito.mock(Player.class)));
        } catch (InvalidBetException ibe) {
            Assert.fail("Not expecting any exceptions");
        }
    }
    
    @Test
    public void placeBetBetNotValidTest() {
        try {
            table.placeBet(new Bet(1000, new Outcome("Test outcome 1", RouletteGame.EVEN_MONEY_BET_ODDS), Mockito.mock(Player.class)));
            Assert.fail("Expecting an exception");
        } catch (InvalidBetException ibe) {
            // Legitimate exception here
        }
    }
    
    @Test
    public void placeBetSameBetTest() {
        
        Bet bet = new Bet(10, new Outcome("Test outcome 1", RouletteGame.EVEN_MONEY_BET_ODDS), Mockito.mock(Player.class));
        
        try {
            table.placeBet(bet);
        } catch (InvalidBetException ibe) {
            Assert.fail("Expecting an exception");
        }
        
        try {
            table.placeBet(bet);
            Assert.fail("Expecting an exception");
        } catch (InvalidBetException ibe) {
            // Legitimate exception here
        }
    }
    
    @Test
    public void iteratorTest() {
        Assert.assertNotNull(table.iterator());
    }
}
