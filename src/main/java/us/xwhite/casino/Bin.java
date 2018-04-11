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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to represent the bin on a roulette wheel
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class Bin {

    private final Set<Outcome> outcomes;

    /**
     * Basic constructor with no outcomes set
     */
    public Bin() {
        this.outcomes = new HashSet<>();
    }

    /**
     * Create a bin with outcomes set to array
     *
     * @param outcomes array of initial outcomes
     */
    public Bin(Outcome[] outcomes) {
        this(Arrays.asList(outcomes));
    }

    /**
     * Create a bin with outcomes set to initial collection
     *
     * @param outcomes collection of initial outcomes
     */
    public Bin(Collection<Outcome> outcomes) {
        this.outcomes = new HashSet<>(outcomes);
    }

    /**
     * Add an outcome to this bin
     *
     * @param outcome outcome to add
     * @return true if this bin did not already contain the outcome
     */
    public boolean add(Outcome outcome) {
        return outcomes.add(outcome);
    }

    /**
     * Determine if a given bin contains a particular outcome
     *
     * @param outcome outcome to test
     * @return true if the outcome is contained in this bin, false otherwise
     */
    public boolean contains(Outcome outcome) {
        return outcomes.contains(outcome);
    }

    /**
     * Returns the string representation of this object
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return outcomes.toString();
    }

    /**
     * Return a copy of passed in bin
     *
     * @param bin Bin to create a copy of
     * @return New bin
     */
    public static Bin of(Bin bin) {
        return new Bin(bin.outcomes);
    }
}
