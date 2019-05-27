package Models;

public class Particle {
    private double[] pozitie ;
    private double[] viteza ;

    public Particle(double[] pozitie , double[] viteza){
        this.pozitie = pozitie;
        this.viteza = viteza;
    }

    public double[] getViteza() {
        return viteza;
    }

    public void setViteza(double[] viteza) {
        this.viteza = viteza;
    }

    public double[] getPozitie() {

        return pozitie;
    }

    public void setPozitie(double[] pozitie) {
        this.pozitie = pozitie;
    }
}
