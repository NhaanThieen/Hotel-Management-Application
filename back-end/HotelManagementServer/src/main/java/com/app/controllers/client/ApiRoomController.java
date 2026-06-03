package com.app.controllers.client;

import com.app.dto.request.ApiRoomSearchCriteria;
import com.app.dto.response.ApiResponse;
import com.app.dto.response.ApiRoomPageDTO;
import com.app.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiRoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<?> getRoomsForClient(@Valid @ModelAttribute ApiRoomSearchCriteria criteria) {
        try {
            ApiRoomPageDTO pageData = this.roomService.getRoomsForClient(criteria);
            ApiResponse<ApiRoomPageDTO> response = new ApiResponse<>(200, "Tra cứu danh sách phòng thành công", pageData);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(400, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(500, "Lỗi server: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
