package com.app.services;

import com.app.dto.response.ApiServiceDTO;
import java.util.List;

public interface ServiceService {
    public List<ApiServiceDTO> getActiveServicesForClient();
}