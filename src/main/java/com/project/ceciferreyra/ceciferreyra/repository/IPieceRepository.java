package com.project.ceciferreyra.ceciferreyra.repository;

import com.project.ceciferreyra.ceciferreyra.model.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPieceRepository extends JpaRepository<Piece, Long> {
}
