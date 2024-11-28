
package com.api.book.endpoint.CustomEndpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "customInfo")
public class CustomEndpoint {

    @ReadOperation
    public Map<String, Object> customInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("appName", "Book Management API");
        info.put("version", "1.0.0");
        info.put("description", "Custom Actuator Endpoint for additional app information");
        info.put("maintainer", "Shivam Pathak");
        return info;
    }
}
