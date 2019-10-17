/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author David
 */
public class ImageProcessor {
    private final int  IMAGE_CELL_SIZE =  102;
    private final int MATRIX_SIZE = 10;
    private final int TARGET_SAMPLE_SIZE = 4162;
    private final int SAMPLE_TAKEN_SIZE = 520;
    private PixelMatrixCell[][] pixelMatrixCells;
    private ArrayList<CustomColor> colorPallete;
    private ArrayList<PixelArea> pixelAreas;
   
    public ImageProcessor(){
        
        setPixelMatrix();
        setColorPallete();
        this.pixelAreas = new ArrayList<>();
        System.out.println("case8genetic.ImageProcessor.<init>()");
        
    }
    
    private void  setPixelMatrix(){
        pixelMatrixCells = new PixelMatrixCell[MATRIX_SIZE][MATRIX_SIZE];
        int startingX = 0;
        int startingY = 0;
        int lastX = IMAGE_CELL_SIZE;
        int lastY = IMAGE_CELL_SIZE;
        for(int row = 0; row < MATRIX_SIZE; row++){
            for(int column = 0; column < MATRIX_SIZE; column++){
                pixelMatrixCells[row][column] = new PixelMatrixCell();
                pixelMatrixCells[row][column].setMinimumX(startingX);
                pixelMatrixCells[row][column].setMinimumY(startingY);
                pixelMatrixCells[row][column].setMaximumX(lastX);
                pixelMatrixCells[row][column].setMaximumY(lastY);
                startingX = lastX;
                lastX += IMAGE_CELL_SIZE;
            }
            startingX = 0;
            lastX = IMAGE_CELL_SIZE;
            startingY = lastY;
            lastY += IMAGE_CELL_SIZE;
            
        }
    }
    
    private void setColorPallete(){
        colorPallete = new ArrayList<>();
        colorPallete.add(new CustomColor(Color.RED, 1));
        colorPallete.add(new CustomColor(Color.blue, 2));
        colorPallete.add(new CustomColor(Color.GREEN, 3));
        colorPallete.add(new CustomColor(Color.YELLOW, 4));
        colorPallete.add(new CustomColor(Color.BLACK, 5));
        colorPallete.add(new CustomColor(Color.CYAN, 6));
        colorPallete.add(new CustomColor(Color.ORANGE, 7));
        colorPallete.add(new CustomColor(Color.MAGENTA, 8));
        colorPallete.add(new CustomColor(Color.GRAY, 9));
        colorPallete.add(new CustomColor(Color.WHITE, 0));
    }
    
    private BufferedImage getBufferedImage(String imageLocation){
        BufferedImage imageToProcess = null;
        try {
            imageToProcess = ImageIO.read(new File(imageLocation));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return imageToProcess;
    }
    
    public void processImage(String imageLocation){
        BufferedImage imageToProcess = getBufferedImage(imageLocation);
        if(imageToProcess == null){
            return;
        }
        int evaluatedSamples = 0;
        while (evaluatedSamples < TARGET_SAMPLE_SIZE) {
            for(int row = 0; row < MATRIX_SIZE; row++){
                for(int column = 0; column < MATRIX_SIZE;column++){
                    analizeImageCell(imageToProcess, pixelMatrixCells[row][column]);
                }
            }
            evaluatedSamples = evaluatedSamples + SAMPLE_TAKEN_SIZE;
        }
        sortSectorsByColor();
        
       
    }

    public ArrayList<CustomColor> getColorPallete() {
        return colorPallete;
    }

    public ArrayList<PixelArea> getPixelAreas() {
        return pixelAreas;
    }
    
    public void analizeImageCell(BufferedImage imageToProcess, PixelMatrixCell matrixCell){
        int analyzedSamples = 0;
        Random random = new Random();
        if(random.nextDouble() <= matrixCell.getCellSampleProbability()){
            while(analyzedSamples < SAMPLE_TAKEN_SIZE){
                int currentX = random.nextInt(matrixCell.getMaximumX()-matrixCell.getMinimumX()) + matrixCell.getMinimumX();
                    int currentY = random.nextInt(matrixCell.getMaximumY()-matrixCell.getMinimumY()) + matrixCell.getMinimumY();
                try {
                    
                    Color pixelColor = new Color(imageToProcess.getRGB(currentX, currentY));
                    Pixel newPixel = new Pixel(currentX, currentY,pixelColor.getRed(), pixelColor.getBlue(), pixelColor.getGreen());//comparar con la paleta
                    setPixelColorGroup(newPixel);
                    if(matrixCell.addPixel(newPixel)){
                    analyzedSamples++;
                }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(currentX);
                    System.out.println(currentY);
                }
                

                
            }
            matrixCell.processCurrentSample();
        }

    }
    
    private int getSamplesTotal(){
        int totalSamples = 0;
        for(int row = 0; row < MATRIX_SIZE; row++){
                for(int column = 0; column < MATRIX_SIZE;column++){
                   totalSamples += pixelMatrixCells[row][column].getCellSamples();
                }
            }
        return totalSamples;
    }
    
    private void sortSectorsByColor(){
        int totalSamples = getSamplesTotal();
        for(int row = 0; row < MATRIX_SIZE; row++){
                for(int column = 0; column < MATRIX_SIZE;column++){
                    pixelMatrixCells[row][column].formPixelGroups();
                    for(int colorNumber = 1; colorNumber < colorPallete.size(); colorNumber++ ){
                        if(pixelMatrixCells[row][column].getPixelColorGroups().get(colorNumber) != null){
                            pixelAreas.add(new PixelArea(colorNumber, pixelMatrixCells[row][column].getPixelColorGroups().get(colorNumber),totalSamples));
                        }
                    }
                }
            }
    }
    
    private void setPixelColorGroup(Pixel pixel){
        int rgbDistance = -1;
        int colorGroup = 0;
        for(CustomColor color : colorPallete){
            if(rgbDistance < 0){
                colorGroup = color.getColorNumber();
                rgbDistance = color.compareDistance(pixel.getRedValue(), pixel.getGreenValue(), pixel.getBlueValue());
            }else{
                int newDistance = color.compareDistance(pixel.getRedValue(), pixel.getGreenValue(), pixel.getBlueValue());
                if(newDistance < rgbDistance){
                    colorGroup = color.getColorNumber();
                    rgbDistance = newDistance;
                }
            }
        }
        pixel.setColorNumber(colorGroup);
    }
}
