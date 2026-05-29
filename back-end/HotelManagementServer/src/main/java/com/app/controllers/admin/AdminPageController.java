package com.app.controllers.admin;

import com.app.configs.SidebarConfig;
import com.app.dto.request.RoomCreateDTO;
import com.app.dto.request.RoomSearchCriteria;
import com.app.dto.response.BedTypeResponse;
import com.app.dto.response.ListRoomAdminRoomPageDTO;
import com.app.dto.response.RoomStatusResponse;
import com.app.dto.response.RoomTypeResponse;
import com.app.services.BedTypeService;
import com.app.services.RoomService;
import com.app.services.RoomStatusService;
import com.app.services.RoomTypeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private BedTypeService bedTypeService;

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
    // Sử dụng @ModelAttribute không cần quan tâm dữ liệu gửi từ URL hay là trong body.
    // Tại vì khi sử dụng View (SSR) dữ liệu gửi lên theo chuẩn HTML Form, tất cả name
    // và value đều để chung vào 1 chỗ. Spring tự vào đó lấy dữ liệu cho DTO.
    public String createRoomsPageAdmin(Model model,
            // Biến searchCriteria trong dấu "" là tên object tự động trả về cho View, không cần trả về thủ công nữa.
            @ModelAttribute("searchCriteria") RoomSearchCriteria criteria) {

        ListRoomAdminRoomPageDTO roomSearchResponse = this.roomService.getRooms(criteria);
        RoomTypeResponse roomTypeResponse = new RoomTypeResponse(this.roomTypeService.getRoomTypes());
        RoomStatusResponse roomStatusResponse = new RoomStatusResponse(this.roomStatusService.getRoomStatus());

        model.addAttribute("roomSearchResponse", roomSearchResponse);
        model.addAttribute("roomTypeResponse", roomTypeResponse);
        model.addAttribute("roomStatusResponse", roomStatusResponse);

        return "RoomsPageAdmin";
    }

    @GetMapping("/addOrUpdateRoom")
    public String createAddOrUpdateRoomPage(Model model) {

        RoomTypeResponse roomTypeResponse = new RoomTypeResponse(this.roomTypeService.getRoomTypes());
        RoomStatusResponse roomStatusResponse = new RoomStatusResponse(this.roomStatusService.getRoomStatus());
        BedTypeResponse bedTypeResponse = new BedTypeResponse(this.bedTypeService.getBedTypes());

        // Form Binding sử dụng dto
        model.addAttribute("roomForm", new RoomCreateDTO());

        model.addAttribute("roomTypeResponse", roomTypeResponse);
        model.addAttribute("roomStatusResponse", roomStatusResponse);
        model.addAttribute("bedTypeResponse", bedTypeResponse);

        return "RoomAddOrUpdatePageAdmin";
    }

    @PostMapping("/addOrUpdateRoom/process")
    public String addOrUpdateRoom(Model model,
            // đặt đúng tên trong file HTML, để nếu có lỗi nó sẽ trả về object tên này cho html
            @Valid @ModelAttribute("roomForm") RoomCreateDTO roomCreateDTO,
            BindingResult bindingResult
    ) {

        // Validate input
        if (bindingResult.hasErrors()) {
            System.out.println("Chi tiết lỗi: " + bindingResult.getAllErrors());
            RoomTypeResponse roomTypeResponse = new RoomTypeResponse(this.roomTypeService.getRoomTypes());
            RoomStatusResponse roomStatusResponse = new RoomStatusResponse(this.roomStatusService.getRoomStatus());
            BedTypeResponse bedTypeResponse = new BedTypeResponse(this.bedTypeService.getBedTypes());

            model.addAttribute("roomTypeResponse", roomTypeResponse);
            model.addAttribute("roomStatusResponse", roomStatusResponse);
            model.addAttribute("bedTypeResponse", bedTypeResponse);

            return "RoomAddOrUpdatePageAdmin";
        }

        this.roomService.createRooms(roomCreateDTO);
        return "HomePageAdmin";
    }
}
