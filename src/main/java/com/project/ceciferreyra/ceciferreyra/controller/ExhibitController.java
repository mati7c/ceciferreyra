package com.project.ceciferreyra.ceciferreyra.controller;

import com.project.ceciferreyra.ceciferreyra.model.Exhibit;
import com.project.ceciferreyra.ceciferreyra.service.CloudinaryService;
import com.project.ceciferreyra.ceciferreyra.service.ExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("exhibit/")
public class ExhibitController {
    private final ExhibitService exhibitService;
    private final CloudinaryService cloudinaryService; // Servicio para subir imágenes

    @Autowired
    public ExhibitController(ExhibitService exhibitService, CloudinaryService cloudinaryService) {
        this.exhibitService = exhibitService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveExhibit(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description) {
        // Subir imágenes a Cloudinary y obtener las URLs

            String imageUrl = null;
            try {
                imageUrl = cloudinaryService.uploadImage(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        Exhibit exhibit = new Exhibit();
            exhibit.setName(name);
        exhibit.setImageUrl(imageUrl);
        exhibit.setDescription(description);

        // Guardar en la base de datos
        exhibitService.saveExhibit(exhibit);

        return ResponseEntity.ok("Muestra creada correctamente creada correctamente");
    }

    @GetMapping("list")
    public List<Exhibit> getExhibits() {
        return exhibitService.getExhibits();
    }

    @DeleteMapping("delete/{id}")
    public String deleteExhibit(@PathVariable Long id) {
        exhibitService.deleteExhibit(id);
        return "Muestra eliminado correctamente";
    }
}
