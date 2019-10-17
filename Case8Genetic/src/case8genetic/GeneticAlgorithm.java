/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package case8genetic;

import java.util.ArrayList;
import java.util.Map;
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
            int areaGenomes = (int) (GENOME_NUMBER * area.getPorcentage());
            genomeMap.put(currentGenome, objectiveIndex);
            genomeMap.put(areaGenomes-1, null);
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
    
}
