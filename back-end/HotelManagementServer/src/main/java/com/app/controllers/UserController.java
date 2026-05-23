
package com.app.controllers;

import com.app.pojo.User;
import com.app.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class UserController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping("/cates")
    public String getCates(Model model){
        
        List<User> users = userService.getUser();
        
        model.addAttribute("users", users);
        return "index";
    }
    
    
}
