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
    private final int CELL_SAMPLE_SIZE = 4162;
    private int[][] cellMatrix;
   
    public ImageProcessor(){
        cellMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
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
        
    }
    
    public void analizeImageCell(BufferedImage imageToProcess, int startingX, int startingY, int maxX, int maxY, int cellColumn, int cellRow){
        int analyzedSamples = 0;
        Random random = new Random();
        while(analyzedSamples < CELL_SAMPLE_SIZE){
            int currentX = random.nextInt(maxX-startingX) + startingX;
            int currentY = random.nextInt(maxY-startingY) + startingY;
            Color pixelColor = new Color(imageToProcess.getRGB(currentX, currentY));
            Pixel newPixel = new Pixel(currentX, currentY, pixelColor.getRed(), pixelColor.getBlue(), pixelColor.getGreen());
            if(cellMatrix[cellRow][cellColumn])
        }
    }
}
