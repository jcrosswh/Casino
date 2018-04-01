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
package us.xwhite.casino.util;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import us.xwhite.casino.Bin;
import us.xwhite.casino.Wheel;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class BinBuilderTest {

    private Wheel wheel;

    private Field outcomesField;

    @Before
    public void setUp() throws NoSuchFieldException {
        wheel = new Wheel(new Random());

        // using reflection to inspect the container - need more detailed
        // information than I'm willing to expose through the containing object
        outcomesField = Bin.class.getDeclaredField("outcomes");
        outcomesField.setAccessible(true);
    }

    @Test
    public void buildBinsTest() throws IllegalAccessException {
        try {
            BinBuilder.buildBins(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(11, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(17, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(11, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateStraightBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateStraightBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateSplitBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateSplitBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(2, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(4, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(2, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateStreetBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateStreetBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateConerBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateConerBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(4, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateLineBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateLineBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(2, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateDozenBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateDozenBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateColumnBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateColumnBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(37))).size());
    }

    @Test
    public void generateEvenMoneyBetsTest() throws IllegalAccessException {
        try {
            BinBuilder.generateEvenMoneyBets(wheel);
        } catch (Exception e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(0))).size());
        Assert.assertEquals(3, ((Set) outcomesField.get(wheel.get(1))).size());
        Assert.assertEquals(3, ((Set) outcomesField.get(wheel.get(17))).size());
        Assert.assertEquals(3, ((Set) outcomesField.get(wheel.get(36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheel.get(37))).size());
    }
}
