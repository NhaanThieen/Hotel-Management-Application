package com.app.controllers.admin;

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
@RequestMapping("/admin/rooms")
public class AdminRoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoomStatusService roomStatusService;

    @Autowired
    private BedTypeService bedTypeService;

    private void loadBasicRoomData(Model model) {
        model.addAttribute("roomTypeResponse", new RoomTypeResponse(this.roomTypeService.getRoomTypes()));
        model.addAttribute("roomStatusResponse", new RoomStatusResponse(this.roomStatusService.getRoomStatus()));
        model.addAttribute("bedTypeResponse", new BedTypeResponse(this.bedTypeService.getBedTypes()));
    }

    @GetMapping("/")
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
        // Form Binding sử dụng dto
        model.addAttribute("roomForm", new RoomCreateDTO());
        loadBasicRoomData(model);
        return "RoomAddOrUpdatePageAdmin";
    }

    @PostMapping("/addOrUpdateRoom/process")
    public String addOrUpdateRoom(Model model,
            // đặt đúng tên trong file HTML, để nếu có lỗi nó sẽ trả về object tên này cho html
            @Valid @ModelAttribute("roomForm") RoomCreateDTO roomCreateDTO,
            BindingResult bindingResult,
            // Trả về giá trị khi redirect
            RedirectAttributes redirectAttributes
    ) {

        // Validate input
        if (bindingResult.hasErrors()) {
            // Lỗi này không cần dùng model để quăng ra View.
            System.out.println("Chi tiết lỗi: " + bindingResult.getAllErrors());
            loadBasicRoomData(model);
            return "RoomAddOrUpdatePageAdmin";
        }

        // Xử lý bắt các expection khi thrown ra
        try {
            this.roomService.createRooms(roomCreateDTO);
            redirectAttributes.addFlashAttribute("successMsg", "Thêm phòng thành công: " + roomCreateDTO.getName());
            return "redirect:/admin/rooms/";
        } catch (Exception e) {
            // Cần dùng model để quăng lỗi này ra View thay vì crash server
            model.addAttribute("errorMsg", e.getMessage());
            loadBasicRoomData(model);
            return "RoomAddOrUpdatePageAdmin";
        }
    }
}
