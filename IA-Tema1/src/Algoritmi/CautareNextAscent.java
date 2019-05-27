package Algoritmi;

import Helpers.Helper;
import Helpers.ReadWriteFile;
import Models.Obiect;
import Models.Rucsac;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CautareNextAscent {

    public void cautareNextAscent(Rucsac r, int k) {
        List<Obiect> ListaObiecte = new ArrayList<Obiect>();
        ListaObiecte = r.getObiectStack();
        int nrObiecte = r.getObiectStack().size();
        int current[] = new int[nrObiecte];
        int cloneCurrent[] = new int[nrObiecte];
        int best[];
        int avrgVal = 0;
        boolean valid = false;
        int maxWeight = r.getMaxWeight();
        Helper help = new Helper();

        String mesaj = "Run      Valoare    Greutate    GreutateMaxima      BinarSol\n";


        //=============== Validari de inceput ==========================

        help.randomArray(current);
        valid = help.validSol(current, ListaObiecte, maxWeight);
        int indexZero[];
        int indexOne[] = help.getOneZero(current, 1);

        while (!valid) {
            Random nrRand = new Random();
            int pozModif = indexOne[nrRand.nextInt(indexOne.length)];
            current[pozModif] = 0;
            indexOne = help.getOneZero(current, 1);
            valid = help.validSol(current, ListaObiecte, maxWeight);
        }
        int nrEvaluari = 0;
        best = current.clone();
        indexZero = help.getOneZero(current, 0);


        //=================Algoritm=====================================


        for (int i = 0; i < indexZero.length; i++) {
            int nextIndex = indexZero[i];           // luam primul index zero
            cloneCurrent = current.clone();
            int x[] = help.vecinCurent(cloneCurrent, nextIndex); // plasam primul vecin al lui current
            boolean validVecin = help.validSol(x, ListaObiecte, maxWeight); // validam vecinul


            // daca nu este valid cautam alt vecin pana cand gasim unul valid daca nu gasim nici unul ne oprim

            while (!validVecin) {
                i++;
                if (i < indexZero.length) {
                    nextIndex = indexZero[i];
                    cloneCurrent = current.clone();
                    x = help.vecinCurent(cloneCurrent, nextIndex);
                    validVecin = help.validSol(x, ListaObiecte, maxWeight);
                } else {
                    break;
                }
            }
            // vedem daca ii valid vecinul si apoi comparam solutiile;

            if (validVecin) {
                int evalBest = help.evalSol(ListaObiecte, best)[0]; // evaluam solutia currenta
                int evalVecin = help.evalSol(ListaObiecte, x)[0];
                nrEvaluari++;
                int sumWeight = 0;
                int sumValue = 0;
                String arrayX = "";
                if (nrObiecte <= 20) {
                    for (int v = 0; v < x.length; v++) {
                        arrayX = arrayX + Integer.toString(x[v]);
                    }
                }
                for (int j = 0; j < current.length; j++) {
                    sumValue += x[j] * ListaObiecte.get(j).getValue();
                    sumWeight += x[j] * ListaObiecte.get(j).getWeight();
                }
                avrgVal += sumValue;
                mesaj += nrEvaluari + "           " + sumValue + "       "
                        + sumWeight + "       " + maxWeight + "         " + arrayX + "\n";


                if (evalVecin > evalBest) {
                    best = x.clone();
                    current = x.clone();
                   // indexZero = help.getOneZero(current, 0);
                }
            }
            // aici facem restart dupa ce am verificat toti vecini ,
            // si sunt invalizi sau mai mici decat bestsi mai putem face evaluari.

            if (!validVecin || i >= indexZero.length-1 || k%10==0) {
                help.randomArray(current);
                valid = help.validSol(current, ListaObiecte, maxWeight);
                while (!valid) {
                    Random nrRand = new Random();
                    int pozModif = indexOne[nrRand.nextInt(indexOne.length)];
                    current[pozModif] = 0;
                    indexOne = help.getOneZero(current, 1);
                    valid = help.validSol(current, ListaObiecte, maxWeight);
                }
                indexZero = help.getOneZero(current, 0);
                i = -1;
            }
            if (nrEvaluari >= k) {
                break;
            }


        }
        // for
        int bestVal = help.evalSol(ListaObiecte, best)[0];
        int bestWeight = help.evalSol(ListaObiecte, best)[1];

        avrgVal = avrgVal / k;

        mesaj += "------------------------------------------------------------\n";
        mesaj += "Best val este:" + bestVal + " si valoarea medie :" + avrgVal + "\n";
        mesaj += "Greutatea este:" + bestWeight + "\n";

        ReadWriteFile write = new ReadWriteFile();
        try {
            write.writeToFile(mesaj, "C:\\Users\\ghiur\\Documents\\workspace1\\IA-Tema1\\src\\write.txt");
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(mesaj);

    } // clasa cautare Aleatoare
}
