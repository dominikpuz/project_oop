package org.example;

import java.util.Random;

public class PartRandom extends GridObject{
    public PartRandom(int energyGrass, Vector2d position, AbstractWorldMap map, int n, int energyToReproduction,int energyToKid, SimulationEngine engine, Textures textures) {
        super(position, map, n, energyToReproduction,energyToKid,engine, textures);
    }

    @Override
    int[] createRandomGen(int[] genTable) {
        Random generator = new Random();
        int sizeOfRandomGen=generator.nextInt(this.n);
        for(int i=0;i<sizeOfRandomGen;i++){
            int changeGen=generator.nextInt(this.n);
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
//            genTable[changeGen]=newGen;

        }
        return genTable;
    }
}
