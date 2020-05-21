package com.xiaobitipao.dungbeetle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
// 由于其他的 model 都有 @Entity 注解，所以成员变量和数据表字段有一一的映射
// 如果想让 BaseEntity 类的成员变量也和数据表字段有一一的映射的话，需要使用 @MappedSuperclass 注解表名这个类是映射的一个超类
// 不使用 @Entity 注解的原因是 BaseEntity 并没有映射到数据库的一张表
@MappedSuperclass
public abstract class BaseEntity {

    // 不会被 jackson 序列化(即不返回给前端)
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Date createTime;
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;
}
