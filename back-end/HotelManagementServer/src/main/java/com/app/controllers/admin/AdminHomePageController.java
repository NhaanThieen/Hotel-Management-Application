package com.app.controllers.admin;

import com.app.dto.response.ListRoomReceptDashboarDTO;
import com.app.properties.SidebarGroupProperties;
import com.app.services.RoomService;
import com.app.services.servicesImpl.SidebarService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private SidebarService sidebarService;
    
    

    @ModelAttribute
    public void commonResponse(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = "guest";
        if (auth != null && auth.isAuthenticated()) {
            for (GrantedAuthority ga : auth.getAuthorities()) {
                role = ga.getAuthority().replace("ROLE_", "").toLowerCase();
                break;
            }
        }
        List<SidebarGroupProperties> functions = this.sidebarService.getSidebarByRole(role);
        System.out.println("DEBUG SỐ LƯỢNG GROUP MENU: " + (functions != null ? functions.size() : "NULL"));
        model.addAttribute("sidebarMenu", functions);
    }

    @GetMapping("/")
    public String createHomepageAdmin(Model model) {
        return "HomePageAdmin";
    }
    
    

}
