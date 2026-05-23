
package com.app.dto;

import java.util.ArrayList;
import java.util.List;


public class SidebarGroupDto {
    private String name;
    private String id;
    private String icon;
    private List<SidebarItemDto> sidebarItems = new ArrayList<>();

    public SidebarGroupDto(String names, String id ,String icon) {
        this.name = names;
        this.id = id;
        this.icon = icon;
    }
    public void addItem(SidebarItemDto item){
        this.getSidebarItems().add(item);
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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the sidebarItems
     */
    public List<SidebarItemDto> getSidebarItems() {
        return sidebarItems;
    }

    /**
     * @param sidebarItems the sidebarItems to set
     */
    public void setSidebarItems(List<SidebarItemDto> sidebarItems) {
        this.sidebarItems = sidebarItems;
    }
    
    
    
}
