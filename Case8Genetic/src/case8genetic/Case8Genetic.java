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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author David
 */
public class Case8Genetic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            // TODO code application logic here
            
//            File file = new File("C:\\Users\\David\\Pictures\\jokerGenetico.jpg");
//            BufferedImage image = ImageIO.read(file);
//            int p = image.getRGB(0, 0);
//            Color c = new Color(p);
            ImageProcessor imageProcessor = new ImageProcessor();
            imageProcessor.processImage("C:\\Users\\David\\Pictures\\guacamaya.jpg");
            GeneticAlgorithm algorithm = new GeneticAlgorithm(imageProcessor.getPixelAreas(), imageProcessor.getColorPallete());
            int totalSamples = imageProcessor.getSamplesTotal();
            int maxNumberPolygons = (totalSamples*60)/100;
            algorithm.runAlgorithm(100000,"guacamaya");
            System.out.println("case8genetic.Case8Genetic.main()");
       
    }
    
}
