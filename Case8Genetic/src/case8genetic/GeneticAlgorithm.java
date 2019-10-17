/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.awt.Polygon;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

/**
 *
 * @author David
 */
public class GeneticAlgorithm {
    private ArrayList<PixelArea> objectiveList;
    private ArrayList<CustomColor> customColors;
    private ArrayList<MYPolygon> population;
    private TreeMap<Integer,Integer> genomeMap;
    private int GENOME_NUMBER = 65535;
    private double MUTATION_PERCENTAGE = 0.05;

    public GeneticAlgorithm(ArrayList<PixelArea> objectiveList, ArrayList<CustomColor> customColors) {
        this.objectiveList = objectiveList;
        this.customColors = customColors;
        this.population = new ArrayList<>();
        this.genomeMap = new TreeMap<>();
        setGenomes();
    }
    
    private void setGenomes(){
        int currentGenome = 0;
        int objectiveIndex = 0;
        while (objectiveIndex < objectiveList.size()) {
            PixelArea area = objectiveList.get(objectiveIndex);
            int areaGenomes = (int) (GENOME_NUMBER * area.getPorcentage())/100;
            genomeMap.put(currentGenome, objectiveIndex);
            genomeMap.put((currentGenome +areaGenomes)-1, null);
            currentGenome += areaGenomes;
            objectiveIndex++; 
        }
    }
    
    private int getGenomeArea( int genomeKey){
        Map.Entry<Integer,Integer> mapEntry = genomeMap.floorEntry(genomeKey);
        if(mapEntry != null && mapEntry.getValue() == null){
            mapEntry = genomeMap.lowerEntry(genomeKey);
        }
        if(mapEntry == null){
            return -1;
        }else{
            return mapEntry.getValue();
        }
        
    }
    
    private void createInitialPopulation(int size){
        Random random = new Random();
        while(size > 0){
            int randomNumber = random.nextInt((GENOME_NUMBER-0)+1) + 0;  
            addPolygon(randomNumber);
            size--;
        }
    }
    
    private void addPolygon(int genomeNumber){
        int genomeIndex = getGenomeArea(genomeNumber);
        PixelArea area = objectiveList.get(genomeIndex);
        Random random = new Random();
        int firstX = random.nextInt((area.getMaximumX()-area.getMinimumX())+1) + area.getMinimumX();
        int secondX = random.nextInt((area.getMaximumX()-area.getMinimumX())+1) + area.getMinimumX();
        int firstY = random.nextInt((area.getMaximumY()-area.getMinimumY())+1) + area.getMinimumY();
        int secondY = random.nextInt((area.getMaximumY()-area.getMinimumY())+1) + area.getMinimumY();
        MYPolygon polygon = new MYPolygon(firstX, firstY, secondX, secondY, area.getColorGroupNumber(), genomeNumber);
        population.add(polygon);
    }
    
    private MYPolygon createPolygon(int genomeNumber){
        int genomeIndex = getGenomeArea(genomeNumber);
        PixelArea area = objectiveList.get(genomeIndex);
        Random random = new Random();
        int firstX = random.nextInt((area.getMaximumX()-area.getMinimumX())+1) + area.getMinimumX();
        int secondX = random.nextInt((area.getMaximumX()-area.getMinimumX())+1) + area.getMinimumX();
        int firstY = random.nextInt((area.getMaximumY()-area.getMinimumY())+1) + area.getMinimumY();
        int secondY = random.nextInt((area.getMaximumY()-area.getMinimumY())+1) + area.getMinimumY();
        MYPolygon polygon = new MYPolygon(firstX, firstY, secondX, secondY, area.getColorGroupNumber(), genomeNumber);
        return polygon;
    }
    
    public void runAlgorithm(int size, String fileName){
        createInitialPopulation(20);
            DOMImplementation domi = GenericDOMImplementation.getDOMImplementation();
            String svgNS = "http://www.w3.org/2000/svg";
            Document document = domi.createDocument(svgNS, "svg", null);
            SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
            
            svgGenerator.setSVGCanvasSize(new Dimension(1024, 1024));
            int fileVersion = 1;
            String file_Name= fileName+fileVersion + ".svg";
           drawPolygons(svgGenerator, file_Name);
        while(population.size() < size){
            ArrayList<MYPolygon> newPopulation = new ArrayList<>();
        for(int actualPolygon = 0; actualPolygon < population.size(); actualPolygon++){
            if (fitnessFunction(population.get(actualPolygon))) {
                newPopulation.add(population.get(actualPolygon));
            }
        }
        mixPolygons(newPopulation);
        fileVersion = fileVersion +1;
            drawPolygons(svgGenerator, fileName+fileVersion + ".svg");
        }
        
    }
    
