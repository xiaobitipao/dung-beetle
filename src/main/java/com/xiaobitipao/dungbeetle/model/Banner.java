package com.xiaobitipao.dungbeetle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Banner extends BaseEntity {
    @Id
    private Long id;
    private String name;
    private String title;
    private String img;
    private String description;

    // 设置懒加载
    @OneToMany(fetch = FetchType.LAZY)
    // 由于没有设置物理外键，所以这里通过 JoinColumn 修饰外键约束
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;
}
