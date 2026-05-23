
package com.app.services.servicesImpl;

import com.app.pojo.User;
import com.app.repositories.UserRepository;
import com.app.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;
    
    @Override
    public List<User> getUser() {
        return this.userRepo.getUsers();
    }
}
