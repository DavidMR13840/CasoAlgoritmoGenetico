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
    private int minRed;
    private int maxRed;
    private int minGreen;
    private int maxGreen;
    private int minBlue;
    private int maxBlue;
    private int colorNumber;
    private String name;
    private Color color;

    public CustomColor(int minRed, int maxRed, int minGreen, int maxGreen, int minBlue, int maxBlue, Color color, int number, String name) {
        this.minRed = minRed;
        this.maxRed = maxRed;
        this.minGreen = minGreen;
        this.maxGreen = maxGreen;
        this.minBlue = minBlue;
        this.maxBlue = maxBlue;
        this.color = color;
        this.name = name;
        this.colorNumber = number;
    }
    
    
    
}
