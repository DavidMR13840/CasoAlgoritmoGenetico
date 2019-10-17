/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

/**
 *
 * @author David
 */
public class Polygon {
    private int firstX, firstY, secondX, secondY;
    private int colorGroupNumber;
    private int genomeNumber;

    public Polygon(int firstX, int firstY, int secondX, int secondY, int colorGroupNumber, int genomeNumber) {
        this.firstX = firstX;
        this.firstY = firstY;
        this.secondX = secondX;
        this.secondY = secondY;
        this.colorGroupNumber = colorGroupNumber;
        this.genomeNumber = genomeNumber;
    }

    
    
    public int getFirstX() {
        return firstX;
    }

    public void setFirstX(int firstX) {
        this.firstX = firstX;
    }

    public int getFirstY() {
        return firstY;
    }

    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }

    public int getSecondX() {
        return secondX;
    }

    public void setSecondX(int secondX) {
        this.secondX = secondX;
    }

    public int getSecondY() {
        return secondY;
    }

    public void setSecondY(int secondY) {
        this.secondY = secondY;
    }

    public int getColorGroupNumber() {
        return colorGroupNumber;
    }

    public void setColorGroupNumber(int colorGroupNumber) {
        this.colorGroupNumber = colorGroupNumber;
    }

    public int getGenomeNumber() {
        return genomeNumber;
    }

    public void setGenomeNumber(int genomeNumber) {
        this.genomeNumber = genomeNumber;
    }
    
    
}
