package com.app.services.servicesImpl;

import com.app.dto.request.UserCreateDTO;
import com.app.pojo.Role;
import com.app.pojo.User;
import com.app.repositories.UserRepository;
import com.app.repositories.UserRoleRepository;
import com.app.services.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service()
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    @Transactional
    public User createUser(UserCreateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("Dữ liệu gửi lên không được NULL");
        }
        // Do các trường này dưới db là unique nên cần kiểm tra
        if (this.userRepository.getUserByUsername(userDTO.getUserName()) != null) {
            throw new IllegalArgumentException("Tên đăng nhập '" + userDTO.getUserName() + "' đã tồn tại!");
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().trim().isEmpty()) {
            if (this.userRepository.getUserByEmail(userDTO.getEmail()) != null) {
                throw new IllegalArgumentException("Địa chỉ Email '" + userDTO.getEmail() + "' đã được sử dụng!");
            }
        }
        if (userDTO.getPhone() != null && !userDTO.getPhone().trim().isEmpty()) {
            if (this.userRepository.getUserByPhone(userDTO.getPhone()) != null) {
                throw new IllegalArgumentException("Số điện thoại '" + userDTO.getPhone() + "' đã được sử dụng!");
            }
        }

        Role role = this.userRoleRepository.getUserRoleById(userDTO.getRoleId());
        if (role == null) {
            throw new IllegalArgumentException("Role của account không được null");
        }

        User u = new User();
        u.setName(userDTO.getName());
        u.setUsername(userDTO.getUserName());
        u.setEmail(userDTO.getEmail());
        u.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        u.setPhone(userDTO.getPhone());
        u.setIsDeleted((short) 0);
        u.setRoleId(role);

        if (userDTO.getAvatarFile() != null && !userDTO.getAvatarFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(userDTO.getAvatarFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                throw new RuntimeException("Lỗi upload ảnh đại diện: " + ex.getMessage());
            }
        }
        return this.userRepository.createUser(u);
    }
}
