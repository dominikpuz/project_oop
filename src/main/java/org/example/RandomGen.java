package org.example;

public class RandomGen extends GridObject{
    public RandomGen(Vector2d position, AbstractWorldMap map, int n, int energyToReproduction, int energyToKid, SimulationEngine engine, Textures textures,int maxMutation,int minMutation,Statisctic statisctic) {
        super(position, map,n,energyToReproduction,energyToKid,engine, textures,maxMutation,minMutation,statisctic);
    }

    @Override
    int[] createRandomGen(int[] genTable,int maxMutation,int minMutation) {
        java.util.Random generator = new java.util.Random();
        int sizeOfRandomGen=generator.nextInt(minMutation, maxMutation);
        for(int i=0;i<sizeOfRandomGen;i++){
            int changeGen=generator.nextInt(this.n);
            int newGen=generator.nextInt(8);
            genTable[changeGen]=newGen;

        }
        return genTable;
    }


}
