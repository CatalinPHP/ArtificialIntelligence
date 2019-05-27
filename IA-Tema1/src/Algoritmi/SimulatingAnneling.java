package Algoritmi;

import Helpers.Helper;
import Models.TSP;

import java.util.Random;
import java.util.stream.IntStream;

public class SimulatingAnneling {

    public void SimulatingAlg(double T, double minT, double alpha, int k, TSP tsp) {
        Helper help = new Helper();
        int[] vecin;
        int delta;
        Random rnd = new Random();


        int[] solutieCurrenta = IntStream.range(0, 70).toArray();
        solutieCurrenta = help.shuffleArray(solutieCurrenta);
        int best = help.evalSolTSP(solutieCurrenta, tsp);
        while (T > minT) {
            vecin = help.getVecin(solutieCurrenta);
            int evalS1 = help.evalSolTSP(vecin, tsp);
            int evalS2 = help.evalSolTSP(solutieCurrenta, tsp);
            delta = evalS1 - evalS2;
            if (delta < 0) {
                if (evalS1 < best) {
                    best = evalS1;
                }
                solutieCurrenta = vecin;
            } else if ((Math.random()) < Math.exp(-delta / T ))
                solutieCurrenta = vecin;
            double exp = Math.random();
            k--;
            if (k < 0) {
                break;
            }
            T = T * alpha;
        }

        String mesaj = "";
        for (int i = 0; i < solutieCurrenta.length; i++) {
            mesaj += solutieCurrenta[i];
            mesaj += " ";
        }
        System.out.println("Solutia este:" + mesaj);
        System.out.println("Best:" + best);
    }
}
