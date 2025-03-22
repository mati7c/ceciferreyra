package com.project.ceciferreyra.ceciferreyra.service;

import com.project.ceciferreyra.ceciferreyra.model.Type;
import com.project.ceciferreyra.ceciferreyra.repository.ITypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService implements ITypeService{
    @Autowired
    private ITypeRepository typeRepository;

    @Override
    public void saveType(Type type) {
        typeRepository.save(type);
    }
}
