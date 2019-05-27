package Controller;
import Models.*;
import Helpers.*;
import Algoritmi.CautareAleatoare;
import Algoritmi.CautareNextAscent;

import Models.Obiect;
import Models.Rucsac;
import Models.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class Controller {

    public TSP getTspData() {
        String filePath = "C:\\Users\\ghiur\\Documents\\workspace1\\IA-Tema1\\src\\st70.tsp";
        ReadWriteFile upFile = new ReadWriteFile();
        String text = upFile.readFromFile(filePath, 7);

        String[] textSplit;
        textSplit = text.split("\\s+");

        int[] x = new int[70];
        int[] y = new int[70];
        int pos = 0;

        for (int i = 0; i < textSplit.length - 3; ) {
            x[pos] = Integer.parseInt(textSplit[i + 1]);
            y[pos] = Integer.parseInt(textSplit[i + 2]);
            i = i + 3;
            pos++;

        }

        int[][] distancePoints = new int[70][70];
        distancePoints = calculateDistance(x,y);
        TSP tsp = new TSP(70,distancePoints,x,y);
        return tsp;
    }

    public static int[][] calculateDistance(int[] x, int[] y) {
        int nrPoints = x.length;


        int[][] dP = new int[nrPoints][nrPoints];

        for (int i = 0; i < nrPoints-1; i++) {
            for (int j = i+1; j < nrPoints; j++) {
                Double radicalIntreg = sqrt((x[j] - x[i]) * (x[j] - x[i]) + (y[j] - y[i]) * (y[j] - y[i]));
                dP[i][j] = radicalIntreg.intValue();
                dP[j][i] = radicalIntreg.intValue();
            }
        }
        return dP;
    }


    public Rucsac fillRucsac() {
        String filePath = "C:\\Users\\ghiur\\Documents\\workspace1\\IA-Tema1\\src\\read1.txt";
        ReadWriteFile upFile = new ReadWriteFile();
        String text = upFile.readFromFile(filePath, 0);

        // Initializare atribute controler

        int nrObiecte = 0;
        int maxWeight = 0;
        List<Obiect> obiectList = new ArrayList<Obiect>();

        //Build Controller.Controller of object;
        String[] textSplit;
        textSplit = text.split("\\s+");

        maxWeight = Integer.parseInt(textSplit[textSplit.length - 1]);

        int weight = 0;
        int val = 0;

        for (int i = 1; i < textSplit.length - 1; ) {
            val = Integer.parseInt(textSplit[i + 1]);
            weight = Integer.parseInt(textSplit[i + 2]);
            Obiect obj = new Obiect(val, weight);
            obiectList.add(obj);
            i += 3;
        }


        Rucsac rucsac = new Rucsac(maxWeight, obiectList);
        return rucsac;

    }

    public void creareRucsac() {
        System.out.println("Numarul de obiecte din rucsac:");
        Scanner in = new Scanner(System.in);
        int nrObiecte = in.nextInt();
        System.out.println("Greutatea maxima:");
        int maxWeight = in.nextInt();

        List<Obiect> obiecteList = new ArrayList<Obiect>();
        for (int i = 0; i < nrObiecte; i++) {
            System.out.println("Obiectul " + i);
            System.out.println("Dati valoarea lui:");
            int value = in.nextInt();
            System.out.println("Dati greutatea lui:");
            int weight = in.nextInt();
            Obiect o = new Obiect(value, weight);
            obiecteList.add(o);
        }

        Rucsac r = new Rucsac(maxWeight, obiecteList);
        int k;
        double opt = minMeniu();
        while (opt != 0) {
            switch ((int) opt) {
                case 1:
                    System.out.println("Numarul de cautari:");
                    k = in.nextInt();
                    CautareAleatoare cautAleator = new CautareAleatoare();
                    cautAleator.CautareAleat(r, k);
                    break;
                case 2:
                    System.out.println("Numarul de cautari:");
                    k = in.nextInt();
                    CautareNextAscent nextA = new CautareNextAscent();
                    nextA.cautareNextAscent(r, k);
                    break;
                default:
                    System.out.println("Ai gresit optiunea, mai incearca");
            }
            opt = minMeniu();
        }

    }

    public static double minMeniu() {
        System.out.println("1.Pentru cautare aleatoare");
        System.out.println("2.Pentru cautare nextAscend");
        System.out.println("0.Exit");
        System.out.println("Alegeti obtiune:");
        Scanner in = new Scanner(System.in);
        int Opt = in.nextInt();
        return Opt;
    }

}
