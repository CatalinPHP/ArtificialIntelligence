package Models;

public class TSP {
    private int nrOrase;
    private int[][] matriceDistante;
    private int[] x;
    private int[] y;

    public TSP(){

    }
    public TSP(int nrOrase, int[][] matriceDistante, int[] x, int[] y) {
        this.nrOrase = nrOrase;
        this.matriceDistante = matriceDistante;
        this.x = x;
        this.y = y;
    }

    public int getNrOrase() {
        return nrOrase;
    }

    public void setNrOrase(int nrOrase) {
        this.nrOrase = nrOrase;
    }

    public int[][] getMatriceDistante() {
        return matriceDistante;
    }

    public void setMatriceDistante(int[][] matriceDistante) {
        this.matriceDistante = matriceDistante;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }
}