    private Color getColor(int colorGroup){
        Color retColor = null;
        for(CustomColor color : customColors){
            if(color.getColorNumber() == colorGroup){
                retColor = color.getColor();
                break;
            }
        }
        return retColor;
    }
    
    private void drawPolygons(SVGGraphics2D graphics2D,String fileName){
        for(MYPolygon polygon : population){
            graphics2D.setColor(getColor(polygon.getColorGroupNumber()));
            graphics2D.drawPolygon(new Polygon(new int[]{polygon.getFirstX(),polygon.getSecondX(),polygon.getFirstX(),polygon.getSecondX()},
                                                new int[]{polygon.getFirstY(),polygon.getFirstY(),polygon.getSecondY(),polygon.getSecondY()},4));
        }
        boolean useCSS = true;
        
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
            graphics2D.stream(out,useCSS);
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SVGGraphics2DIOException ex) {
            Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private boolean fitnessFunction(MYPolygon polygon){
        double totalTypePercentage = getTotalTypePercentage(polygon);
        double TypePercentageWithoutPolygon = getTypePercentage(polygon);
        double typeObjectivePercentage = objectiveList.get(getGenomeArea(polygon.getGenomeNumber())).getPorcentage();
        double percentageValue = (TypePercentageWithoutPolygon/totalTypePercentage)* typeObjectivePercentage;
        if(percentageValue > 0.95*typeObjectivePercentage){
            return  true;
        }
        else{
            return false;
        }
    }
    
    private void mixPolygons(ArrayList<MYPolygon> polygons){
        ArrayList<MYPolygon> finalPopulation = new ArrayList<>();
        Random random = new Random();
        while(polygons.size() >= 2){
            MYPolygon firstParent = polygons.remove(random.nextInt((polygons.size()-0))+0);
            MYPolygon secondParent = polygons.remove(random.nextInt((polygons.size()-0))+0);
            int childGenome = mixGenomes(firstParent.getGenomeNumber(), secondParent.getGenomeNumber());
            finalPopulation.add(createPolygon(childGenome));
            finalPopulation.add(firstParent);
            finalPopulation.add(secondParent);
        }
        if(polygons.size() == 1){
            finalPopulation.add(polygons.remove(0));
        }
        population = finalPopulation;
        
    }
    
    private int mixGenomes(int firstGenome, int secondGenome){
        String firstGenomeBits = Integer.toBinaryString(firstGenome);
        String secondGenomeBits = Integer.toBinaryString(secondGenome);
        Random random = new Random();
        int point = random.nextInt((16));
        String newGenome = firstGenomeBits.substring(0,point-1) + secondGenomeBits.substring(point);
        if(random.nextDouble() <= MUTATION_PERCENTAGE){
            int bitLocation = random.nextInt(16);
            if(newGenome.charAt(bitLocation) == '1' ){
                newGenome = newGenome.substring(0, bitLocation-1) + "0" + newGenome.substring(bitLocation+1);
            }
        }
        int childGenome = Integer.valueOf(newGenome, 2);
        return  childGenome;
    }
    
    private double getTotalTypePercentage(MYPolygon polygon){
        int polygonTypeNumber = 0;
        for(MYPolygon p : population){
            if(getGenomeArea(p.getGenomeNumber()) == getGenomeArea(polygon.getGenomeNumber())){
                polygonTypeNumber++;
            }
        }
        double percentage = (polygonTypeNumber*100.0)/population.size();
        return  percentage;
    }
    
        private double getTypePercentage(MYPolygon polygon){
        int polygonTypeNumber = 0;
        for(MYPolygon p : population){
            if (!p.equals(polygon)) {
                 if(getGenomeArea(p.getGenomeNumber()) == getGenomeArea(polygon.getGenomeNumber())){
                polygonTypeNumber++;
            }
            }
        }
        double percentage = (polygonTypeNumber*100.0)/population.size();
        return  percentage;
    }
    
}
