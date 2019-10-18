/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author David
 */
public class PixelMatrixCell {
    private ArrayList<Pixel> samplesTaken;
    private ArrayList<Pixel> currentSamples;
    private final double PROBABILITY_REDUCTION_PORCENTAGE = 0.10;
    private int minimumX;
    private int minimumY;
    private int maximumX;
    private int maximumY;
    private double cellSampleProbability;
    private Map<Integer, List<Pixel>> pixelColorGroups;

    public PixelMatrixCell(int minimumX, int minimumY, int maximumX, int maximumY) {
        samplesTaken = new ArrayList<>();
        currentSamples = new ArrayList<>();
        this.minimumX = minimumX;
        this.minimumY = minimumY;
        this.maximumX = maximumX;
        this.maximumY = maximumY;
        this.cellSampleProbability = 1;
        this.pixelColorGroups = new HashMap<>();
    }

    PixelMatrixCell() {
        samplesTaken = new ArrayList<>();
        currentSamples = new ArrayList<>();
        this.cellSampleProbability = 1;
        this.pixelColorGroups = new HashMap<>();
    }

    public double getCellSampleProbability() {
        return cellSampleProbability;
    }
    
    public void  formPixelGroups(){
        samplesTaken.forEach((pixel) -> {
            List<Pixel> tempList = pixelColorGroups.get(pixel.getColorNumber());
            
            if(tempList == null){
                tempList = new ArrayList<>();
                pixelColorGroups.put(pixel.getColorNumber(), tempList);
            }
            tempList.add(pixel);
        });
    }
    
    
    public void processCurrentSample(){
        boolean samplesAllWhite = true;
        for(Pixel pixel : currentSamples){
            if(!checkWhitePixel(pixel.getRedValue(), pixel.getBlueValue(), pixel.getGreenValue())){
                samplesAllWhite = false;
                break;
            }
        }
        if(samplesAllWhite){
            cellSampleProbability = cellSampleProbability - PROBABILITY_REDUCTION_PORCENTAGE;  //((cellSampleProbability*PROBABILITY_REDUCTION_PORCENTAGE)/100);
        }
        currentSamples.clear();
    }
    
//    public void sortSampleList(){
//        //No es necesario hacer un sorting ya que generamos los grupos con un hashmap
//        //Collections.sort(samplesTaken, new CustomPixelColorComparator());
//        
//    }

    
    private boolean checkWhitePixel(int red, int blue, int green){
        boolean pixelIsWhite = (red == 255) && (blue == 255) && (green == 255);
        return  pixelIsWhite;
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

    public int getSamplesTaken() {
        return samplesTaken.size() - getWhiteSamples();
    }
    
    public int getCellSamples(){
        Long pixels = samplesTaken.stream().filter(pixel -> pixel.getColorNumber() != 0).count();
        int returnVal = pixels.intValue();
        return returnVal;
    }
    
    private int getWhiteSamples(){
        Long whitePixels =  samplesTaken.stream().filter(pixel -> 0 == pixel.getColorNumber()).count();
        return whitePixels.intValue();
    }

    public Map<Integer, List<Pixel>> getPixelColorGroups() {
        return pixelColorGroups;
    }
    
    
    
    
}
