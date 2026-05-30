package com.app.configs;

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
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class SidebarConfig {

    public List<SidebarGroupProperties> getFuction() {
        return getFuction(null);
    }

    public List<SidebarGroupProperties> getFuction(HttpServletRequest request) {
        Map<String, SidebarGroupProperties> groupMap = new LinkedHashMap<>();
        Properties props = new Properties();
        List<String> orderedKeys = new ArrayList<>(); 

        String resourceName = "SidebarFunctions_vi.properties";
        if (request != null) {
            String uri = request.getRequestURI();
            if (uri != null) {
                if (uri.startsWith("/admin")) {
                    resourceName = "Sidebar_Admin.properties";
                } else if (uri.startsWith("/reception") || uri.startsWith("/reception/")) {
                    resourceName = "Sidebar_Receptionist.properties";
                }
            }
        }

        try {
            ClassPathResource resource = new ClassPathResource(resourceName);
            
            // Nạp thuộc tính để xử lý Unicode tự động
            props = PropertiesLoaderUtils.loadProperties(resource);
            
            // Đọc tuần tự để lấy đúng thứ tự các Key từ trên xuống dưới
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