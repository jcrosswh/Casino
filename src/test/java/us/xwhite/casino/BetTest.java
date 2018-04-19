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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class BetTest {

    private Bet bet;

    @Before
    public void setUp() {
        bet = new Bet(100, new Outcome("Test outcome", 17), Mockito.mock(Player.class));
    }

    @Test
    public void constructorTest() {

        try {
            Bet bet = new Bet(0, new Outcome("Test outcome", RouletteGame.COLUMN_BET_ODDS), Mockito.mock(Player.class));
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // legitimate exception
        }
        
        try {
            Bet bet = new Bet(10, null, Mockito.mock(Player.class));
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // legitimate exception
        }
        
        try {
            Bet bet = new Bet(0, new Outcome("Test outcome", RouletteGame.COLUMN_BET_ODDS), null);
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // legitimate exception
        }
    }

    @Test
    public void winAmountTest() {
        Assert.assertEquals(1800, bet.winAmount());
    }

    @Test
    public void loseAmountTest() {
        Assert.assertEquals(100, bet.loseAmount());
    }
    
    @Test
    public void toStringTest() {
        Logger.getLogger(BetTest.class.getName()).log(Level.FINE, "bet:={0}", bet);
    }
}
