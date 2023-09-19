package org.example;

import lombok.Data;

import java.util.*;

@Data
public class MageRepository {
    private Collection<Mage> collection;

    public MageRepository() {
        this.collection = new ArrayList<>();
    }

    public Optional<Mage> find(String name) {
        Mage returnMage = null;
        for(Mage mage: this.collection){
            if(mage.getName().equals(name)) {
                returnMage = mage;
                break;
            }
        }
        return Optional.ofNullable(returnMage);
    }
    public void delete(String name) {
        Mage mageToDelete = null;
        for(Mage mage: this.collection) {
            if(mage.getName().equals(name)) {
                mageToDelete = mage;
                break;
            }
        }
        if(mageToDelete == null) {
            throw new IllegalArgumentException();
        } else {
            this.collection.remove(mageToDelete);
        }
    }
    public void save(Mage mage) {
        for(Mage mageToCheck: this.collection) {
            if(mage.getName().equals(mageToCheck.getName())) {
                throw new IllegalArgumentException();
            }
        }
        this.collection.add(mage);
    }
}
