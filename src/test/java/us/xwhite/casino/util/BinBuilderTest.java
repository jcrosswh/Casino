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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import us.xwhite.casino.Bin;
import us.xwhite.casino.Wheel;
import us.xwhite.casino.Wheel.WheelBuilder;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class BinBuilderTest {

    private Wheel wheel;

    private Field outcomesField;
    
    private Method wheelGetMethod;

    private Map<String, Method> binBuilderMethods;

    @Before
    public void setUp() {

        // bypassing the singleton instantiator so we can test creating the bins
        WheelBuilder builder = new Wheel.WheelBuilder().rng(new Random());
        for (Constructor<?> wheelConstructor : Wheel.class.getDeclaredConstructors()) {
            if (wheelConstructor.getParameterCount() == 1 && wheelConstructor.getParameterTypes()[0].equals(WheelBuilder.class)) {
                wheelConstructor.setAccessible(true);
                try {
                    wheel = (Wheel) wheelConstructor.newInstance(builder);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Assert.fail("Not expecting any exceptions");
                }
                break;
            }
        }

        // allow access to bin builder methods
        binBuilderMethods = new HashMap<>();
        try {
            for (Method method : Class.forName("us.xwhite.casino.Wheel$BinBuilder").getDeclaredMethods()) {
                method.setAccessible(true);
                binBuilderMethods.put(method.getName(), method);
            }
        } catch (ClassNotFoundException ex) {
            Assert.fail("Could not find Wheel.BinBuilder class");
        }

        // using reflection to inspect the container - need more detailed
        // information than I'm willing to expose through the containing object
        try {
            outcomesField = Bin.class.getDeclaredField("outcomes");
        } catch (NoSuchFieldException | SecurityException ex) {
            Assert.fail("Not expecting any exceptions");
        }
        outcomesField.setAccessible(true);
        
        try {
            for (Method method : Class.forName("us.xwhite.casino.Wheel").getDeclaredMethods()) {
                if (method.getName().equals("get")) {
                    method.setAccessible(true);
                    wheelGetMethod = method;
                }
            }
        } catch (ClassNotFoundException ex) {
            Assert.fail("Could not find Wheel.get method");
        }
    }

    @Test
    public void buildBinsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("buildBins").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(11, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(17, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(11, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateStraightBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateStraightBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateSplitBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateSplitBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(2, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(4, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(2, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateStreetBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateStreetBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateConerBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateConerBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(4, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateLineBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateLineBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(2, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateDozenBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateDozenBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateColumnBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateColumnBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(1, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }

    @Test
    public void generateEvenMoneyBetsTest() throws IllegalAccessException, InvocationTargetException {
        try {
            binBuilderMethods.get("generateEvenMoneyBets").invoke(this, wheel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail("Not expecting any exceptions");
        }

        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 0))).size());
        Assert.assertEquals(3, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 1))).size());
        Assert.assertEquals(3, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 17))).size());
        Assert.assertEquals(3, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 36))).size());
        Assert.assertEquals(0, ((Set) outcomesField.get(wheelGetMethod.invoke(wheel, 37))).size());
    }
}
