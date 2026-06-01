/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.controllers.admin;

import com.app.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    
    @Autowired
    private UserRoleService userRoleService;
    
    private void loadBasicInfor(Model model){
        model.addAttribute("userRoles", this.userRoleService.getUserRoles());
    }
    
    @GetMapping("/")
    public String createUserView(Model model){
        loadBasicInfor(model);
        return "UserPageAdmin";
    }
    
    @GetMapping("/addOrUpdateUser")
    public String createUserAddOrUpdateView(Model model){
        loadBasicInfor(model);
        return "UserAddOrUpdatePageAdmin";
    }
}
