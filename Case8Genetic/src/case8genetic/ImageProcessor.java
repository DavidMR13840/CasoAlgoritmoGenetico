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
    
   
    public ImageProcessor(){
        
        setPixelMatrix();
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
    
//    private void setColorPallete(){
//        colorPallete = new ArrayList<>();
//        colorPallete.add(new CustomColor(150, 255, 150, 255, MATRIX_SIZE, MATRIX_SIZE, Color.yellow, MATRIX_SIZE, name))
//    }
    
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
        
    }
    
    public void analizeImageCell(BufferedImage imageToProcess, PixelMatrixCell matrixCell){
        int analyzedSamples = 0;
        Random random = new Random();
        if(random.nextDouble() <= matrixCell.getCellSampleProbability()){
            while(analyzedSamples < SAMPLE_TAKEN_SIZE){
                int currentX = random.nextInt(matrixCell.getMaximumX()-matrixCell.getMinimumX()) + matrixCell.getMaximumX();
                int currentY = random.nextInt(matrixCell.getMaximumY()-matrixCell.getMinimumY()) + matrixCell.getMinimumY();
                Color pixelColor = new Color(imageToProcess.getRGB(currentX, currentY));
                Pixel newPixel = new Pixel(currentX, currentY,pixelColor.getRed(), pixelColor.getBlue(), pixelColor.getGreen());//comparar con la paleta

                if(matrixCell.addPixel(newPixel)){
                    analyzedSamples++;
                }
            }
            matrixCell.processCurrentSample();
        }

    }
}
