
package com.app.services;

import com.app.pojo.Bedtype;
import java.util.List;
import java.util.Set;


public interface BedTypeService {
    public List<Bedtype> getBedTypes();
    List<Bedtype> getBedTypesByListId(Set<Integer> ids);
}
