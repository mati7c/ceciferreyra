package com.project.ceciferreyra.ceciferreyra.controller;

import com.project.ceciferreyra.ceciferreyra.model.Type;
import com.project.ceciferreyra.ceciferreyra.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("type/")
public class TypeController {
    @Autowired
    private ITypeService typeService;

    @PostMapping("save")
    public String saveType(@RequestBody Type type) {typeService.saveType(type);
        return "Type creado correctamente";
    }
}
