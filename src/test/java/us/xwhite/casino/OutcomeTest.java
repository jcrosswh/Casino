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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class OutcomeTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testWinAmount() {
        Outcome instance = new Outcome("Test Outcome", 17);
        Assert.assertEquals(0, instance.winAmount(0));

        Assert.assertEquals(17, instance.winAmount(1));

        Assert.assertEquals(85, instance.winAmount(5));
    }

    @Test
    public void testEquals() {
        String outcomeName = "Test Outcome";
        Object outcome1 = new Outcome(outcomeName, 17);
        Assert.assertEquals(false, outcome1.equals(null));

        Assert.assertEquals(true, outcome1.equals(new Outcome(outcomeName, 0)));

        Assert.assertEquals(true, outcome1.equals(new Outcome(outcomeName, 17)));

        Assert.assertEquals(false, outcome1.equals(new Outcome(outcomeName + "1", 17)));

        Assert.assertEquals(new Outcome(outcomeName, 17), outcome1);
    }

}
