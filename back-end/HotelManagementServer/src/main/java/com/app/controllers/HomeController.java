package com.app.controllers;

import com.app.configs.SidebarConfig;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
    @Autowired
    private SidebarConfig sidebarConfig;
    @RequestMapping("/")
    public String index(Model model){
        List functions = sidebarConfig.getFuction();
        model.addAttribute("sidebarMenu", functions);
        return "index";
    }
}
