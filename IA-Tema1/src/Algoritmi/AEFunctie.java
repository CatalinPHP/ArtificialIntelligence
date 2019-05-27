package Algoritmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AEFunctie {
    protected Random r = new Random();
    int bound = 50;

    public double[] AlgoritmMinimizareFunctie(int nrPop, int nrIter) {
        List<double[]> Population = new ArrayList<double[]>();
        List<double[]> pool;
        List<double[]> crossoverPop;
        List<double[]> mutationPop1;
        List<double[]> mutationPop2;
        int sizeFit = nrPop * 3 / 4 - nrPop / 2;
        double[] oldFittnes = new double[sizeFit];
        double[] newFittnes = new double[sizeFit];
        fillPop(nrPop, Population);
        int generatii = 0;
        double[] globalBest = new double[2];

        double[] bestofGeneration = new double[nrIter];
        //====== algoritm

        while (generatii < nrIter) {
            pool = selectParents(Population);
            mutationPop1 = mutationSimple(pool, 0.8);
            for (int i = Population.size() / 2; i < Population.size() * 3 / 4; i++) {
                newFittnes[i-Population.size()/2] = fittnesFunction(Population.get(i));
            }
            if (generatii == 0) {
                oldFittnes = newFittnes.clone();
            }
            mutationPop2 = mutationNeuniforma(Population, generatii, newFittnes, oldFittnes, nrIter);
            oldFittnes = newFittnes.clone();

            Population = selectNewPopulatie(pool,mutationPop1,mutationPop2,nrPop);
            double[] best = selectBest(Population,false);

            bestofGeneration[generatii] = fittnesFunction(best);
            if (generatii == 1){
                globalBest = best;
            }
            if(fittnesFunction(globalBest) > fittnesFunction(best)){
               globalBest = best;
            }
//            System.out.println("Best of generation : "+fittnesFunction(best));
            generatii++;
        }
//        System.out.println("\nBest of all generation : "+fittnesFunction(globalBest));

        return bestofGeneration;

    }

    //=============== algortim functions ===================================
    public List<double[]> mutationNeuniforma(List<double[]> Population, int t, double[] newGenFit, double[] oldGenFit, int nrGen) {
        List<double[]> ChildPop = new ArrayList<double[]>();
        double rnd = r.nextDouble();
        int p = 0;
        int start = Population.size() / 2;
        int end = Population.size() * 3 / 4;
        for (int i = start; i < end; i++) {
            double[] mutateChild = new double[2];
            if (newGenFit[i - start] < oldGenFit[i - start])
                p = 1;
            else p = -1;
            int pos = r.nextInt(2);
            double x = Population.get(i)[pos];
            if (p == 1) {
                mutateChild[1 - pos] = x + (2 - x) * (1 - Math.pow(rnd, 1 - t / nrGen));
                mutateChild[pos] = Population.get(i)[0];

            } else {
                mutateChild[1 - pos] = x - (x + 2) * (1 - Math.pow(rnd, 1 - t / nrGen));
                mutateChild[pos] = Population.get(i)[0];
            }
            ChildPop.add(mutateChild);
        }


        return ChildPop;
    }


    public List<double[]> mutationSimple(List<double[]> Population, double prob_m) {
        List<double[]> ChildPop = new ArrayList<double[]>();

        for (int i = 0; i < Population.size(); i++) {
            double[] vector = Population.get(i);
            double[] vectorChild = new double[2];
            for (int j = 0; j < 2; j++) {
                double rnd = Math.random();
                if (rnd < prob_m) {
                    vectorChild[j] = getRandomDouble();
                } else
                    vectorChild[j] = vector[j];
            }
            ChildPop.add(vectorChild);
        }
        return ChildPop;
    }


    public List<double[]> selectParents(List<double[]> Populatie ) {
        int nrPop = Populatie.size();
        List<double[]> pool = new ArrayList<double[]>();
        for (int i = 0; i < nrPop; i++) {
            if (i <= nrPop / 4) {
                pool.add(i, selectByFittnes(Populatie));
            } else if (nrPop / 4 < i && i < nrPop * 3 / 4) {
                pool.add(i, selectTurnir(Populatie, nrPop * 3/ 4));
            } else {
                double newR = r.nextDouble();
                double[] x = new double[2];
                x[0] = newR * r.nextInt(bound) * -1;
                x[1] = newR * r.nextInt(bound);
                pool.add(x);
            }

        }
        return pool;
    }

    public double[] selectTurnir(List<double[]> Pop, int k) {

        double[] best = Pop.get(r.nextInt(Pop.size()));
        int i = 0;
        while (i < k - 1) {
            int rnd = r.nextInt(Pop.size());
            if (fittnesFunction(best) < fittnesFunction(Pop.get(rnd))) {
                best = Pop.get(rnd);
            }
            i++;
        }
        return best;

    }

    public double[] selectByFittnes(List<double[]> Pop) {
        double[] populatieFitness = new double[Pop.size()];
        float totalFitness = 0;
        for (int i = 0; i < Pop.size(); i++) {
            populatieFitness[i] = fittnesFunction(Pop.get(i));
            totalFitness += fittnesFunction(Pop.get(i));
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

    public double fittnesFunction(double[] x) {
        double F = 0;
        double F1 = 0;
//        Main function
        F = (4 - 2.1 * Math.pow(x[0], 2) + Math.pow(x[0], 4) / 3) * Math.pow(x[0], 2) +
                +x[0] * x[1] + (-4 + 4 * Math.pow(x[1], 2)) * Math.pow(x[1], 2);

        // Another function...
        F1 = (1+ Math.pow(x[0]+x[1]+1,2)*(19-14*x[0]+3*x[1]-14*x[1]+x[0]*x[1]+3*Math.pow(x[1],2)))* (30 +
                + Math.pow(2*x[0] - 3*x[1],2) * (18 - 32*x[0] + 12*x[0] + 48*x[1] - 36*x[0]*x[1] + 27*Math.pow(x[1],2)));
        return F;
    }

    public void fillPop(int nrPop, List<double[]> Pop) {
        int sgn = 1;
        for (int i = 0; i < nrPop; i++) {
            double[] vector = new double[2];
            Random nr = new Random();
            double r = nr.nextDouble();
            if (0.5 < nr.nextDouble()) {
                sgn = -1;
            } else sgn = 1;
            vector[0] = r * nr.nextInt(bound) * sgn;
            vector[1] = r * nr.nextInt(bound) * sgn;
            Pop.add(vector);
        }
    }

    public double getRandomDouble() {
        int sgn = 1;
        double x;
        Random nr = new Random();
        double r = nr.nextDouble();
        if (0.5 < nr.nextDouble()) {
            sgn = -1;
        } else sgn = 1;
        x = r * nr.nextInt(bound) * sgn;
        return x;
    }

    public List<double[]> selectNewPopulatie(List<double[]> pool, List<double[]> mutatie1, List<double[]> mutatie2, int popSize) {
        List<double[]> finalPopulatie = new ArrayList<double[]>(popSize);
        int nrPop = popSize;

        for (int i = 0; i < nrPop; i++) {
            if (i <= nrPop / 4) {
                finalPopulatie.add(selectBest(pool, true));
            } else if (nrPop / 4 < i && i <= nrPop / 2) {
                finalPopulatie.add(selectBest(mutatie1, true));
            } else if (nrPop / 2 < i && i < nrPop * 3/ 4) {
                finalPopulatie.add(selectBest(mutatie2, true));
            }
            else {
                double[] x = new double[]{getRandomDouble(),getRandomDouble()};
                finalPopulatie.add(x);
            }

        }
        return finalPopulatie;


    }

    public double[] selectBest(List<double[]> Populatie, boolean todelete) {
        double[] best = Populatie.get(0);
        int indexBest = 0;
        for (int i = 1; i < Populatie.size(); i++) {
            if (fittnesFunction(best) > fittnesFunction(Populatie.get(i))) {
                best = Populatie.get(i);
                indexBest = i;
            }

        }
        if (todelete) {
            Populatie.remove(indexBest);
        }
        return best;
    }
}
