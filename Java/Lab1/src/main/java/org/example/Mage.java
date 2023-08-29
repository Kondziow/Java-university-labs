package org.example;

import java.util.*;

public class Mage implements Comparable {
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public Mage(String name, int level, double power, String sort) {
        this.name = name;
        this.level = level;
        this.power = power;
        if (sort == null)
            this.apprentices = new HashSet<>();
        else if (sort.equalsIgnoreCase("natural"))
            this.apprentices = new TreeSet<>();
        else if (sort.equalsIgnoreCase("alternative"))
            this.apprentices = new TreeSet<>(new MyComparator());
        else
            this.apprentices = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || o.hashCode() != this.hashCode()) return false;
        Mage mage = (Mage) o;
        return level == mage.level && Double.compare(mage.power, power) == 0 && Objects.equals(name, mage.name) && Objects.equals(apprentices, mage.apprentices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, power, apprentices);
    }

    @Override
    public int compareTo(Object o) {
        return Comparator.comparing(Mage::getName)
                .reversed()
                .thenComparing(Mage::getLevel)
                .thenComparing(Mage::getPower)
                .reversed()
                .compare(this, (Mage) o);
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", power=" + power +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Set<Mage> getApprentices() {
        return apprentices;
    }

    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices = apprentices;
    }
}

