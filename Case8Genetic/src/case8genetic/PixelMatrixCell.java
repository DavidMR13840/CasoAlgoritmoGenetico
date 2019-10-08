/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.util.ArrayList;

/**
 *
 * @author David
 */
public class PixelMatrixCell {
    private ArrayList<Pixel> samplesTaken;
    private int minimumX;
    private int minimumY;
    private int maximumX;
    private int maximumY;

    public PixelMatrixCell(int minimumX, int minimumY, int maximumX, int maximumY) {
        samplesTaken = new ArrayList<>();
        this.minimumX = minimumX;
        this.minimumY = minimumY;
        this.maximumX = maximumX;
        this.maximumY = maximumY;
    }

    PixelMatrixCell() {
        
    }

    
    public int getMinimumX() {
        return minimumX;
    }

    public void setMinimumX(int minimumX) {
        this.minimumX = minimumX;
    }

    public int getMinimumY() {
        return minimumY;
    }

    public void setMinimumY(int minimumY) {
        this.minimumY = minimumY;
    }

    public int getMaximumX() {
        return maximumX;
    }

    public void setMaximumX(int maximumX) {
        this.maximumX = maximumX;
    }

    public int getMaximumY() {
        return maximumY;
    }

    public void setMaximumY(int maximumY) {
        this.maximumY = maximumY;
    }
    
    
    
    
}
