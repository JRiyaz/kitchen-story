package com.kitchenstory.service;

import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DishService {

    private final DishRepository repository;

    public List<DishEntity> findAll() {
        return repository.findAll();
    }

    public Optional<DishEntity> findById(String id) {
        return repository.findById(id);
    }

    public Optional<DishEntity> findByName(String name) {
        return repository.findByName(name);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public DishEntity save(DishEntity dish) {
        return repository.save(dish);
    }

    public List<DishEntity> saveAll(List dishes) {
        return repository.saveAll(dishes);
    }

    public void deleteAll(List dishes) {
        repository.deleteAll(dishes);
    }
}
