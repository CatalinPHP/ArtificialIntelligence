package Algoritmi;

import Models.Particle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleSwarmOptimization {
    int leftX = -50;
    int rightX = 50;
    double vitezainit = 0;

    Random r = new Random();
    AEFunctie AE = new AEFunctie();

    public double PSOAlgoritm(int n, int max_iter, double w, double c1, double c2) {
        vitezainit = (rightX - leftX)/2510;
        List<Particle> Particule = new ArrayList<Particle>();
        List<double []> pbest = new ArrayList<double[]>();
        double fittnesPart = 0;
        double[] gbest =new double[2];

        for (int i = 0; i < n; i++) {
            double[] x = new double[2];
            double[] v = new double[2];
            x[0] = getRandomDouble();
            x[1] = getRandomDouble();

            v[0] = vitezainit;
            v[1] = vitezainit;

            pbest.add(x);
            Particle p = new Particle(x, v);
            Particule.add(p);
        }
        gbest = AE.selectBest(pbest,false);

        int iter = 0;
        while (iter < max_iter) {
            for (int i = 0; i < n; i++) {
                fittnesPart = AE.fittnesFunction(Particule.get(i).getPozitie());
//                System.out.println(fittnesPart);
                if (AE.fittnesFunction(pbest.get(i)) > fittnesPart) {
                    pbest.set(i,Particule.get(i).getPozitie());

                }

                if (AE.fittnesFunction(gbest) > fittnesPart) {
                    gbest = Particule.get(i).getPozitie();
                }
                double[] v1 = new double[2];
                double[] x1 = new double[2];

                v1[0] = w*Particule.get(i).getViteza()[0] + c1 * r.nextDouble() * (pbest.get(i)[0] - fittnesPart) + c2*r.nextDouble()*(gbest[0] - fittnesPart);
                v1[1] = w*Particule.get(i).getViteza()[1] + c1 * r.nextDouble() * (pbest.get(i)[1] - fittnesPart) + c2*r.nextDouble()*(gbest[1] - fittnesPart);

                Particule.get(i).setViteza(v1);

                x1[0] = v1[0] + Particule.get(i).getPozitie()[0];
                x1[1] = v1[1] + Particule.get(i).getPozitie()[1];

                Particule.get(i).setPozitie(x1);
            }
            iter++;
        }

        System.out.println("best of particule it is : " + AE.fittnesFunction(gbest));
        return AE.fittnesFunction(gbest);
    }


    public double bestFromPop(double[] x) {
        double best = x[0];

        for (int i = 0; i < x.length; i++) {
            if (best > x[i]) {
                best = x[i];
            }
        }

        return best;
    }

    public double getRandomDouble() {
        int sgn = 1;
        double x;
        Random nr = new Random();
        double rd = Math.random();
        if (0.5 < Math.random()) {
            sgn = -1;
        } else sgn = 1;
        x = rd * nr.nextInt(3) * sgn;
        return x;
    }
}
