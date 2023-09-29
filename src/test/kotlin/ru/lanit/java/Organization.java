package ru.lanit.java;

public class Organization {

    public static void main(String[] args) {

        final Depart depart = new Depart("Интеграция");

        final Employee employee = new Employee("Alex");

        depart.setBoss(employee);

    }
}
