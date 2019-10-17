/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author David
 */
public class GeneticAlgorithm {
    private ArrayList<PixelArea> objectiveList;
    private ArrayList<CustomColor> customColors;
    private ArrayList<Polygon> population;
    private TreeMap<Integer,Integer> genomeMap;
    private int GENOME_NUMBER = 65535;

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
        Polygon polygon = new Polygon(firstX, firstY, secondX, secondY, area.getColorGroupNumber(), genomeNumber);
        population.add(polygon);
    }
    
    public void runAlgorithm(){
        createInitialPopulation(20);
    }
    
}
