/*
 * Copyright (c) 2019 Halan.
 */

import java.io.PrintStream;

public class GA {

    private Constant.Type type = Constant.Type.GDR;
    private int lambda;
    private int h;
    private int weeks;
    private int max_t;
    private int penalty = 0;

    public GA(int lambda, int h, int weeks, int max_t) {
        this.lambda = lambda;
        this.h = h;
        this.weeks = weeks;
        this.max_t = max_t;
    }


    public void run() {
        startAlgorithm(System.out);
    }

    public void startAlgorithm(PrintStream printStream) {
        int k = 2;
        int precision = 5;
        double chi = 5;
        double range = 0.1;
        boolean logging = printStream != System.out;
        Population population = Population.random(lambda, h);
        Population best_population = population;
        double best_average = 0;
        int bestPenalty = Integer.MIN_VALUE;
        while (population.getGeneration() < max_t) {
            int individualInBar = 0;
            penalty = 0;
            while (population.getWeek() < weeks - 1) {
                population.simulator();
                if (logging) {
                    individualInBar += population.getIndividualsInBar();
                    updatePenalty(population);
                } else {
                    printStream.println(population);
                }
            }
            if (penalty > bestPenalty) {
                best_population = population;
                best_average = (double) individualInBar / (double) weeks;
            }
            population = population.develop(k, chi, range, precision, type);
        }
        if (logging) {
            StringBuilder builder = new StringBuilder();
            builder.append(best_population.getWeek()).append("\t").append(best_population.getGeneration()).append("\t")
                    .append(best_average).append("\t").append(h).append("\t").append(lambda).append("\t").append(k)
                    .append("\t").append(chi).append("\t").append(range).append("\t").append(precision).append("\t")
                    .append(type);

            printStream.println(builder);
        }
    }

    private void updatePenalty(Population population){
        if(!population.isCrowded()){
            penalty += population.getIndividualsInBar();
        }
        else{
            penalty -= 100*population.getIndividualsInBar();
        }
    }


}
