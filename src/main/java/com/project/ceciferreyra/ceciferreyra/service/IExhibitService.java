package com.project.ceciferreyra.ceciferreyra.service;

import com.project.ceciferreyra.ceciferreyra.model.Exhibit;

import java.util.List;

public interface IExhibitService {
    public List<Exhibit> getExhibits();
    public Exhibit getExhibitById(Long id);
    public void saveExhibit(Exhibit exhibit);
    public void deleteExhibit(Long id);
    public void editExhibit(Exhibit exhibit);
}
