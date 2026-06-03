package com.app.services.servicesImpl;

import com.app.properties.SidebarGroupProperties;
import com.app.properties.SidebarItemProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

@Service
public class SidebarService {

    public List<SidebarGroupProperties> getSidebarByRole(String role) {
        String fileName = "sidebar_" + role.toLowerCase() + ".properties";
        Map<String, SidebarGroupProperties> groupMap = new LinkedHashMap<>();
        Properties props = new Properties();
        List<String> orderedKeys = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            if (!resource.exists()) {
                return new ArrayList<>();
            }

            props = PropertiesLoaderUtils.loadProperties(resource);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    int eqIdx = line.indexOf('=');
                    if (eqIdx != -1) {
                        orderedKeys.add(line.substring(0, eqIdx).trim());
                    }
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }

        for (String menuKey : orderedKeys) {
            String rawValue = props.getProperty(menuKey);
            if (rawValue == null) {
                continue;
            }
            String[] parts = rawValue.split("\\|", -1);

            if (menuKey.startsWith("sidebar.grp.")) {
                String groupName = menuKey.substring("sidebar.grp.".length());
                String name = parts.length > 0 && !parts[0].isEmpty() ? parts[0].trim() : "";
                String id = parts.length > 1 && !parts[1].isEmpty() ? parts[1].trim() : "";
                String icon = parts.length > 2 && !parts[2].isEmpty() ? parts[2].trim() : "";

                if (groupMap.containsKey(groupName)) {
                    SidebarGroupProperties existingGroup = groupMap.get(groupName);
                    existingGroup.setName(name);
                    existingGroup.setId(id);
                    existingGroup.setIcon(icon);
                } else {
                    groupMap.put(groupName, new SidebarGroupProperties(name, id, icon));
                }

            } else if (menuKey.startsWith("sidebar.item.")) {
                String remaining = menuKey.substring("sidebar.item.".length());
                String groupName = remaining.split("\\.")[0];
                String name = parts.length > 0 && !parts[0].isEmpty() ? parts[0].trim() : "";
                String url = parts.length > 1 && !parts[1].isEmpty() ? parts[1].trim() : "";

                SidebarGroupProperties group = groupMap.get(groupName);
                if (group != null) {
                    group.addItem(new SidebarItemProperties(name, url));
                }
            }
        }

        return new ArrayList<>(groupMap.values());
    }
}
