package com.app.controllers.admin;

import com.app.configs.SidebarConfig;
import com.app.dto.request.RoomSearchCriteria;
import com.app.dto.response.RoomSearchResponse;
import com.app.dto.response.RoomStatusResponse;
import com.app.dto.response.RoomTypeResponse;
import com.app.services.RoomService;
import com.app.services.RoomStatusService;
import com.app.services.RoomTypeService;
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
public class AdminPageController {

    @Autowired
    private SidebarConfig sidebarConfig;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoomStatusService roomStatusService;

    @ModelAttribute
    public void commonResponse(Model model) {
        List functions = sidebarConfig.getFuction();
        model.addAttribute("sidebarMenu", functions);
    }

    @GetMapping("/")
    public String createHomepageAdmin(Model model) {

        return "HomePageAdmin";
    }

    @GetMapping("/rooms")
    public String createRoomsPageAdmin(Model model, RoomSearchCriteria criteria) {
        RoomSearchResponse roomSearchResponse = new RoomSearchResponse(this.roomService.getRooms(criteria));
        RoomTypeResponse roomTypeResponse = new RoomTypeResponse(this.roomTypeService.getRoomTypes());
        RoomStatusResponse roomStatusResponse = new RoomStatusResponse(this.roomStatusService.getRoomStatus());

        model.addAttribute("roomSearchResponse", roomSearchResponse);
        model.addAttribute("roomTypeResponse", roomTypeResponse);
        model.addAttribute("roomStatusResponse", roomStatusResponse);
        model.addAttribute("searchCriteria", criteria);
                
        return "RoomsPageAdmin";
    }
}
