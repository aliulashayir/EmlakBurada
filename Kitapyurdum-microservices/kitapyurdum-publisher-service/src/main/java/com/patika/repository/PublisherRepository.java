package com.patika.repository;

import com.patika.model.Publisher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PublisherRepository {
    private List<Publisher> publishers = new ArrayList<>();
    private int currentId = 1;

    public void save(Publisher publisher) {
        publisher.setId(currentId++);
        publishers.add(publisher);
    }

    public List<Publisher> findAll() {
        return publishers;
    }

    public Optional<Publisher> findByName(String name) {
        return publishers.stream().filter(publisher -> publisher.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Publisher> findById(int id) {
        return publishers.stream().filter(publisher -> publisher.getId() == id).findFirst();
    }
}
