package com.app.properties;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nhaan
 */
public class SidebarItemProperties {
    private String name;
    private String url;

    public SidebarItemProperties() {
    }

    public SidebarItemProperties(String name, String url) {
        this.name = name;
        this.url = url;
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
     * @return the URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param URL the URL to set
     */
    public void setUrl(String URL) {
        this.url = URL;
    }
}
