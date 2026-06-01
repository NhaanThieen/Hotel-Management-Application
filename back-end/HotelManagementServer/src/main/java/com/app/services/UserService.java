
package com.app.services;

import com.app.dto.request.UserCreateDTO;
import com.app.pojo.User;


public interface UserService{
    public User getUserByUsername(String username);
    public User createUser(UserCreateDTO userDTO);
}
