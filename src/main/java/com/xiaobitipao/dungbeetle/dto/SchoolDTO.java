package com.xiaobitipao.dungbeetle.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SchoolDTO {

    @Length(min = 3)
    private String schoolName;
}
