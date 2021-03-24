package com.kitchenstory.repository;

import com.kitchenstory.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, String> {
    Optional<DishEntity> findByName(String name);

    List<DishEntity> findByNameContainingIgnoreCase(String name);
}
