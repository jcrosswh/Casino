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
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class BinTest {

    @Test
    public void constructionTest() {

        Bin instance = new Bin();
        Assert.assertNotNull(instance);

        Outcome[] outcomes = {new Outcome("Test Outcome", 1)};
        instance = new Bin(outcomes);
        Assert.assertNotNull(instance);

        instance = new Bin(Arrays.asList(new Outcome("Test Outcome", 1)));
        Assert.assertNotNull(instance);
    }

    @Test
    public void addTest() {
        Outcome outcome = new Outcome("Test outcome", 5);
        Bin instance = new Bin();
        Assert.assertEquals(true, instance.add(outcome));

        Assert.assertEquals(false, instance.add(outcome));

        Assert.assertEquals(true, instance.add(new Outcome("Another outcome", 1)));
    }

    @Test
    public void containsTest() {

        Outcome outcome = new Outcome("Test outcome", 5);
        Bin instance = new Bin();
        instance.add(outcome);
        
        Assert.assertTrue(instance.contains(outcome));
        
        Assert.assertFalse(instance.contains(new Outcome("Some other outcome" ,7)));
    }
    
    @Test
    public void getOutcomesTest() {
        
        Outcome outcome = new Outcome("Test outcome", 5);
        Bin instance = new Bin();
        instance.add(outcome);
        
        Set<Outcome> outcomes = instance.getOutcomes();
        Assert.assertNotNull(outcomes);
        Assert.assertEquals(1, outcomes.size());
        Assert.assertTrue(outcomes.contains(outcome));
        
        try {
            outcomes.add(null);
            Assert.fail("Expecting to throw an exception");
        } catch (UnsupportedOperationException uoe) {
            // do nothing, expecting an exception
        }
        
        Object[] outcomesArray = outcomes.toArray();
        Assert.assertEquals(1, outcomesArray.length);
        outcomesArray[0] = null;
        Assert.assertTrue(outcomes.contains(outcome));
    }
}
