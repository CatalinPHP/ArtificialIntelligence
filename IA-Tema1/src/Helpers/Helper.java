package Helpers;


import Models.*;

import java.util.List;
import java.util.Random;

public class Helper {
    /**
     * Genereza un sir de 0 si 1 aleator
     *
     * @param x
     */
    public void randomArray(int[] x) {
        Random nrRand = new Random();
        for (int i = 0; i < x.length; i++) {
            x[i] = nrRand.nextInt(2);
        }
    }

    public boolean validSol(int[] x, List<Obiect> o, int maxWeight) {
        int sumWeight = 0;
        for (int i = 0; i < x.length; i++) {
            sumWeight += x[i] * o.get(i).getWeight();
        }

        if (sumWeight < maxWeight) {
            return true;
        }
        return false;
    }

    public int[] getOneZero(int x[], int OneZero) {
        int nrOne = 0;

        for (int i = 0; i < x.length; i++) {
            if (x[i] == OneZero)
                nrOne++;
        }
        int indexOne[] = new int[nrOne];
        int j = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] == OneZero) {
                indexOne[j] = i;
                j++;
            }
        }
        return indexOne;

    }

    public int getNrOneZero(int x[] , int oneZero){
        int nrOne = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] == oneZero)
                nrOne++;
        }
        return nrOne;
    }

    public int[] vecinCurent(int x[], int index) {
        x[index] = 1;
        return x;
    }

    public int[] evalSol(List<Obiect> o, int x[]) {
        int sumVal = 0;
        int sumWeight = 0;
        for (int i = 0; i < x.length; i++) {
            sumVal += x[i] * o.get(i).getValue();
            sumWeight += x[i] * o.get(i).getWeight();
        }
        int val[] = new int[2];
        val[0] = sumVal;
        val[1] = sumWeight;
        return val;
    }

    //======================== Laborator2 ====================

    public int[] shuffleArray(int[] x) {
        Random rnd = new Random();
        for (int i = x.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = x[index];
            x[index] = x[i];
            x[i] = a;
        }
        return x;
    }

    public int[] getVecin(int[] x1 ) {
        Random rnd = new Random();
        int[] x;
        int i = x1.length - 1;
        int index1 = rnd.nextInt(i + 1);
        int index2 = rnd.nextInt(i + 1);
        x = x1.clone();
        // Simple swap
        int a = x[index1];
        x[index1] = x[index2];
        x[index2] = a;
        return x;
    }

    public int evalSolTSP(int[] x , TSP tsp){
        int sol = 0;
        int nrOrase = x.length;

        int[][] distance = tsp.getMatriceDistante();
        for (int i = 0; i < nrOrase-3; i++){
            sol += distance[x[i]][x[i+1]];
//            System.out.println("Sa blocat:"+x[i]+" esi "+x[i+1]);

        }

        sol += distance[0][x.length-1];
        return sol;
    }

}


