package com.project.ceciferreyra.ceciferreyra.controller;

import com.project.ceciferreyra.ceciferreyra.model.Type;
import com.project.ceciferreyra.ceciferreyra.service.CloudinaryService;
import com.project.ceciferreyra.ceciferreyra.service.IPieceService;
import com.project.ceciferreyra.ceciferreyra.model.Piece;
import com.project.ceciferreyra.ceciferreyra.service.PieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("piece/")
public class PieceController {

    private final PieceService pieceService;
    private final CloudinaryService cloudinaryService; // Servicio para subir imágenes

    @Autowired
    public PieceController(PieceService pieceService, CloudinaryService cloudinaryService) {
        this.pieceService = pieceService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePiece(
            @RequestParam("name") String name,
            @RequestParam("year") String year,
            @RequestParam("technique") String technique,
            @RequestParam("typeId") Long typeId,
            @RequestParam("available") boolean available,
            @RequestParam("measures") String measures,
            @RequestParam("images") List<MultipartFile> images) {
        // Subir imágenes a Cloudinary y obtener las URLs
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String imageUrl = null;
            try {
                imageUrl = cloudinaryService.uploadImage(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageUrls.add(imageUrl);
        }

        // Crear el objeto Piece
        Piece piece = new Piece();
        piece.setName(name);
        piece.setYear(year);
        piece.setTechnique(technique);
        piece.setTypeId(typeId);
        piece.setAvailable(available);
        piece.setMeasures(measures);
        piece.setImageUrls(imageUrls);

        // Guardar en la base de datos
        pieceService.savePiece(piece);

        return ResponseEntity.ok("Piece creada correctamente");
    }

    @GetMapping("list")
    public List<Piece> getPieces() {
        return pieceService.getPieces();
    }

    @DeleteMapping("delete/{id}")
    public String deletePieces(@PathVariable Long id) {
        pieceService.deletePiece(id);
        return "Piece eliminado correctamente";
    }

    @PutMapping("edit/{id_original}")
    public Piece editPiece(@PathVariable Long id_original, @RequestBody Piece pieceEdit) {
        pieceService.editPiece(pieceEdit);
        Piece pieceEditado =pieceService.getPieceById(id_original);
        return pieceEditado;
    }

    @GetMapping("list/{id}")
    public Piece getPiece(@PathVariable Long id) {
        return pieceService.getPieceById(id);
    }

}
