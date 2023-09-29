package ru.lanit.java;

public class Employee {
    private final String name;
    private Depart depart;

    public Employee(final String name) {
        this.name = name;
    }

    public void setDepart(final Depart depart) {
        this.depart = depart;
    }

    public String getName() {
        return name;
    }

    public Depart getDepart() {
        return depart;
    }


    @Override
    public String toString() {
        return "Employee{" +
               "name='" + name + '\'' +
               ", depart=" + depart +
               '}';
    }

}



