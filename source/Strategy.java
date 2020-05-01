/*
 * Copyright (c) 2019 Halan.
 */

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public final class Strategy {

    private State[] stateArray;
    private final int h;
    private int simulatedState;
    private boolean isSimulated;
    private int payoff = 0;

    public Strategy(int h) {
        this.h = h;
        this.stateArray = new State[h];
    }

    public static Strategy parser(String strategy) {
        String[] strategyArray = strategy.split(" ");
        int p = 0;
        int h = Integer.parseInt(strategyArray[p]);
        Strategy stra = new Strategy(h);
        stra.stateArray = new State[h];
        for (int i = 0; i < h; i++) {
            p++;
            double probability = Double.parseDouble(strategyArray[p]);
            double[] crowdArray = new double[h];
            double[] uncrowdArray = new double[h];
            for (int j = 0; j < h; j++) {
                p++;
                crowdArray[j] = Double.parseDouble(strategyArray[p]);
                uncrowdArray[j] = Double.parseDouble(strategyArray[p + h]);
            }
            stra.stateArray[i] = new State(probability, crowdArray, uncrowdArray);
            p += h;
            assert p < strategyArray.length;
        }
        return stra;
    }

    public static Strategy random(int h) {
        Strategy strategy = new Strategy(h);
        for (int idx = 0; idx < h; idx++) {
            strategy.stateArray[idx] = State.random(h);
        }
        strategy.simulatedState = ThreadLocalRandom.current().nextInt(h);
        strategy.isSimulated = ThreadLocalRandom.current().nextDouble() < strategy.getState(strategy.simulatedState).getProbability();
        return strategy;
    }

    public int getSimulatedState() {
        return simulatedState;
    }

    public boolean isSimulated() {
        return isSimulated;
    }

    public Strategy mutate(double chi, double range) {
        Strategy strategy = clone();
        double rate = chi / (2 * strategy.stateArray.length + 1) * strategy.stateArray.length;
        strategy.stateArray = Arrays.stream(strategy.stateArray).map(s -> s.mutate(rate, range)).toArray(State[]::new);
        return strategy;
    }

    public static Strategy crossover(Strategy parentX, Strategy parentY) {
        Strategy s = new Strategy(parentX.h);
        for (int idx = 0; idx < s.h; idx++) {
            s.stateArray[idx] = State.crossover(parentX.stateArray[idx], parentY.stateArray[idx]);
        }
        return s;
    }

    public int getPayoff() {
        return payoff;
    }

    public void updatePayoff(boolean isCrowded) {
        if (isSimulated != isCrowded)
            payoff++;
    }

    public void simulator(int state, boolean crowded) {
        simulatedState = state;
        simulator(crowded);
    }

    public void simulator(boolean crowded) {
        simulatedState = getState(simulatedState).simulator(crowded);
        isSimulated = ThreadLocalRandom.current().nextDouble() < getState(simulatedState).getProbability();
    }

    public State getState(int s) {
        return stateArray[s];
    }

    public Strategy clone() {
        Strategy strategy = new Strategy(h);
        strategy.stateArray = stateArray;
        return strategy;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(h);
        Arrays.stream(stateArray).forEach(s -> builder.append(" ").append(s));
        return builder.toString();
    }

}
