package Models;

import Models.Obiect;

import java.util.ArrayList;
import java.util.List;

public class Rucsac {
    private int maxWeight;
    private List<Obiect> obiectStack = new ArrayList<Obiect>();

    public Rucsac(int maxWeight, List<Obiect> obiectStack) {

        this.maxWeight = maxWeight;
        this.obiectStack = obiectStack;
    }

    public Rucsac(){
    }



    public int getMaxWeight() {
        return maxWeight;
    }

    public List<Obiect> getObiectStack() {
        return obiectStack;
    }
    public int getNrObiecte(){
        return obiectStack.size();
    }



    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setObiectStack(List<Obiect> obiectStack) {
        this.obiectStack = obiectStack;
    }
}
