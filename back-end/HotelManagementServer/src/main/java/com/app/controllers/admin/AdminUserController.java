/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.controllers.admin;

import com.app.dto.request.UserCreateDTO;
import com.app.dto.request.UserSearchCriteria;
import com.app.dto.response.ListUserAdminUserPageDTO;
import com.app.services.UserRoleService;
import com.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;
    

    private void loadBasicUserData(Model model) {
        model.addAttribute("userRoles", this.userRoleService.getUserRoles());
    }

    @GetMapping("/")
    public String createUserView(Model model,
            @ModelAttribute("searchCriteria") UserSearchCriteria criteria) {
        
        ListUserAdminUserPageDTO userResponse = this.userService.getUsers(criteria);
        loadBasicUserData(model);
        model.addAttribute("userResponse", userResponse);
        model.addAttribute("searchCriteria", criteria);
        return "UserPageAdmin";
    }

    @GetMapping("/addOrUpdateUser")
    public String createUserAddOrUpdateView(Model model) {
        loadBasicUserData(model);
        model.addAttribute("userForm", new UserCreateDTO());
        return "UserAddOrUpdatePageAdmin";
    }

    @PostMapping("/addOrUpdateUser/process")
    public String addOrUpdateUserProcess(Model model,
            @Valid @ModelAttribute("userForm") UserCreateDTO userCreateDTO,
            BindingResult bindingResult,
            // Trả về giá trị khi redirect
            RedirectAttributes redirectAttributes
    ) {
        // Validate input
        if (bindingResult.hasErrors()) {
            // Lỗi này không cần dùng model để quăng ra View.
            System.out.println("Chi tiết lỗi: " + bindingResult.getAllErrors());
            loadBasicUserData(model);
            return "UserAddOrUpdatePageAdmin";
        }
        // Xử lý bắt các expection khi thrown ra
        try {
            this.userService.createUser(userCreateDTO);
            redirectAttributes.addFlashAttribute("successMsg", "Thêm phòng thành user: " + userCreateDTO.getName());
            return "redirect:/admin/user/";
        } catch (Exception e) {
            // Cần dùng model để quăng lỗi này ra View thay vì crash server
            model.addAttribute("errorMsg", e.getMessage());
            loadBasicUserData(model);
            return "UserPageAdmin";
        }
    }
}
