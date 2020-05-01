/*
 * Copyright (c) 2019 Halan.
 */

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public final class State {

    private final double[] uncrowdArray;
    private final double[] crowdArray;
    private final double probability;

    public State(double probability, double[] crowdArray, double[] uncrowdArray) {
        this.probability = probability;
        this.crowdArray = crowdArray;
        this.uncrowdArray = uncrowdArray;
    }

    public static State random(int h){
        double probability = ThreadLocalRandom.current().nextDouble();
        double[] crowdArray = ElFarolBar.getDistribution(h);
        double[] uncrowdArray = ElFarolBar.getDistribution(h);
        return new State(probability, crowdArray, uncrowdArray);
    }

    public static State crossover(State parentX, State parentY){
        double probability =  ThreadLocalRandom.current().nextBoolean() ? parentX.probability : parentY.probability;
        double[] crowdArray = new double[parentX.crowdArray.length];
        double[] uncrowdArray = new double[parentX.uncrowdArray.length];
        for(int idx = 0; idx < crowdArray.length; idx++){
            crowdArray[idx] = ThreadLocalRandom.current().nextBoolean() ? parentX.crowdArray[idx] : parentY.crowdArray[idx];
            uncrowdArray[idx] = ThreadLocalRandom.current().nextBoolean() ? parentX.uncrowdArray[idx] : parentY.uncrowdArray[idx];
        }
        return new State(probability, crowdArray, uncrowdArray);
    }

    public State mutate(double rate, double range){
        double probability = this.probability;
        double[] crowdArray = this.crowdArray;
        double[] uncrowdArray = this.uncrowdArray;
        double rnd = ThreadLocalRandom.current().nextDouble();
        if(rnd < rate){
            probability = ElFarolBar.mutate(probability, range);
        }
        for(int i = 0; i < crowdArray.length; i++){
            rnd = ThreadLocalRandom.current().nextDouble();
            if(rnd < rate){
                crowdArray[i] = ElFarolBar.mutate(crowdArray[i], range);
            }
            rnd = ThreadLocalRandom.current().nextDouble();
            if(rnd < rate){
                uncrowdArray[i] = ElFarolBar.mutate(uncrowdArray[i], range);
            }
        }
        return new State(probability, crowdArray, uncrowdArray);
    }

    public int simulator(boolean isCrowded){
        return ElFarolBar.distributer(isCrowded ? crowdArray : uncrowdArray);
    }

    public double getProbability(){
        return probability;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(probability);
        Arrays.stream(crowdArray).forEach(s -> builder.append(" ").append(s));
        Arrays.stream(uncrowdArray).forEach(s -> builder.append(" ").append(s));
        return builder.toString();
    }



}
