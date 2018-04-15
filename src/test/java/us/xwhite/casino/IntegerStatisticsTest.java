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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public class IntegerStatisticsTest {

    @Test
    public void meanTest() {

        List<Integer> myList = Arrays.asList(1, 2, 3, 4, 5);
        Assert.assertEquals(Double.valueOf(3.0), IntegerStatistics.MEAN.apply(myList));

        List<Integer> myOneValueList = Arrays.asList(1);
        Assert.assertEquals(Double.valueOf(1.0), IntegerStatistics.MEAN.apply(myOneValueList));
    }

    @Test
    public void meanNullTest() {

        try {
            IntegerStatistics.MEAN.apply(null);
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting this exception
        } catch (Exception e) {
            Assert.fail("Not expecting any other exceptions");
        }
    }

    @Test
    public void meanEmptyListTest() {

        try {
            IntegerStatistics.MEAN.apply(new ArrayList<>());
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting this exception
        } catch (Exception e) {
            Assert.fail("Not expecting any other exceptions");
        }
    }

    @Test
    public void standardDeviationTest() {

        List<Integer> myList = Arrays.asList(9, 8, 5, 9, 9, 4, 5, 8, 10, 7, 8, 8);
        Assert.assertEquals(
                new BigDecimal(1.88).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(IntegerStatistics.STANDARD_DEVIATION.apply(myList))
                        .setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void standardDeviationOneItemTest() {

        try {
            IntegerStatistics.STANDARD_DEVIATION.apply(Arrays.asList(9));
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting this exception
        } catch (Exception e) {
            Assert.fail("Not expecting any other exceptions");
        }
    }

    @Test
    public void nthPercentilTest() {

        List<Integer> myList = Arrays.asList(9, 8, 5, 9, 9, 4, 5, 8, 10, 7);
        Assert.assertEquals(Integer.valueOf(9), IntegerStatistics.NTH_PERCENTILE.apply(myList, 90));
        
        Assert.assertEquals(Integer.valueOf(10), IntegerStatistics.NTH_PERCENTILE.apply(myList, 99));
        
        Assert.assertEquals(Integer.valueOf(4), IntegerStatistics.NTH_PERCENTILE.apply(myList, 1));
    }
    
    @Test
    public void nthPercentilBoundsTest() {

        List<Integer> myList = Arrays.asList(9, 8, 5, 9, 9, 4, 5, 8, 10, 7);
        
        try {
            IntegerStatistics.NTH_PERCENTILE.apply(myList, 100);
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting this exception
        } catch (Exception e) {
            Assert.fail("Not expecting any other exceptions");
        }
        
        try {
            IntegerStatistics.NTH_PERCENTILE.apply(myList, 101);
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting this exception
        } catch (Exception e) {
            Assert.fail("Not expecting any other exceptions");
        }
        
        try {
            IntegerStatistics.NTH_PERCENTILE.apply(myList, 0);
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting this exception
        } catch (Exception e) {
            Assert.fail("Not expecting any other exceptions");
        }
        
        try {
            IntegerStatistics.NTH_PERCENTILE.apply(myList, -1);
            Assert.fail("Expecting an exception");
        } catch (IllegalArgumentException iae) {
            // do nothing, expecting this exception
        } catch (Exception e) {
            Assert.fail("Not expecting any other exceptions");
        }
    }
}
