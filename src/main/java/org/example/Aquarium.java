package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Aquarium {
    private final List<String> fFishNames = new CopyOnWriteArrayList<>(Arrays.asList("Gloria", "Marie", "Lele", "Elisa", "Linda", "Lisa"));
    private final List<String> mFishNames = new CopyOnWriteArrayList<>(Arrays.asList("Nemo", "Lolo", "Pepe", "Maykl", "Jordan", "Panda", "Messi"));
    private List<Fish> fishes = new CopyOnWriteArrayList<>();
    private final Random random = new Random();
    private final int maxAge = 10;
    private final int AQUARIUM_Z = 10;
    private final int AQUARIUM_X = 8;
    private final int AQUARIUM_Y = 7;
    private final int minAge = 5;
    private int mFishCount = 1;
    private int fFishCount = 1;
    private int maxCount;

    private void addFish(Fish fish) {
        getList().add(fish);
    }

    private int getListSize() {
        return getList().size();
    }

    private List<Fish> getList() {
        return fishes;
    }

    public void run() throws InterruptedException {
        maxCount = random.nextInt(4, 10);
        addFish(new Fish(random.nextInt(minAge, maxAge), Fish.M_GENDER, mFishNames.get(0), random.nextInt(AQUARIUM_X), random.nextInt(AQUARIUM_Y), random.nextInt(AQUARIUM_Z)));
        addFish(new Fish(random.nextInt(minAge, maxAge), Fish.F_GENDER, fFishNames.get(0), random.nextInt(AQUARIUM_X), random.nextInt(AQUARIUM_Y), random.nextInt(AQUARIUM_Z)));

        boolean check = maxCount > getListSize();

        System.out.printf("Erkak baliqlar soni: %d\nUrg'ochi baliqlar soni: %d\nAkavarium sig'imi: %d\n", mFishCount, fFishCount, maxCount);
        System.out.printf("Akvarium olchami (%d, %d, %d).%n\n",AQUARIUM_X,AQUARIUM_Y,AQUARIUM_Z);

        while (check && getListSize() != 0) {

            System.out.println("Akvariumda:");
            for (Fish fish : getList()) {
                System.out.printf("%s isimli %s umri %d s va joylashuvi (%d, %d, %d).%n", fish.getName(), fish.getGender(), fish.getLifeSpan(),fish.getX(),fish.getY(),fish.getZ());
            }
            System.out.println("bo'lgan baliqlar bor\n");

            for (Fish fish : getList()) {
                if (getListSize() >= maxCount) break;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                new Thread(() -> {
                    try {
                        Thread.sleep(fish.getLifeSpan() * 1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (fishDrive(fish)) {
                        for (int i = 0; i < random.nextInt(1, 4); i++) {
                            Fish mate;
                            if ((mate = findMate(fish.getGender())) != null) {
                                if (random.nextBoolean()) {
                                    Fish nFish = new Fish(
                                            random.nextInt(minAge, maxAge), Fish.M_GENDER, mFishNames.get(random.nextInt(1, mFishNames.size())), random.nextInt(AQUARIUM_X), random.nextInt(AQUARIUM_Y), random.nextInt(AQUARIUM_Z)
                                    );
                                    getList().add(nFish);
                                    System.out.println(
                                            mate.getName() + " va " + fish.getName() + " uchrashdi va " + nFish.getName() + " isimli " + nFish.getGender() + " umri " + nFish.getLifeSpan() + " bolgan baliq tug'uldi"
                                    );
                                    mFishCount++;
                                } else {
                                    Fish nFish = new Fish(
                                            random.nextInt(minAge, maxAge), Fish.F_GENDER, fFishNames.get(random.nextInt(1, fFishNames.size())), random.nextInt(AQUARIUM_X), random.nextInt(AQUARIUM_Y), random.nextInt(AQUARIUM_Z)
                                    );
                                    getList().add(nFish);
                                    System.out.println(
                                            mate.getName() + " va " + fish.getName() + " uchrashdi va " + nFish.getName() + " isimli " + nFish.getGender() + " umri " + nFish.getLifeSpan() + " s bolgan baliq tug'uldi"
                                    );
                                    fFishCount++;
                                }
                            }
                        }
                    }
                    getList().remove(fish);
                    System.out.println(fish.getName() + " isimli " + fish.getGender() + " baliq o'ldi");
                    if (fish.getGender().equals("Erkak")) mFishCount--;
                    else fFishCount--;


                }).run();


            }
            check = maxCount > getListSize();

        }

        Thread.sleep(10000);
        if (getListSize() != 0) System.out.println("Akvarium to'ldi");
        else System.out.println("Baliqlar qolmadi");

    }

    Fish findMate(String gender) {
        for (Fish fish : getList()) {
            if (!fish.getGender().equals(gender)) return fish;
        }
        return null;
    }

    private boolean fishDrive(Fish fish) {
        int x, y, z;
        x = random.nextInt(10);
        y = random.nextInt(10);
        z = random.nextInt(10);
        System.out.printf("%s isimli baliq ->%n",fish.getName());
        if (fish.getX() + x > AQUARIUM_X) {
            if (((fish.getX() + x) / AQUARIUM_X) % 2 != 0) {
                fish.setX(AQUARIUM_X - ((fish.getX() + x) % AQUARIUM_X));
                System.out.printf("X oqi boyicha %d birlik masofa harakatlandi va hozirgi joylashuvi %d. %n", x, fish.getX());
            } else {
                fish.setX((fish.getX() + x) % AQUARIUM_X);
                System.out.printf("X oqi boyicha %d birlik masofa harakatlandi va hozirgi joylashuvi %d. %n", x, fish.getX());
            }
        } else {
            System.out.printf("X oqi boyicha %d birlik masofa harakatlandi va hozirgi joylashuvi %d.%n", x, x + fish.getX());
            fish.setX(fish.getX() + x);
        }

        if (fish.getY() + y > AQUARIUM_Y) {
            if (((fish.getY() + y) / AQUARIUM_Y) % 2 != 0) {
                fish.setY(AQUARIUM_Y - ((fish.getY() + y) % AQUARIUM_Y));
                System.out.printf("Y oqi boyicha %d birlik masofa harakatlandi va hozirgi joylashuvi %d.%n",  y, fish.getY());
            } else {
                fish.setY((fish.getY() + y) % AQUARIUM_Y);
                System.out.printf("Y oqi boyicha %d birlik masofa harakatlandi va hozirgi joylashuvi %d.%n", y, fish.getY());
            }
        } else {
            System.out.printf("Y oqi boyicha %d birlik masofa harakatlandi va hozirgi joylashuvi %d.%n", y, y + fish.getY());
            fish.setY(fish.getY() + y);
        }

        if (z + fish.getZ() > AQUARIUM_Z) {
            System.out.printf("Z oqi boyicha %d birlik masofaga kotarildi va akvariumdan chiqib ketti.%n", z);
            return false;
        } else {
            fish.setZ(z + fish.getZ());
            System.out.printf("Z oqi boyicha %d masofaga haraktlandi va hozirgi joylashuvi %d.%n", z,fish.getZ());
            return true;
        }
    }


}
