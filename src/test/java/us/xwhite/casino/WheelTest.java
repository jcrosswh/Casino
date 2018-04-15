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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import us.xwhite.casino.Wheel.WheelBuilder;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class WheelTest {

    private Wheel wheel;

    private final int seed = 4;

    @Before
    public void setUp() {
        Random rng = new NonRandom();
        rng.setSeed(seed);
        wheel = new Wheel.WheelBuilder().rng(rng).build();
    }

    @Test
    public void nextTest() {

        Assert.assertTrue(wheel.next().contains(Wheel.getOutcome(Integer.toString(seed))));
    }
    
    @Test
    public void verifyOutcomeCannotBeAddedTest() {
        
        Outcome invalidOutcome = new Outcome("Not a valid outcome", 17);
        
        Assert.assertTrue(wheel.next().add(invalidOutcome));
        Assert.assertFalse(wheel.next().contains(invalidOutcome));
    }

    @Test
    public void getOutcomeTest() {

        Assert.assertNotNull(Wheel.getOutcome("0"));

        try {
            Wheel.getOutcome("Illegal outcome");
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting an exception
        }
    }

    @Test
    public void createNewWheelTest() {

        WheelBuilder builder = new Wheel.WheelBuilder();

        Assert.assertNotNull(builder.rng(new Random()).build());
    }

    @Test
    public void createNewWheelExceptionTest() {

        WheelBuilder builder = new Wheel.WheelBuilder();

        try {
            builder.build();
            Assert.fail("Expecting to get an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting an exception
        }

        try {
            builder.rng(null).build();
            Assert.fail("Expecting to get an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting an exception
        }
    }
}
