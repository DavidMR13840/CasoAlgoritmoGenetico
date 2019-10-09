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
    private ArrayList<Pixel> currentSamples;
    
    private int minimumX;
    private int minimumY;
    private int maximumX;
    private int maximumY;

    public PixelMatrixCell(int minimumX, int minimumY, int maximumX, int maximumY) {
        samplesTaken = new ArrayList<>();
        currentSamples = new ArrayList<>();
        this.minimumX = minimumX;
        this.minimumY = minimumY;
        this.maximumX = maximumX;
        this.maximumY = maximumY;
    }

    PixelMatrixCell() {
        samplesTaken = new ArrayList<>();
        currentSamples = new ArrayList<>();
    }
    
    public void processCurrentSample(){
        
        currentSamples.clear();
    }

    public boolean addPixel(Pixel pixel){
        if(samplesTaken.contains(pixel)){
            return false;
        }else{
            samplesTaken.add(pixel);
            currentSamples.add(pixel);
            return true;
        }
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
