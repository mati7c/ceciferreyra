package com.project.ceciferreyra.ceciferreyra.service;

import com.project.ceciferreyra.ceciferreyra.model.User;

import java.util.List;

public interface IUserService {
    public List<User> getUsers();
    public User getUserById(Long id);
    public void saveUser(User user);
    public void deleteUser(Long id);
    public void editUser(User user);

}
