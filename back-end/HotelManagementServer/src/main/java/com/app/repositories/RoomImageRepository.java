/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories;

import com.app.pojo.Roomimage;
import java.util.List;


public interface RoomImageRepository {
    public List<Roomimage> saveAll(List<Roomimage> roomImages);
}
