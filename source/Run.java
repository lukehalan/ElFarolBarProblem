/*
 * Copyright (c) 2019 Halan.
 */


public class Run {

    private static int repetitions;
    //exercise1
    private static int question;
    private static String prob;
    //exercise2
    private static String strategy;
    private static int state;
    private static int crowded;
    //exercise3
    private static int lambda;
    private static int h;
    private static int weeks;
    private static int max_t;


    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (args[i].substring(1).equals("question"))
                    question = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("repetitions"))
                    repetitions = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("strategy"))
                    strategy = args[++i];
                else if (args[i].substring(1).equals("prob"))
                    prob = args[++i];
                else if (args[i].substring(1).equals("state"))
                    state = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("lambda"))
                    lambda = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("h"))
                    h = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("weeks"))
                    weeks = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("max_t"))
                    max_t = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("crowded"))
                    crowded = Integer.parseInt(args[++i]);
            }
        }

        switch (question) {
            case 1:
                exercise1(prob, repetitions);
                break;
            case 2:
                exercise2(strategy, state, crowded, repetitions);
                break;
            case 3:
                exercise3(lambda, h, weeks, max_t);
                break;
            default:
                System.err.println("The question number is not valid");
                break;
        }
    }

    private static void exercise1(String prob_str, int repetitions) {
        double[] probArray = ElFarolBar.probArrayFromString(prob_str);
        for (int i = 0; i < repetitions; i++) {
            System.out.println(ElFarolBar.distributer(probArray));
        }
    }

    private static void exercise2(String strategy_str, int state, int crowded_int, int repetitions) {
        Strategy strategy = Strategy.parser(strategy_str);
        boolean crowded = crowded_int > 0;
        for (int i = 0; i < repetitions; i++) {
            strategy.simulator(state, crowded);
            System.out.println((strategy.isSimulated() ? 1 : 0) + "\t" + strategy.getSimulatedState());
        }
    }

    private static void exercise3(int lambda, int h, int weeks, int max_t) {
        GA ga = new GA(lambda, h, weeks, max_t);
        ga.run();
    }


}
