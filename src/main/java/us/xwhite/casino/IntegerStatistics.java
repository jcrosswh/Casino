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
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author Joel Crosswhite <joel.crosswhite@ix.netcom.com>
 */
public final class IntegerStatistics {

    public static final Function<List<Integer>, Double> MEAN
            = (list) -> {

                if (list == null || list.isEmpty()) {
                    throw new IllegalArgumentException("List must not be null and must contain values");
                }

                return (double) list.parallelStream().reduce(0, Integer::sum) / list.size();
            };

    public static final BiFunction<List<Integer>, Integer, Integer> NTH_PERCENTILE
            = (list, percentile) -> {
                if (percentile <= 0 || percentile >= 100) {
                    throw new IllegalArgumentException("Please use a value between 0 and 100");
                }

                Collections.sort(list);
                int location = new BigDecimal((double) percentile * list.size() / 100)
                        .setScale(0, RoundingMode.UP).intValue() - 1;
                return list.get(location);
            };

    public static final Function<List<Integer>, Double> STANDARD_DEVIATION
            = (list) -> {
                double mean = MEAN.apply(list);

                if (list.size() == 1) {
                    throw new IllegalArgumentException("List must contain more than one value");
                }

                double s = list.stream()
                        .map((x) -> x - mean)
                        .map((d) -> d * d)
                        .reduce(0.0, Double::sum);

                return Math.sqrt(s / (list.size() - 1));
            };

    private IntegerStatistics() {

    }
}
