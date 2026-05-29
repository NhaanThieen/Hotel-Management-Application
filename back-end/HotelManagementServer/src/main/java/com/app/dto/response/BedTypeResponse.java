
package com.app.dto.response;

import com.app.pojo.Bedtype;

import java.util.List;


public class BedTypeResponse {
    private List<Bedtype> bedTypes;

    public BedTypeResponse(List<Bedtype> bedTypes) {
        this.bedTypes = bedTypes;
    }

    /**
     * @return the bedTypes
     */
    public List<Bedtype> getBedTypes() {
        return bedTypes;
    }

    /**
     * @param bedTypes the bedTypes to set
     */
    public void setBedTypes(List<Bedtype> bedTypes) {
        this.bedTypes = bedTypes;
    }
    
    
}
