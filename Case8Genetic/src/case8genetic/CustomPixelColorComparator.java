/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.util.Comparator;

/**
 *
 * @author David
 */
public class CustomPixelColorComparator implements Comparator<Pixel>{
    @Override
    public int compare(Pixel pixel1, Pixel pixel2){
        return pixel1.getColorNumber() - pixel2.getColorNumber();
    }
    
}
