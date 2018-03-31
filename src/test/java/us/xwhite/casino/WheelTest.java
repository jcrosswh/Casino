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
        wheel = new Wheel(rng);
    }
    
    @Test
    public void addTest() {
        
        Assert.assertTrue(wheel.add(0, new Outcome("Test outcome", 17)));
        
        Assert.assertTrue(wheel.add(17, new Outcome("Test outcome", 17)));
    }
    
    @Test
    public void getTest() {
        
        Outcome testOutcome = new Outcome("Test outcome", 17);
        Assert.assertTrue(wheel.add(0, testOutcome));
        Assert.assertNotNull(wheel.get(0));
    }
    
    @Test
    public void nextTest() {
        
        Assert.assertEquals(wheel.get(seed), wheel.next());
    }
}
