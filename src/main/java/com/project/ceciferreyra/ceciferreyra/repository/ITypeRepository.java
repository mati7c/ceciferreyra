package com.project.ceciferreyra.ceciferreyra.repository;

import com.project.ceciferreyra.ceciferreyra.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITypeRepository extends JpaRepository<Type, Long> {
}
