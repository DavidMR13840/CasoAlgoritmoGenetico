/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.util.List;

/**
 *
 * @author David
 */
public class PixelArea {
    private int minimumX, minimumY, maximumX, maximumY;
    private int colorGroupNumber;
    private double porcentage;
    private int groupSize;
    
    public PixelArea(int colorNumber, List<Pixel> pixelColorGroup, int totalPixelSamples){
        this.colorGroupNumber = colorNumber;
        this.groupSize = pixelColorGroup.size();
        setRanges(pixelColorGroup);
        setPorcentage(totalPixelSamples);
    }

    public int getMinimumX() {
        return minimumX;
    }

    public int getMinimumY() {
        return minimumY;
    }

    public int getMaximumX() {
        return maximumX;
    }

    public int getMaximumY() {
        return maximumY;
    }

    public int getColorGroupNumber() {
        return colorGroupNumber;
    }

    public double getPorcentage() {
        return porcentage;
    }
    
    private void setPorcentage(int totalPixelSamples){
        this.porcentage = (groupSize*100.0)/totalPixelSamples;
    }

    private void setRanges(List<Pixel> pixelColorGroup) {
        int minX = -1, minY = -1, maxX = -1 , maxY = -1;
        for(Pixel pixel : pixelColorGroup){
            if(minX < 0){
                minX = pixel.getxValue();
            }
            if(minY < 0){
                minY = pixel.getyValue();
            }
            if(maxX < 0){
                maxX = pixel.getxValue();
            }
            if(maxY < 0){
                maxY = pixel.getyValue();
            }
            if(minX > pixel.getxValue()){
                minX = pixel.getxValue();
            }
            if(minY > pixel.getyValue()){
                minY = pixel.getyValue();
            }
            if(maxX < pixel.getxValue()){
                maxX = pixel.getxValue();
            }
            if(maxY < pixel.getyValue()){
                maxY = pixel.getyValue();
            }
            
        }
        this.maximumX = maxX;
        this.minimumX = minX;
        this.maximumY = maxY;
        this.minimumY = minY;
    }
    
}
