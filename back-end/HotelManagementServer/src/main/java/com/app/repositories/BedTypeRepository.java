
package com.app.repositories;

import com.app.pojo.Bedtype;
import java.util.List;
import java.util.Set;

public interface BedTypeRepository {
    public List<Bedtype> getBedTypes();
    public List<Bedtype> getBedTypesByListId(Set<Integer> ids);
}
