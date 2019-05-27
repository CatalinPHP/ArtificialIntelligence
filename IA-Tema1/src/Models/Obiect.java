package Models;

public class Obiect {
    private int weight;
    private int value;

    public Obiect(int v , int w){
        this.weight = w;
        this.value = v;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {

        this.value = value;
    }
}
