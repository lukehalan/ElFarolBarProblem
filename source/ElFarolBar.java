/*
 * Copyright (c) 2019 Halan.
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ElFarolBar {

    public static int distributer(double[] probArray) {
        double s = DoubleStream.of(probArray).sum();
        double rnd = ThreadLocalRandom.current().nextDouble(s);
        double acumulative = 0;
        for (int idx = 0; idx < probArray.length; idx++) {
            acumulative += probArray[idx];
            if (rnd < acumulative) {
                return idx;
            }
        }
        return -1;
    }

    public static double[] probArrayFromString(String prob) {
        String[] probArray = prob.split(" ");
        int l = probArray.length;
        double[] probArrayDouble = new double[l];
        for (int idx = 0; idx < l; idx++) {
            probArrayDouble[idx] = Double.parseDouble(probArray[idx]);
        }
        return probArrayDouble;
    }

    public static double[] doNormalisation(double[] values) {
        double[] val = new double[values.length];
        double s = DoubleStream.of(values).sum();
        for (int i = 0; i < val.length; i++) {
            val[i] = values[i] / s;
        }
        return val;
    }

    public static double[] getDistribution(int h) {
        double[] v = new double[h];
        for (int idx = 0; idx < h; idx++) {
            v[idx] = ThreadLocalRandom.current().nextDouble();
        }
        return doNormalisation(v);
    }

    public static double mutate(double value, double range) {
        return Math.max(0, value + (value * ThreadLocalRandom.current().nextDouble(-range, range)));
    }

}
