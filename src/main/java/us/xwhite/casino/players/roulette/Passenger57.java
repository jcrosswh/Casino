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
import us.xwhite.casino.InvalidBetException;
import us.xwhite.casino.Player;
import us.xwhite.casino.Table;
import us.xwhite.casino.Wheel;
import us.xwhite.casino.util.BinBuilder;

/**
 * Create a Roulette player who only bets on black
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class Passenger57 extends Player {

    /**
     * Create a new Passenger57 player on this table
     *
     * @param table Table to associate player to
     */
    public Passenger57(Table table) {
        super(table);
    }

    @Override
    public void placeBets() {
        try {
            placeBet(10, Wheel.getOutcome(BinBuilder.BETS.getString("bet.black")), this);
        } catch (InvalidBetException ex) {
            Logger.getLogger(Passenger57.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
