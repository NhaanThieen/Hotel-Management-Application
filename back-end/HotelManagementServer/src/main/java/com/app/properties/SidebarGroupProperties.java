
package com.app.properties;

import java.util.ArrayList;
import java.util.List;


public class SidebarGroupProperties {
    private String name;
    private String id;
    private String icon;
    private List<SidebarItemProperties> sidebarItems = new ArrayList<>();

    public SidebarGroupProperties(String names, String id ,String icon) {
        this.name = names;
        this.id = id;
        this.icon = icon;
    }
    public void addItem(SidebarItemProperties item){
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
    public List<SidebarItemProperties> getSidebarItems() {
        return sidebarItems;
    }

    /**
     * @param sidebarItems the sidebarItems to set
     */
    public void setSidebarItems(List<SidebarItemProperties> sidebarItems) {
        this.sidebarItems = sidebarItems;
    }
}
