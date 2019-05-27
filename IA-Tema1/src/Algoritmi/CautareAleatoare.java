package Algoritmi;

import Helpers.Helper;
import Helpers.ReadWriteFile;
import Models.Obiect;
import Models.Rucsac;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CautareAleatoare {
    /**
     * @param r
     * @param k
     */

    public void CautareAleat(Rucsac r, int k) {
        //===== var declare =====

        List<Obiect> ListaObiecte = new ArrayList<Obiect>();
        ListaObiecte = r.getObiectStack();
        int nrObiecte = r.getObiectStack().size();
        int x[] = new int[nrObiecte];
        boolean valid = false;
        int maxWeight = r.getMaxWeight();
        Helper help = new Helper();


        //======= algoritmul ===


        int run = 0;
        int bestVal = 0;
        int avgVal = 0;
        int bestW = 0;
        int avgW = 0;

        String mesaj = "Run      Valoare    Greutate    GreutateMaxima\n";
        for (int iter = 0; iter < k; iter++) {
            help.randomArray(x);
            valid = help.validSol(x, ListaObiecte, maxWeight);
            int indexOne[] = help.getOneZero(x, 1);


            while (!valid) {
                Random nrRand = new Random();
                int pozModif = indexOne[nrRand.nextInt(indexOne.length)];
                x[pozModif] = 0;
                valid = help.validSol(x, ListaObiecte, maxWeight);
            }

            if (valid) {
                int sumVal = 0;
                int sumWeight = 0;
                for (int i = 0; i < x.length; i++) {
                    sumVal += x[i] * ListaObiecte.get(i).getValue();
                    sumWeight += x[i] * ListaObiecte.get(i).getWeight();
                }
                run++;
                avgW += sumWeight;
                if (sumWeight > bestW) {
                    bestW = sumWeight;
                }
                avgVal += sumVal;
                if (sumVal > bestVal) {
                    bestVal = sumVal;
                }
                String arrayX = "";
                if (nrObiecte <= 20) {
                    for (int i = 0; i < x.length; i++) {
                        arrayX = arrayX + Integer.toString(x[i]);
                    }
                }
                mesaj += run + "           " + sumVal + "       "
                        + sumWeight + "       " + maxWeight +"         " +arrayX +"\n";
            }

        }

        avgW = avgW / k;
        avgVal = avgVal / k;
        mesaj += "----------------------------------------------\n";
        mesaj += "BestVal : " + bestVal + " BestWeight: " + bestW + "\n";
        mesaj += "AvrgVal : " + avgVal + " AvrgWeught: " + avgW + "\n";
        //---------------------- Write to File------------------------

        ReadWriteFile write = new ReadWriteFile();
        try

        {
            write.writeToFile(mesaj, "C:\\Users\\ghiur\\Documents\\workspace1\\IA-Tema1\\src\\write.txt");
        } catch (
                Exception e)

        {
            System.out.println(e);
        }

        //----------------------Display on console------------------
        System.out.println(mesaj);


    }


}
