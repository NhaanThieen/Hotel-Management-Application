package com.app.repositories;

import java.util.List;

public interface ServiceRepository {
    public List<com.app.pojo.Service> getActiveServices();
}