package org.example;

import java.util.Comparator;

public class MyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return Comparator.comparing(Mage::getLevel)
                .reversed()
                .thenComparing(Mage::getName)
                .reversed()
                .thenComparing(Mage::getPower)
                .reversed()
                .compare((Mage) o1, (Mage) o2);
    }
}
