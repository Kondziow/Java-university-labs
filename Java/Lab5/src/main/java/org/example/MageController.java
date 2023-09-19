package org.example;

import java.util.Optional;

public class MageController {
    private MageRepository repository;
    public MageController(MageRepository repository) {
        this.repository = repository;
    }
    public String find(String name) {
        Optional<Mage> value = this.repository.find(name);
        if(value.isEmpty())
            return "not found";
        else
            return value.get().toString();
    }
    public String delete(String name) {
        try {
            this.repository.delete(name);
        } catch (IllegalArgumentException e) {
            return "not found";
        }
        return "done";
    }
    public String save(String name, String level) {
        try {
            this.repository.save(new Mage(name, Integer.parseInt(level)));
        } catch (IllegalArgumentException e) {
            return "bad request";
        }
        return "done";
    }
}
