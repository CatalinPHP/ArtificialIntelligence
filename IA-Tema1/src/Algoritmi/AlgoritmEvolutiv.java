package Algoritmi;
import Helpers.*;
import Models.*;
import Controller.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AlgoritmEvolutiv {
    protected Rucsac ruc;
    protected Helper help = new Helper();
    protected Random r = new Random();

    public AlgoritmEvolutiv() {
        Controller ctrl = new Controller();
        this.ruc = ctrl.fillRucsac();
    }


    public void AlgoritmEvol(int popSize, int maxGen, double prob_c, double prob_m) {

        int t = 0;
        List<int[]> Populatie;
        List<int[]> pool;
        List<int[]> crossoverPop;
        List<int[]> mutationPop;
        Populatie = fillPop(popSize);
        int[] bestOfAll = selectBest(Populatie, false);

        while (t < maxGen) {
            pool = selectParents(Populatie);
            crossoverPop = crossover(pool);
            mutationPop = mutatie(pool, prob_m);

            Populatie = selectNewPopulatie(pool, crossoverPop, mutationPop, popSize);
            t = t + 1;

            if (help.evalSol(ruc.getObiectStack(), selectBest(Populatie, false))[0] > help.evalSol(ruc.getObiectStack(), bestOfAll)[0]) {
                bestOfAll = selectBest(Populatie, false);
            }
        }

//
        String mesaj = "";
        for (int i = 0; i < bestOfAll.length; i++) {
            mesaj += bestOfAll[i];
        }
//        mesaj += "\nSecond : ";
//        for(int i = 0; i< y.length;i++){
//            mesaj += y[i];
//        }
        System.out.println("Best :" + mesaj + "\n" + "Best val:" + help.evalSol(ruc.getObiectStack(), bestOfAll)[0]);

    }


    /// =============== helper for evolutiv algoritm ============


    public List<int[]> selectNewPopulatie(List<int[]> pool, List<int[]> crossover, List<int[]> mutatie, int popSize) {
        List<int[]> finalPopulatie = new ArrayList<int[]>(popSize);
        int nrPop = popSize;


        int[] x = new int[ruc.getNrObiecte()];
        for (int i = 0; i < nrPop; i++) {
            if (i <= nrPop / 5) {
                finalPopulatie.add(selectBest(pool, true));
            } else if (nrPop / 5 < i && i <= nrPop * 3 / 5) {
                finalPopulatie.add(selectBest(crossover, true));
            } else if (nrPop * 3 / 5 < i && i < nrPop * 4 / 5) {
                finalPopulatie.add(selectBest(mutatie, true));
            } else {
                help.randomArray(x);
                if (help.validSol(x, ruc.getObiectStack(), ruc.getMaxWeight())) {
                    finalPopulatie.add(x);
                } else
                    i--;

            }


        }
        return finalPopulatie;


    }


    public List<int[]> mutatie(List<int[]> Populatie, double prob_m) {
        List<int[]> childPop = new ArrayList<int[]>();

        for (int i = 0; i < Populatie.size(); i++) {
            int[] p1 = Populatie.get(i);
            int lenghtP = p1.length;
            int[] o1 = new int[lenghtP];
            for (int j = 0; j < lenghtP; j++) {
                double rnd = Math.random();
                if (rnd < prob_m) {
                    o1[j] = r.nextInt(2);
                } else
                    o1[j] = p1[j];


            }
            if (help.validSol(o1, ruc.getObiectStack(), ruc.getMaxWeight())) {
                childPop.add(o1);
            }

        }
        return childPop;
    }


    public List<int[]> crossover(List<int[]> Populatie) {
        List<int[]> childPop = new ArrayList<int[]>();
        Random r = new Random();
        for (int i = 0; i < Populatie.size() - 1; i++) {
            int[] p1 = Populatie.get(i);
            int[] p2 = Populatie.get(i + 1);
            int lenghtParent = p1.length;
            int[] o1 = new int[lenghtParent];
            int[] o2 = new int[lenghtParent];
            int rnd = r.nextInt(lenghtParent - 2);
            i++;
            for (int j = 0; j < lenghtParent; j++) {
                if (j < rnd) {
                    o1[j] = p1[j];
                    o2[j] = p2[j];
                } else {
                    o2[j] = p1[j];
                    o1[j] = p2[j];
                }

            }
            if (help.validSol(o1, ruc.getObiectStack(), ruc.getMaxWeight())) {
                childPop.add(o1);
            }
            if (help.validSol(o2, ruc.getObiectStack(), ruc.getMaxWeight())) {
                childPop.add(o2);
            }
        }
        return childPop;
    }


    public List fillPop(int popSize) {
        List<int[]> Populatie = new ArrayList<int[]>();
        int[] x = new int[ruc.getNrObiecte()];
        for (int i = 0; i < popSize; i++) {
            help.randomArray(x);
            if (help.validSol(x, ruc.getObiectStack(), ruc.getMaxWeight())) {
                Populatie.add(x.clone());
            } else
                i--;
        }
        return Populatie;
    }

    public List<int[]> selectParents(List<int[]> Populatie) {
        int nrPop = Populatie.size();

        for (int i = 0; i < nrPop; i++) {
            int[] x = Populatie.get(i);
            Populatie.remove(i);
            if (i <= nrPop / 4) {
                Populatie.add(i, selectByFittnes(Populatie));
            } else if (nrPop / 4 < i && i < nrPop * 3 / 4) {
                Populatie.add(i, selectTurnir(Populatie, 100));
            } else
                Populatie.add(x);
        }
        return Populatie;
    }

    public int[] selectByFittnes(List<int[]> Pop) {
        int[] populatieFitness = new int[Pop.size()];
        float totalFitness = 0;
        for (int i = 0; i < Pop.size(); i++) {
            populatieFitness[i] = help.getNrOneZero(Pop.get(i), 1);
            totalFitness += help.getNrOneZero(Pop.get(i), 1);
//            System.out.println(i);
        }

        double r = Math.random();
        int proportie = 0;
        float interval = 0;
        while (proportie < populatieFitness.length) {
            interval += populatieFitness[proportie];
            float inter = interval / totalFitness;
            if (r < interval / totalFitness) {
                return Pop.get(proportie);
            }
            proportie++;
        }
        return null;
    }

    public int[] selectTurnir(List<int[]> Pop, int k) {

        int[] best = Pop.get(r.nextInt(Pop.size()));
        int i = 0;
        while (i < k - 1) {
            int rnd = r.nextInt(Pop.size());
            if (help.evalSol(ruc.getObiectStack(), best)[0] < help.evalSol(ruc.getObiectStack(), Pop.get(rnd))[0]) {
                best = Pop.get(rnd);
            }
            i++;
        }
        return best;

    }

    public int[] selectBest(List<int[]> Populatie, boolean todelete) {
        int[] best = Populatie.get(0);
        int indexBest = 0;
        for (int i = 1; i < Populatie.size(); i++) {
            if (help.evalSol(ruc.getObiectStack(), best)[0] < help.evalSol(ruc.getObiectStack(), Populatie.get(i))[0]) {
                best = Populatie.get(i);
                indexBest = i;
            }

        }
        if (todelete) {
            Populatie.remove(indexBest);
        }
        return best;
    }

    public List<int[]> selectBestOfALL(int poptoSelect , List<int []> pool , List<int []> crossover , List<int []> mutatie) {
        List<int[]> finalPopulatie = new ArrayList<int[]>(poptoSelect);
        List<int[]> selectionPopulatie = new ArrayList<int[]>();
        int totalNrPopSel = pool.size() + crossover.size() + mutatie.size();
        for (int i = 0; i < totalNrPopSel; i++) {
            if (i < pool.size()) {
                selectionPopulatie.add(pool.get(i));
            } else if (i < pool.size() + crossover.size()) {
                selectionPopulatie.add(crossover.get(i - pool.size()));
            } else {
                selectionPopulatie.add(mutatie.get(i - pool.size() - crossover.size()));
            }
        }
        int[] x = new int[ruc.getNrObiecte()];
        for (int i = 0; i < poptoSelect; i++) {
            if (i < poptoSelect * 4 / 5) {
                finalPopulatie.add(selectBest(selectionPopulatie, true));
            } else {
                help.randomArray(x);
                if (help.validSol(x, ruc.getObiectStack(), ruc.getMaxWeight())) {
                    finalPopulatie.add(x);
                } else
                    i--;
            }

        }
        return finalPopulatie;
    }


}
