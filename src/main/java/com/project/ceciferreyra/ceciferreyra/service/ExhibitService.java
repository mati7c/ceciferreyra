package com.project.ceciferreyra.ceciferreyra.service;

import com.project.ceciferreyra.ceciferreyra.model.Exhibit;
import com.project.ceciferreyra.ceciferreyra.repository.IExhibitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitService implements IExhibitService{
    @Autowired
    private IExhibitRepository exhibitRepository;


    @Override
    public List<Exhibit> getExhibits() {
        return exhibitRepository.findAll();
    }

    @Override
    public Exhibit getExhibitById(Long id) {
        return exhibitRepository.findById(id).orElse(null);
    }

    @Override
    public void saveExhibit(Exhibit exhibit) {
        exhibitRepository.save(exhibit);
    }

    @Override
    public void deleteExhibit(Long id) {
        exhibitRepository.deleteById(id);
    }

    @Override
    public void editExhibit(Exhibit exhibit) {
        this.saveExhibit(exhibit);
    }
}
