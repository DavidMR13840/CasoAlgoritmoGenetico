/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.awt.Color;

/**
 *
 * @author David
 */
public class CustomColor {
    
    private final Color color;
    private final int colorNumber;

    public CustomColor( Color color, int number) {
        
        this.color = color;
        this.colorNumber = number;
    }
    
    public int compareDistance(int red, int green, int blue){
        return Math.abs(red - this.color.getRed()) + Math.abs(green - this.color.getGreen()) + Math.abs(blue - this.color.getBlue());
    }

    public Color getColor() {
        return color;
    }

    public int getColorNumber() {
        return colorNumber;
    }
    
    
    
    
}
