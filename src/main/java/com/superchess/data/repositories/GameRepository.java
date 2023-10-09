package com.superchess.data.repositories;

import com.superchess.data.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    public Game findFirstByPlayer2IsNull();

    List<Game> findAllByPlayer2IsNull();
}
