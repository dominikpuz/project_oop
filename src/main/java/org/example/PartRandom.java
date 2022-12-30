package org.example;

public class PartRandom extends GridObject{
    public PartRandom(int energyGrass, Vector2d position, AbstractWorldMap map, int n, int energyToReproduction,int energyToKid, SimulationEngine engine) {
        super(position, map, n, energyToReproduction,energyToKid,engine);
    }

    @Override
    int[] createRandomGen(int[] genTable) {
        java.util.Random generator = new java.util.Random();
        int sizeOfRandomGen=generator.nextInt(this.n);
        for(int i=0;i<sizeOfRandomGen;i++){
            int changeGen=generator.nextInt(this.n);
            int newGen=generator.nextInt(2);
            if(newGen==0){
                changeGen-=1;
            }
            else{
                changeGen+=1;
            }
            genTable[changeGen]=newGen;

        }
        return genTable;
    }
}
