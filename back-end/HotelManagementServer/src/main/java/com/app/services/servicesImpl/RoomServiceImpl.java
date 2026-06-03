/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services.servicesImpl;

import com.app.dto.request.ApiRoomSearchCriteria;
import com.app.dto.request.RoomCreateDTO;
import com.app.dto.request.RoomSearchCriteria;
import com.app.dto.response.ApiRoomPageDTO;
import com.app.dto.response.ListRoomAdminRoomPageDTO;
import com.app.pojo.Bed;
import com.app.pojo.Bedtype;
import com.app.pojo.Room;
import com.app.pojo.Roomimage;
import com.app.pojo.Roomstatus;
import com.app.pojo.Roomtype;
import com.app.repositories.RoomImageRepository;
import com.app.repositories.RoomRepository;
import com.app.repositories.RoomStatusRepository;
import com.app.repositories.RoomTypeRepository;
import com.app.services.BedService;
import com.app.services.BedTypeService;
import com.app.services.RoomService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
// Mặc định bật transaction read cho toàn bộ class, để tránh trường hợp gọi chéo hàm nội bộ không có trasaction
// Transaction read cũng tiết kiệm tài nguyên hơn transaciton write.
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Autowired
    private BedTypeService bedTypeService;

    @Autowired
    private BedService bedService;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ListRoomAdminRoomPageDTO getRooms(RoomSearchCriteria roomData) {
        int currentPage = roomData.getPage();
        if (currentPage < 1) {
            currentPage = 1;
        }
        // Lấy rooms entity từ repo
        List<Room> roomEntities = this.roomRepository.getRooms(roomData);

        // Mapping roomEntities sang dto
        List<ListRoomAdminRoomPageDTO.RoomForAdminRoomPageDTO> roomDTOs = new ArrayList<>();

        for (Room entity : roomEntities) {
            ListRoomAdminRoomPageDTO.RoomForAdminRoomPageDTO roomDTO = new ListRoomAdminRoomPageDTO.RoomForAdminRoomPageDTO();

            roomDTO.setRoomId(entity.getRoomId());
            roomDTO.setName(entity.getRoomName());
            roomDTO.setImgURL(entity.getThumbnail());
            roomDTO.setRoomTypeId(entity.getRoomTypeId().getRoomTypeId());
            roomDTO.setRoomTypeName(entity.getRoomTypeId().getName());
            roomDTO.setCapacity(entity.getCapacity());

            List<ListRoomAdminRoomPageDTO.BedForAdminRoomPageDTO> beds = new ArrayList<>();

            for (Bed bed : entity.getBedList()) {
                ListRoomAdminRoomPageDTO.BedForAdminRoomPageDTO bedDTO = new ListRoomAdminRoomPageDTO.BedForAdminRoomPageDTO();
                bedDTO.setName(bed.getBedTypeId().getName());
                bedDTO.setQuantity(bed.getAmount());
                beds.add(bedDTO);
            }

            roomDTO.setBeds(beds);
            roomDTO.setPrice(entity.getPrice());
            roomDTO.setRoomStatusName(entity.getRoomStatusId().getName());
            roomDTO.setRoomStatusId(entity.getRoomStatusId().getRoomStatusId());
            roomDTOs.add(roomDTO);
        }

        ListRoomAdminRoomPageDTO response = new ListRoomAdminRoomPageDTO(roomDTOs);

        response.setCurrentPage(currentPage);
        return response;
    }

    @Override
    @Transactional
    public void createRooms(RoomCreateDTO roomDTO) {

        if (roomDTO == null) {
            throw new IllegalArgumentException("Dữ liệu gửi lên không được NULL");
        }

        // Lấy roomStatus và roomType
        Roomstatus rs = this.roomStatusRepository.getRoomStatusById(roomDTO.getStatusId());
        Roomtype rt = this.roomTypeRepository.getRoomTypeById(roomDTO.getTypeId());

        // Chuyển đổi roomDTO thành room
        Room room = new Room();

        if (rs == null) {
            throw new IllegalArgumentException("RoomStatus không được null");
        }
        if (rt == null) {
            throw new IllegalArgumentException("RoomType không được null");
        }
        // <0 là A < B
        if (roomDTO.getPrice().compareTo(rt.getPrice()) < 0) {
            throw new IllegalArgumentException(String.format(java.util.Locale.forLanguageTag("vi-VN"), "Số tiền không được nhỏ hơn %,d VNĐ", rt.getPrice().longValue()));
        }
        room.setRoomName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setRoomStatusId(rs);
        room.setRoomTypeId(rt);
        room.setPrice(roomDTO.getPrice());
        room.setIsDeleted((short) 0);
        room.setVersion(0);

        // Lưu img
        if (roomDTO.getThumbnailImage() != null && !roomDTO.getThumbnailImage().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(roomDTO.getThumbnailImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                room.setThumbnail(res.get("secure_url").toString());
            } catch (IOException ex) {
                throw new RuntimeException("Lỗi upload ảnh đại diện: " + ex.getMessage());
            }
        }

        // Lưu room để lấy id để lưu những table khác
        this.roomRepository.saveRoom(room);

        // Lưu ảnh phụ
        if (roomDTO.getExtraImageFiles() != null && !roomDTO.getExtraImageFiles().isEmpty()) {
            List<Roomimage> rimgList = new ArrayList<>();
            for (MultipartFile extraFile : roomDTO.getExtraImageFiles()) {
                if (extraFile != null && !extraFile.isEmpty()) {
                    try {
                        Map res = this.cloudinary.uploader().upload(extraFile.getBytes(),
                                ObjectUtils.asMap("resource_type", "auto"));
                        String extraImgUrl = res.get("secure_url").toString();

                        Roomimage rimg = new Roomimage();
                        rimg.setRoomId(room);
                        rimg.setUrl(extraImgUrl);
                        rimgList.add(rimg);

                    } catch (IOException ex) {
                        throw new RuntimeException("Lỗi upload ảnh phụ: " + ex.getMessage());
                    }
                }
            }
            this.roomImageRepository.saveAll(rimgList);
        }

        // Lưu bed
        if (roomDTO.getBeds() != null && !roomDTO.getBeds().isEmpty()) {
            // Lấy danh sách bed có dữ liệu trong dto
            List<RoomCreateDTO.BedRoomDTO> activeBeds = roomDTO.getBeds().stream().
                    filter(b -> b.getQuantity() != null && b.getQuantity() > 0).
                    toList();

            if (!activeBeds.isEmpty()) {
                // Lấy id của bedType trong Dto
                Set<Integer> bedTypeIds = new HashSet<>(activeBeds.stream().
                        map(b -> b.getBedTypeId()).toList());
                // Lấy bedType từ db
                List<Bedtype> bedTypes = this.bedTypeService.getBedTypesByListId(bedTypeIds);
                // Chuyển đổi list bedType thành Map cho tốc độ truy vấn cao
                Map<Integer, Bedtype> bedTypeMap = bedTypes.stream().
                        collect(Collectors.toMap(b -> b.getBedTypeId(), b -> b));
                // Lập danh sách các bed trong room
                List<Bed> entityBeds = new ArrayList<>();
                for (RoomCreateDTO.BedRoomDTO bedDto : activeBeds) {
                    Bedtype bt = bedTypeMap.get(bedDto.getBedTypeId());
                    if (bt != null) {
                        Bed bed = new Bed();
                        bed.setAmount(bedDto.getQuantity());
                        bed.setBedTypeId(bt);
                        bed.setRoomId(room);
                        entityBeds.add(bed);
                    }
                }
                // Lưu bed cho room
                this.bedService.saveAll(entityBeds);
            }
        }
    }

    @Override
    public ApiRoomPageDTO getRoomsForClient(ApiRoomSearchCriteria roomData) {

        List<Room> rooms = this.roomRepository.getRoomsForClient(roomData);

        // Mapping sang response
        List<ApiRoomPageDTO.RoomForClientDTO> roomDTOs = new ArrayList<>();

        for (Room r : rooms) {
            ApiRoomPageDTO.RoomForClientDTO roomDTO = new ApiRoomPageDTO.RoomForClientDTO();

            roomDTO.setRoomId(r.getRoomId());
            roomDTO.setName(r.getRoomName());
            roomDTO.setCapacity(r.getCapacity());
            roomDTO.setPrice(r.getPrice());

            if (r.getRoomTypeId() != null) {
                roomDTO.setRoomTypeId(r.getRoomTypeId().getRoomTypeId());
                roomDTO.setRoomTypeName(r.getRoomTypeId().getName());
            }
            roomDTO.setThumbnail(r.getThumbnail());

            List<ApiRoomPageDTO.BedDTO> bedDTOs = new ArrayList<>();
            if (r.getBedList() != null) {
                for (Bed bed : r.getBedList()) {
                    ApiRoomPageDTO.BedDTO bedDTO = new ApiRoomPageDTO.BedDTO();
                    bedDTO.setQuantity(bed.getAmount());
                    if (bed.getBedTypeId() != null) {
                        bedDTO.setName(bed.getBedTypeId().getName());
                    }
                    bedDTOs.add(bedDTO);
                }
            }
            roomDTO.setBeds(bedDTOs);
            roomDTOs.add(roomDTO);
        }

        ApiRoomPageDTO responsePage = new ApiRoomPageDTO();
        responsePage.setRooms(roomDTOs);
        int currentPage = (roomData.getPage() != null && roomData.getPage() > 0) ? roomData.getPage() : 1;
        responsePage.setCurrentPage(currentPage);
        return responsePage;
    }

    @Override
    public List<Room> getRoomsForReceptionist() {
        return roomRepository.getRoomsForReceptionist();
    }
}
