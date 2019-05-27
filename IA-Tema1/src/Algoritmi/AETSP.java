package Algoritmi;

import Controller.Controller;
import Helpers.Helper;
import Models.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class AETSP {
    private Controller Ctrl = new Controller();
    private Helper help = new Helper();
    Random r = new Random();

    public int AlgoritmEvolTSP(int n , int max_iter) {
        TSP tsp = new TSP();
        tsp = Ctrl.getTspData();

        List<int[]> Population = new ArrayList<int[]>();
        List<int[]> pool = new ArrayList<int[]>();
        List<int[]> mutation = new ArrayList<int[]>();
        List<int[]> crossover = new ArrayList<int[]>();

        for (int i = 0; i < n; i++) {

            int[] solutieCurrenta = IntStream.range(0, 70).toArray();
            solutieCurrenta = help.shuffleArray(solutieCurrenta);

            Population.add(solutieCurrenta);
        }
        int[] globalBest = selectBest(Population, false, tsp);
        int globalBestFit = help.evalSolTSP(globalBest , tsp);
        int nrIter = 0;
        int bestofGen = 0;
        while (nrIter < max_iter) {
            pool = selectParents(Population, tsp);
            mutation = selectMutation(Population);
           crossover = selectCrossover(Population);

            Population = selectNewPopulatie(pool, mutation,crossover, n, tsp);

            int[] best = selectBest(Population, false, tsp);
            bestofGen = help.evalSolTSP(best,tsp);

            if(globalBestFit > bestofGen){
                globalBest = best;
                globalBestFit = bestofGen;
            }

            nrIter++;
        }
        System.out.println(":Best of the gen:" + globalBestFit);
        return globalBestFit;

    }


    // =================================== algoritm helpers ===============

    public List<int[]> selectNewPopulatie(List<int[]> pool, List<int[]> mutatie, List<int[]> mutatie2, int popSize , TSP tsp) {
        List<int[]> finalPopulatie = new ArrayList<int[]>(popSize);
        int nrPop = popSize;

        for (int i = 0; i < nrPop; i++) {
            if (i <= nrPop / 4) {
                finalPopulatie.add(selectBest(pool, true, tsp));
            } else if (nrPop / 4 < i && i <= nrPop / 2) {
                finalPopulatie.add(selectBest(mutatie, true , tsp));
            } else if (nrPop / 2 < i && i < nrPop * 3 / 4) {
                finalPopulatie.add(selectBest(mutatie2, true , tsp));
            } else {
                int[] x = IntStream.range(0, 70).toArray();
                x = help.shuffleArray(x);
                finalPopulatie.add(x);
            }

        }
        return finalPopulatie;
    }

    public int[] selectBest(List<int[]> Populatie, boolean todelete ,TSP tsp) {
        int[] best = Populatie.get(0);
        int indexBest = 0;
        for (int i = 1; i < Populatie.size(); i++) {
            if (help.evalSolTSP(best,tsp) > help.evalSolTSP(Populatie.get(i),tsp)) {
                best = Populatie.get(i);
                indexBest = i;
            }

        }
        if (todelete) {
            Populatie.remove(indexBest);
        }
        return best;
    }


    public List<int[]> selectMutation(List<int[]> Pop) {
        List<int[]> mutatePop = new ArrayList<int[]>();

        for(int i  = 0 ; i < Pop.size() ; i++){
            int[] permutare = Pop.get(i);

            int aux = 0;
            int pos1 = r.nextInt(70);
            int pos2 = r.nextInt(70);

            aux = permutare[pos1];
            permutare[pos1] = permutare[pos2];
            permutare[pos2] = aux;

            mutatePop.add(permutare);
        }

        for (int i = 0 ; i < Pop.size() ; i++){
            int[] permutare = Pop.get(i);

            int aux = 0;
            int pos1 = r.nextInt(70);
            int pos2 = pos1 + r.nextInt(70-pos1);



            int nriter = (pos2-pos1)/2;
            for(int j = 0 ; j < nriter ; j++){
                aux = permutare[pos1];
                permutare[pos1] = permutare[pos2];
                permutare[pos2] = aux;

                pos1++;
                pos2--;
            }
            mutatePop.add(permutare);

        }


        return mutatePop;
    }

    public List<int[]> selectCrossover(List<int[]> Pop) {
        List<int[]> crossoverPop = new ArrayList<int[]>();


        for (int iter = 0; iter < Pop.size(); iter++) {
            int[] child1 = new int[70];
            int[] child2 = new int[70];
            int leftPos = r.nextInt(68);
            int rightPos =leftPos + r.nextInt(70 - leftPos);

            int[] p1 = Pop.get(iter);
            int[] p2 = Pop.get(iter + 1);

            for (int i = leftPos; i < rightPos; i++) {
                child1[i] = p1[i];
                child2[i] = p2[i];
            }

            int sizeOfRest = p1.length - (rightPos - leftPos) ;
            int[] duplic1 = new int[sizeOfRest];
            int[] duplic2 = new int[sizeOfRest];

            int addIter1 = 0;
            int addIter2 = 0;
            for (int rest = rightPos; rest < p1.length; rest++) {
                boolean exist1 = false;
                boolean exist2 = false;
                for (int i = leftPos; i < rightPos; i++) {
                    if (child1[i] == p2[rest]) {
                        exist1 = true;
                    }
                    if (child2[i] == p1[rest]) {
                        exist2 = true;
                    }
                }
                if (!exist1) {
                    duplic1[addIter1] = p2[rest];
                    addIter1++;
                }
                if (!exist2) {
                    duplic2[addIter2] = p1[rest];
                    addIter2++;
                }
            }


            for (int rest = 0; rest < rightPos ; rest++) {
                boolean exist1 = false;
                boolean exist2 = false;
                for (int i = leftPos; i < rightPos; i++) {
                    if (child1[i] == p2[rest]) {
                        exist1 = true;
                    }
                    if (child2[i] == p1[rest]) {
                        exist2 = true;
                    }
                }
                if (!exist1) {
                    duplic1[addIter1] = p2[rest];
                    addIter1++;
                }
                if (!exist2) {
                    duplic2[addIter2] = p1[rest];
                    addIter2++;
                }
            }

            // fill child with rest ////
            int posOfRest = 0;
            for(int i = rightPos ; i < p1.length;i++){
                child1[i] = duplic1[posOfRest];
                child2[i] = duplic2[posOfRest];
                posOfRest++;
            }
            for(int i = 0 ; i < leftPos;i++){
                child1[i] = duplic1[posOfRest];
                child2[i] = duplic2[posOfRest];
                posOfRest++;
            }

            crossoverPop.add(child1);
            crossoverPop.add(child2);
            iter++;
        }


        return crossoverPop;
    }


    public List<int[]> selectParents(List<int[]> Populatie, TSP tsp) {


        int nrPop = Populatie.size();

        for (int i = 0; i < nrPop; i++) {
            int[] x = Populatie.get(i);
            Populatie.remove(i);
            if (i <= nrPop / 4) {
                Populatie.add(i, selectByFittnes(Populatie, tsp));
            } else if (nrPop / 4 < i && i < nrPop * 3 / 4) {
                Populatie.add(i, selectTurnir(Populatie, nrPop / 3, tsp));
            } else
                Populatie.add(x);
        }
        return Populatie;
    }

    public int[] selectByFittnes(List<int[]> Pop, TSP tsp) {
        int[] populatieFitness = new int[Pop.size()];
        float totalFitness = 0;
        for (int i = 0; i < Pop.size(); i++) {
            populatieFitness[i] = help.evalSolTSP(Pop.get(i), tsp);
            totalFitness += help.evalSolTSP(Pop.get(i), tsp);
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

    public int[] selectTurnir(List<int[]> Pop, int k, TSP tsp) {

        int[] best = Pop.get(r.nextInt(Pop.size()));
        int i = 0;
        while (i < k - 1) {
            int rnd = r.nextInt(Pop.size());
            if (help.evalSolTSP(best, tsp) > help.evalSolTSP(Pop.get(rnd), tsp)) {
                best = Pop.get(rnd);
            }
            i++;
        }
        return best;

    }


}
