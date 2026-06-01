/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dto.response;

import java.util.List;

public class ListUserAdminUserPageDTO {

    public static class UserForAdminUserPageDTO {
        private Integer id;
        private String name;
        private String username;
        private Integer roleId;
        private String roleName;
        private Integer isDeleted;
        private String phone;
        
        /**
         * @return the id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * @param username the username to set
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * @return the roleId
         */
        public Integer getRoleId() {
            return roleId;
        }

        /**
         * @param roleId the roleId to set
         */
        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        /**
         * @return the isDeleted
         */
        public Integer getIsDeleted() {
            return isDeleted;
        }

        /**
         * @param isDeleted the isDeleted to set
         */
        public void setIsDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
        }

        /**
         * @return the phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone the phone to set
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return the roleName
         */
        public String getRoleName() {
            return roleName;
        }

        /**
         * @param roleName the roleName to set
         */
        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
        
    }

    private List<UserForAdminUserPageDTO> users;
    private Integer currentPage;

    public ListUserAdminUserPageDTO() {
    }

    public ListUserAdminUserPageDTO(List<UserForAdminUserPageDTO> users) {
        this.users = users;
    }

    /**
     * @return the users
     */
    public List<UserForAdminUserPageDTO> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<UserForAdminUserPageDTO> users) {
        this.users = users;
    }

    /**
     * @return the currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
