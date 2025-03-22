package com.project.ceciferreyra.ceciferreyra.service;

import com.project.ceciferreyra.ceciferreyra.model.Piece;

import java.util.List;

public interface IPieceService {
    public List<Piece> getPieces();
    public Piece getPieceById(Long id);
    public void savePiece(Piece piece);
    public void deletePiece(Long id);
    public void editPiece(Piece piece);
}
