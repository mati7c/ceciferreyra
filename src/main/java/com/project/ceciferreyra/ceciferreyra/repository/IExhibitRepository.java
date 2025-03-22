package com.project.ceciferreyra.ceciferreyra.repository;

import com.project.ceciferreyra.ceciferreyra.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExhibitRepository extends JpaRepository<Exhibit, Long> {
}
