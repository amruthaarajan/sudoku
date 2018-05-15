package com.agilysys.repositories;

/**
 * Created by amruthaa on 5/14/18.
 */
import com.agilysys.models.Board;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardRepository extends CrudRepository<Board, String> {
    @Override
    Optional<Board> findById(String id);

    @Override
    void delete(Board deleted);
}