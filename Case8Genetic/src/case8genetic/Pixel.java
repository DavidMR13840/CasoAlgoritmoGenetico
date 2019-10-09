/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

/**
 *
 * @author David
 */
public class Pixel {
    private int xValue;
    private int yValue;
    private int redValue;
    private int blueValue;
    private int greenValue;
    private int colorNumber;

    public Pixel(int xValue, int yValue, int redValue, int blueValue, int greenValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.redValue = redValue;
        this.blueValue = blueValue;
        this.greenValue = greenValue;
    }

    public boolean equals(Pixel pixel) {
        return (this.xValue == pixel.xValue) && (this.yValue == pixel.yValue);
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }

    public int getRedValue() {
        return redValue;
    }

    public int getBlueValue() {
        return blueValue;
    }

    public int getGreenValue() {
        return greenValue;
    }
    
    
    
    

    
    
}
