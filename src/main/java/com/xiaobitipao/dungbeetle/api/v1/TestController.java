package com.xiaobitipao.dungbeetle.api.v1;

import com.xiaobitipao.dungbeetle.dto.PersonDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.Map;

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
    public PersonDTO getParam1(@RequestBody @Validated PersonDTO person) {
        return person;
    }
}
