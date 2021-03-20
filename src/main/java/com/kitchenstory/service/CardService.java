package com.kitchenstory.service;

import com.kitchenstory.entity.CardEntity;
import com.kitchenstory.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CardService {

    private final CardRepository repository;

    public CardEntity save(CardEntity card) {
        return repository.save(card);
    }

    public Optional<CardEntity> findById(Integer id) {
        return repository.findById(id);
    }

    public List<CardEntity> findAll() {
        return repository.findAll();
    }
}
