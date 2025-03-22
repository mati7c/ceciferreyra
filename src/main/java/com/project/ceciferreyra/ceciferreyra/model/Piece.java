package com.project.ceciferreyra.ceciferreyra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String year;
    private String technique;
    private Long typeId;
    private boolean available;
    private String measures;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> imageUrls;


}

