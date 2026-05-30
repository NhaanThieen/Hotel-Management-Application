/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories;

import com.app.pojo.Bed;
import java.util.List;


public interface BedRepository {
    public List<Bed> saveAll(List<Bed> beds);
}
