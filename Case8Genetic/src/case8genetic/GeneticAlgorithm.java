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
        Polygon polygon = new Polygon(firstX, firstY, secondX, secondY, area.getColorGroupNumber(), genomeNumber);
        population.add(polygon);
    }
    
    private Polygon createPolygon(int genomeNumber){
        int genomeIndex = getGenomeArea(genomeNumber);
        PixelArea area = objectiveList.get(genomeIndex);
        Random random = new Random();
        int firstX = random.nextInt((area.getMaximumX()-area.getMinimumX())+1) + area.getMinimumX();
        int secondX = random.nextInt((area.getMaximumX()-area.getMinimumX())+1) + area.getMinimumX();
        int firstY = random.nextInt((area.getMaximumY()-area.getMinimumY())+1) + area.getMinimumY();
        int secondY = random.nextInt((area.getMaximumY()-area.getMinimumY())+1) + area.getMinimumY();
        Polygon polygon = new Polygon(firstX, firstY, secondX, secondY, area.getColorGroupNumber(), genomeNumber);
        return polygon;
    }
    
    public void runAlgorithm(){
        createInitialPopulation(20);
        ArrayList<Polygon> newPopulation = new ArrayList<>();
        for(int actualPolygon = 0; actualPolygon < population.size(); actualPolygon++){
            if (fitnessFunction(population.get(actualPolygon))) {
                newPopulation.add(population.get(actualPolygon));
            }
        }
        mixPolygons(newPopulation);
    }
    
    private boolean fitnessFunction(Polygon polygon){
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
    
    private void mixPolygons(ArrayList<Polygon> polygons){
        ArrayList<Polygon> finalPopulation = new ArrayList<>();
        Random random = new Random();
        while(polygons.size() >= 2){
            Polygon firstParent = polygons.remove(random.nextInt((polygons.size()-0))+0);
            Polygon secondParent = polygons.remove(random.nextInt((polygons.size()-0))+0);
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
    
    private double getTotalTypePercentage(Polygon polygon){
        int polygonTypeNumber = 0;
        for(Polygon p : population){
            if(getGenomeArea(p.getGenomeNumber()) == getGenomeArea(polygon.getGenomeNumber())){
                polygonTypeNumber++;
            }
        }
        double percentage = (polygonTypeNumber*100.0)/population.size();
        return  percentage;
    }
    
        private double getTypePercentage(Polygon polygon){
        int polygonTypeNumber = 0;
        for(Polygon p : population){
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
