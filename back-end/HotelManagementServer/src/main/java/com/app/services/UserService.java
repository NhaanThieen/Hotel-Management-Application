
package com.app.services;

import com.app.dto.request.ApiRegisterDTO;
import com.app.dto.request.UserCreateDTO;
import com.app.dto.request.UserSearchCriteria;
import com.app.dto.response.ListUserAdminUserPageDTO;
import com.app.pojo.User;


public interface UserService{
    public User getUserByUsername(String username);
    public User createUser(UserCreateDTO userDTO);
    public User createUserForClient(ApiRegisterDTO registerDTO);
    public ListUserAdminUserPageDTO getUsers(UserSearchCriteria criteria);
}
