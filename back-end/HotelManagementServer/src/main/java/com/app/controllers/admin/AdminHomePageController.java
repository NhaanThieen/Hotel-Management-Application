package com.app.controllers.admin;

import com.app.configs.SidebarConfig;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice(basePackages = "com.app.controllers.admin")
@RequestMapping("/admin")
public class AdminHomePageController {

    @Autowired
    private SidebarConfig sidebarConfig;

    @ModelAttribute
    public void commonResponse(Model model) {
        List functions = sidebarConfig.getFuction();
        model.addAttribute("sidebarMenu", functions);
    }

    @GetMapping("/")
    public String createHomepageAdmin(Model model) {
        return "HomePageAdmin";
    }

}
