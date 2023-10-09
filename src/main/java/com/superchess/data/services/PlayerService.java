package com.superchess.data.services;

import com.superchess.data.entities.Player;
import com.superchess.data.repositories.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Optional<Player> get(Long id) {
        return repository.findById(id);
    }

    public Player update(Player entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Player> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Player> list(Pageable pageable, Specification<Player> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public Player findByUsername(String username) {
        return repository.findByUsername(username);
    }
}