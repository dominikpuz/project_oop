package org.example;

import java.util.ArrayList;
import java.util.List;

public class GridObject {
    protected List<Animal> AnimalsOnGrid = new ArrayList<Animal>();
    protected boolean isGrass;
    protected int energyGrass;

    public void addAnimal(Animal animalToAdd) {
        AnimalsOnGrid.add(animalToAdd);
    }

    public void deadAnimal() {
        for (Animal x : AnimalsOnGrid) {
            if (x.getEnergy() <= 0) {
                AnimalsOnGrid.remove(x);
                /**
                 * tzreba tutja tez usuwac z observera zwierze

                 */

            }

        }
    }

    public Animal bestAnimal(ArrayList<Animal> tmp) {
        Animal maxAnimal = null;
        int maxEnergy = 0;
        int maxDays = 0;
        int maxKids = 0;
        for (Animal y : tmp) {
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    if (((Animal) y).getEnergy() > maxEnergy) {
                        maxAnimal = (Animal) y;
                        maxEnergy = ((Animal) y).getEnergy();
                        maxDays = ((Animal) y).getDays();
                        maxKids = ((Animal) y).getKids();
                        break;
                    }
                    if (((Animal) y).getEnergy() < maxEnergy) {
                        break;
                    }
                }
                if (i == 1) {
                    if (((Animal) y).getDays() > maxDays) {
                        maxAnimal = (Animal) y;
                        maxEnergy = ((Animal) y).getEnergy();
                        maxDays = ((Animal) y).getDays();
                        maxKids = ((Animal) y).getKids();
                        break;
                    }
                    if (((Animal) y).getDays() < maxDays) {
                        break;
                    }


                }
                if (i == 2) {
                    if (((Animal) y).getKids() > maxKids) {
                        maxAnimal = (Animal) y;
                        maxEnergy = ((Animal) y).getEnergy();
                        maxDays = ((Animal) y).getDays();
                        maxKids = ((Animal) y).getKids();
                        break;
                    }
                    if (((Animal) y).getKids() < maxKids) {
                        break;
                    }

                }

            }

        }
        return maxAnimal;
    }
    public void feedAnimal () {
            if (isGrass) {
               bestAnimal((ArrayList<Animal>) AnimalsOnGrid).addEnergy(energyGrass);
            }
    }
    public void Reproduction (){
        ArrayList<Animal> tmp = new ArrayList<>(AnimalsOnGrid);
        Animal animal1=bestAnimal(tmp);
        tmp.remove(animal1);
        Animal animal2=bestAnimal(tmp);
        tmp.remove(animal2);



    }




}
