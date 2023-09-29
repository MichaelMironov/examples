package ru.lanit.java;

public class Depart {
    private final String name;
    private Employee boss;


    public void setBoss(final Employee boss) {
        this.boss = boss;
        boss.setDepart(this);
    }

    public Depart(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Employee getBoss() {
        return boss;
    }
}
