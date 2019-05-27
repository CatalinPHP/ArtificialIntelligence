package Algoritmi;

import Helpers.Helper;
import Models.TSP;

import java.util.Random;
import java.util.stream.IntStream;

public class TabuSearch {

    public void TabuAlgoritm(int iteratii , TSP tsp , int tabuPoint){
        Helper help = new Helper();
        int[] vecin;
        int delta;
        Random rnd = new Random();

        int[] solutieCurrenta = IntStream.range(0, 70).toArray();
        solutieCurrenta = help.shuffleArray(solutieCurrenta);
        int best = help.evalSolTSP(solutieCurrenta, tsp);
        int tabuIter = 0;
        int[] x;
        int[][] Memorie = new int[70][70];
        while( tabuIter < iteratii){
            int index1 = rnd.nextInt(70);
            int index2 = rnd.nextInt(70);

            while(!(Memorie[index1][index2] == 0)){
                index1 = rnd.nextInt(70);
                index2 = rnd.nextInt(70);
            }
            if(Memorie[index1][index2] == 0){
                vecin = getVecinTabu(solutieCurrenta , index1 , index2);
                if(help.evalSolTSP(vecin, tsp) < best){
                    best = help.evalSolTSP(vecin,tsp);
                }
                solutieCurrenta = vecin;
                updateMemorie(Memorie, index1 ,index2 , tabuPoint);
            }


            tabuIter++;
        }

        System.out.println("best mesaj: "+best);
    }

    public void updateMemorie(int[][] Memorie , int x , int y , int tabuPoint){
        for(int i = 0 ; i < 70 ; i++){
            for(int j = i+1; j < 70 ; j++){
                if( Memorie[i][j] != 0){
                    Memorie[i][j] = Memorie[i][j]-1;
                }
            }
        }
        Memorie[x][y] = tabuPoint;
    }

    public int[] getVecinTabu(int[] x1 , int xPoz , int yPoz ) {
        Random rnd = new Random();
        int[] x;
        int i = x1.length - 1;
        x = x1.clone();
        // Simple swap
        int a = x[xPoz];
        x[xPoz] = x[yPoz];
        x[yPoz] = a;
        return x;
    }
}
