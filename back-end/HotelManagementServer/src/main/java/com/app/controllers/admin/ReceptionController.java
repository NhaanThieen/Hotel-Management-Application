package com.app.controllers.admin;

import com.app.pojo.Room;
import com.app.services.RoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/recept") 
public class ReceptionController {

    @Autowired
    private RoomService roomService;

    
    @GetMapping({"/dashboard"}) 
    public String showRoomsForReceptionist(Model model) {
        
        List<Room> rooms = roomService.getRoomsForReceptionist();
        
        model.addAttribute("rooms", rooms);
        
        return "ReceptionistRoomsPage"; 
    }
}
