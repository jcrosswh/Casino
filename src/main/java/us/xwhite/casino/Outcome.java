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
 * Class for holding outcome odds to be applied to bets. Each outcome should
 * have odds, and any bet amount can get the win amount by calling winAmount.
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class Outcome {

    private final String name;

    private final int odds;

    /**
     * Create new Outcome with name and odds
     *
     * @param name Name representation of the outcome
     * @param odds Odds in (odds:1) ratio to be applied
     */
    public Outcome(String name, int odds) {
        this.name = name;
        this.odds = odds;
    }

    /**
     * Determine amount to be paid
     *
     * @param amount Bet amount placed by player
     * @return Bet amount times the odds
     */
    public int winAmount(int amount) {
        return amount * odds;
    }

    /**
     * Get the name for the outcome
     *
     * @return Name of the outcome
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the hash code value for this outcome
     *
     * @return the hash code value for this outcome
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /**
     * Compares the specified object for equality
     *
     * @param obj object to be compared
     * @return true if the object names are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Outcome other = (Outcome) obj;
        return !((this.name == null) ? (other.name != null) : !this.name.equals(other.name));
    }

    /**
     * Returns the string representation of this object
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append(" (").append(odds).append(":1)");
        return result.toString();
    }

}
