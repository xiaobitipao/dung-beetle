package com.xiaobitipao.dungbeetle.api.v1;

import com.xiaobitipao.dungbeetle.core.interceptors.ScopeLevel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/test")

public class TestController {
    @PostMapping("/sample/{id}")
    public String getParam1(@PathVariable Integer id,
                            @RequestParam String name,
                            @RequestBody Map<String, Object> person) {
        System.out.println(person);
        return "sample: {id}=" + id + ",name=" + name;
    }

    @PostMapping("/sample/test2")
    @ScopeLevel()
    public Properties getParam1() {
        Properties prop = new Properties();
        prop.put("key1", "value1");
        return prop;
    }
}
