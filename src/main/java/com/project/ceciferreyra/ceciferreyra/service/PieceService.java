package com.project.ceciferreyra.ceciferreyra.service;

import com.project.ceciferreyra.ceciferreyra.model.Piece;
import com.project.ceciferreyra.ceciferreyra.repository.IPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PieceService implements IPieceService{

    @Autowired
    private IPieceRepository pieceRepository;

    @Override
    public List<Piece> getPieces() {
        return pieceRepository.findAll();
    }

    @Override
    public Piece getPieceById(Long id) {
        return pieceRepository.findById(id).orElse(null);
    }

    @Override
    public void savePiece(Piece piece) {


        pieceRepository.save(piece);
    }

    @Override
    public void deletePiece(Long id) {
        pieceRepository.deleteById(id);
    }

    @Override
    public void editPiece(Piece piece) {
        this.savePiece(piece);
    }
}
