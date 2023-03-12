package org.example;

public class Fish {

    public static String M_GENDER="Erkak";
    public static String F_GENDER="Urg'ochi";

    private String gender;// Erkak , Urg'ochi
    private int lifeSpan;//  Umri
//    private int id;// id
    private String name;

    private int x;
    private int z;
    private int y;

    Fish(int life, String gender, String name, int x,int y,int z) {
        this.lifeSpan = life;
        this.gender = gender;
        this.name = name;
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public String getGender() {
        return gender;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public String getName(){return name;}

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setY(int y) {
        this.y = y;
    }
}
