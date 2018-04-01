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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Wheel for a Roulette game. This wheel will contain a collection of bins and
 * allow the game to randomly select a bin with a spin of the wheel.
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class Wheel {

    /**
     * Maximum number of bins on a wheel (38 for American Roulette)
     */
    public static final int MAX_BINS = 38;

    private final List<Bin> bins;

    private final Random rng;

    private static final Map<String, Outcome> ALL_OUTCOMES;
    
    static {
        ALL_OUTCOMES = new HashMap<>();
    }

    /**
     * Create a Wheel with a random number generator
     *
     * @param rng Random number generator to use when selecting bins
     */
    public Wheel(Random rng) {
        this.bins = new ArrayList<>(MAX_BINS);
        this.rng = rng;

        for (int i = 0; i < MAX_BINS; i++) {
            bins.add(new Bin());
        }
    }

    /**
     * Add an outcome to the bin
     *
     * @param bin Bin on the wheel to add the outcome to. 0 is index 0, and 00
     * is index 37.
     * @param outcome Outcome to add
     * @return true (returned from List.add)
     */
    public boolean add(int bin, Outcome outcome) {

        if (bin < 0 && bin >= MAX_BINS) {
            throw new IndexOutOfBoundsException("Index value must be between 0 and Wheel.MAX_BINS");
        }

        if (!ALL_OUTCOMES.containsKey(outcome.getName())) {
            ALL_OUTCOMES.put(outcome.getName(), outcome);
        }

        return bins.get(bin).add(outcome);
    }
    
    public static Outcome getOutcome(String name) {
        return ALL_OUTCOMES.get(name);
    }

    /**
     * Spin the wheel and get the next random bin
     *
     * @return Bin that was randomly selected
     */
    public Bin next() {
        return get(rng.nextInt(MAX_BINS));
    }

    /**
     * Get the bin at the specified location
     *
     * @param bin Bin number to fetch. 0 is index 0, and 00 is index 37.
     * @return Bin at this location
     */
    public Bin get(int bin) {
        return bins.get(bin);
    }
}

/**
 * Non random rng that should only be used for testing. Create this object, then
 * set the seed value
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
class NonRandom extends Random {

    private int value;

    /**
     * Set the random value that should always be returned
     *
     * @param value Value that is always returned
     * @throws IndexOutOfBoundsException Thrown if value is less than 0 or
     * greater than {@link Wheel#MAX_BINS}
     */
    @Override
    public void setSeed(long value) {

        if (value < 0 && value >= Wheel.MAX_BINS) {
            throw new IndexOutOfBoundsException("Seed values must be between 0 and Wheel.MAX_BINS");
        }

        this.value = (int) value;
    }

    @Override
    public int next(int bits) {
        return value;
    }
}
