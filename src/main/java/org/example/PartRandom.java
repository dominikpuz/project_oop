package org.example;

import com.sun.prism.Texture;

import java.util.Random;

public class PartRandom extends GridObject{
    public PartRandom(int energyGrass, Vector2d position, AbstractWorldMap map, int n, int energyToReproduction, int energyToKid, SimulationEngine engine, Texture textures, int maxMutation, int minMutation, Statisctic statisctic) {
        super(position, map, n, energyToReproduction,energyToKid,engine, textures,maxMutation,minMutation,statisctic);
    }

    @Override
    int[] createRandomGen(int[] genTable, int maxMutation,int minMutation) {
        java.util.Random generator = new java.util.Random();
        int sizeOfRandomGen=generator.nextInt(minMutation-maxMutation)+maxMutation;
        for(int i=0;i<sizeOfRandomGen;i++){
            int changeGen=generator.nextInt(this.n-1);

            int newGen=generator.nextInt(2);
            if(newGen==0){
                if (genTable[changeGen] == 0) {
                    genTable[changeGen] = 7;
                } else {
                    genTable[changeGen] -= 1;
                }
            }
            else{
                genTable[changeGen] = (genTable[changeGen] + 1) % 8;
            }

        }
        return genTable;
    }
}
