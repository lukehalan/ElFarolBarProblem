/*
 * Copyright (c) 2019 Halan.
 */

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Population extends ArrayList<Strategy> {

    private static final long serialVersionUID = -135392962374553684L;
    private boolean crowded;
    private int individualsInBar;
    private int week = -1;
    private int generation = 0;

    public static Population random(int lambda, int h) {
        Population population = new Population();
        for (int i = 0; i < lambda; i++) {
            population.add(Strategy.random(h));
        }
        population.crowded = false;
        return population;
    }

    public Population develop(int k, double chi, double range, int precision, Constant.Type type) {
        Population population = new Population();
        while (population.size() < size()) {
            ArrayList<Strategy> vAdd = new ArrayList<>();
            if(type == Constant.Type.GDR){
                vAdd.add(Strategy.crossover(tournamentSelection(k), tournamentSelection(k)));
            } else if (type == Constant.Type.MUTATE){
                vAdd.add(tournamentSelection(k).mutate(chi, range));
            }
            int sl = Math.min(size() - population.size(), vAdd.size());
            for (int idx = 0; idx < sl; idx++) {
                population.add(vAdd.get(idx));
            }
        }
        population.generation = generation + 1;
        return population;
    }

    private Strategy tournamentSelection(int k) {
        ArrayList<Strategy> winners = new ArrayList<>();
        int bFitness = Integer.MIN_VALUE;
        for (int i = 0; i <= k; i++) {
            int rnd = ThreadLocalRandom.current().nextInt(size());
            Strategy contestant = get(rnd);
            int fitness = contestant.getPayoff();
            if (fitness >= bFitness) {
                if (fitness > bFitness) {
                    winners.clear();
                    bFitness = fitness;
                }
                winners.add(contestant);
            }
        }
        return winners.get(ThreadLocalRandom.current().nextInt(winners.size()));
    }


    public int getWeek() {
        return week;
    }

    public int getIndividualsInBar(){
        return individualsInBar;
    }

    public int getGeneration() {
        return generation;
    }

    public boolean isCrowded(){
        return crowded;
    }

    public void simulator() {
        individualsInBar = 0;
        for (Strategy strategy : this) {
            strategy.simulator(crowded);
            individualsInBar += strategy.isSimulated() ? 1 : 0;
        }
        crowded = ((double) individualsInBar / (double) size()) >= 0.6;
        stream().forEach(s -> s.updatePayoff(crowded));
        week++;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(week);
        builder.append("\t").append(generation);
        builder.append("\t").append(individualsInBar);
        builder.append("\t").append(crowded ? '1' : '0');
        stream().forEach(s -> builder.append("\t").append(s.isSimulated() ? '1' : '0'));
        return builder.toString();
    }



}
