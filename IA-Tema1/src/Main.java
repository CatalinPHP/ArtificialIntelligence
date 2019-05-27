import Algoritmi.*;
import Controller.Controller;
import Helpers.Diagram;
import Models.Rucsac;
import Models.TSP;

import java.util.Random;
import java.util.Scanner;

public class Main {
    Controller ctrl = new Controller();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k;
        Main m = new Main();

        double opt = meniu();
        while (opt != 0) {
            switch ((int) opt) {
                case 1:
                    m.creareRucsac();
                    break;
                case 2:
                    System.out.println("Numarul de cautari:");
                    k = in.nextInt();
                    m.cautareAleatoare(k);
                    break;
                case 3:
                    System.out.println("Numarul de cautari:");
                    k = in.nextInt();
                    m.cautareNextAscend(k);
                    break;
                case 4:
                    System.out.println("Numarul de cautari:");
                    k = in.nextInt();
                    System.out.println("Temperatura T:");
                    double T = in.nextDouble();
                    in.nextLine();
                    System.out.println("Minimul minT:");
                    double minT = Double.parseDouble(in.nextLine());
                    System.out.println("Alpha:");
                    double alpha = Double.parseDouble(in.nextLine());
                    m.test(T, minT, alpha, k);
                    break;
                case 5:
                    System.out.println("Populatia :");
                    int popSize = in.nextInt();
                    System.out.println("Urmasii ai generatiei:");
                    int maxGen = in.nextInt();
                    m.AlgoEvolut(popSize, maxGen);
                    break;
                case 6:
                    System.out.println("Nriteratii :");
                    int iteratii = in.nextInt();
                    System.out.println("Tabuiter point:");
                    int tabuPoint = in.nextInt();
                    m.TabuSearch(iteratii, tabuPoint);
                    break;
                case 7:
                    m.AEFunction();
                    break;
                case 8:
                    m.PSOFunction();
                    break;
                case 9:
                    m.TSPAlgoritmEvol();
                    break;
                default:
                    System.out.println("Ai gresit optiunea, mai incearca");
            }
            opt = meniu();
        }
        System.out.println("Program terminat");


    }

    public static double meniu() {
        System.out.println("--------------------------------------------");
        System.out.println("1.Pentru a crea rucsac:");
        System.out.println("2.Pentru cautare aleatoare");
        System.out.println("3.Pentru cautare nextAscend");
        System.out.println("4.Pentru Models.TSP cu Algoritmi.SimulatingAnneling");
        System.out.println("5.Pentru Algoritmi.AlgoritmEvolutiv");
        System.out.println("6.Pentru Models.TSP cu Algoritmi.TabuSearch");
        System.out.println("7.Pentru Algoritm evolutiv Functie");
        System.out.println("8.Pentru Particule Swarm Optimization Functie");
        System.out.println("9.Pentru Evol TSP");
        System.out.println("0.Exit");
        System.out.println("Alegeti obtiune:");
        Scanner in = new Scanner(System.in);
        int Opt = in.nextInt();
        return Opt;

    }

    public void creareRucsac() {
        ctrl.creareRucsac();
    }

    public void cautareAleatoare(int k) {
        Rucsac r = new Rucsac();


        r = ctrl.fillRucsac();

        CautareAleatoare cautAleator = new CautareAleatoare();
        cautAleator.CautareAleat(r, k);
    }

    public void cautareNextAscend(int k) {
        Rucsac r = new Rucsac();

        r = ctrl.fillRucsac();

        CautareNextAscent cautNextAscend = new CautareNextAscent();
        cautNextAscend.cautareNextAscent(r, k);
    }

    public void test(double T, double minT, double alpha, int k) {
        TSP tsp = new TSP();
        tsp = ctrl.getTspData();
        SimulatingAnneling SA = new SimulatingAnneling();
        SA.SimulatingAlg(T, minT, alpha, k, tsp);
    }

    public void AlgoEvolut(int popSize, int maxGen) {
        AlgoritmEvolutiv AE = new AlgoritmEvolutiv();
        AE.AlgoritmEvol(popSize, maxGen, 0.8, 0.2);
    }

    public void TabuSearch(int iter, int tabuPoint) {
        TSP tsp = new TSP();
        tsp = ctrl.getTspData();
        TabuSearch TS = new TabuSearch();
        TS.TabuAlgoritm(iter, tsp, tabuPoint);
    }

    public void AEFunction() {
        AEFunctie fct = new AEFunctie();
        double avg = 0;
        for (int i = 0; i < 10; i++) {
            fct.AlgoritmMinimizareFunctie(30, 50);
        }
        System.out.println("avg :" + avg/10 );
    }

    public void PSOFunction() {
        ParticleSwarmOptimization PSO = new ParticleSwarmOptimization();
        double avg = 0;
        for (int i = 0; i < 10; i++) {
            avg += PSO.PSOAlgoritm(30, 400, 0.5, 1.5, 1.5);
        }
        System.out.println("avg :" + avg/10 );
    }

    public void TSPAlgoritmEvol() {
        AETSP TSPsol = new AETSP();
        int avg = 0;
        for (int i = 0 ; i < 10 ; i++) {
            avg += TSPsol.AlgoritmEvolTSP(100, 500);
        }

        System.out.println("Avg of 10:" + avg/10);



    }
}
