/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories;

import com.app.dto.request.UserSearchCriteria;
import com.app.pojo.User;
import java.util.List;


public interface UserRepository {
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);
    public User getUserByPhone(String phone);
    public List<User> getUsers(UserSearchCriteria criteria);
    public User createUser(User user);
    public User getUserById(Integer id);
}
