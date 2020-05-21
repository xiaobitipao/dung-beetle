package com.xiaobitipao.dungbeetle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class BannerItem extends BaseEntity {
    @Id
    private Long id;
    private String name;
    private String img;
    private Short type;
    private String keyword;
    private Long bannerId;
}
