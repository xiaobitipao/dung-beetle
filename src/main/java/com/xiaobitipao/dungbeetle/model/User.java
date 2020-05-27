package com.xiaobitipao.dungbeetle.model;

import com.xiaobitipao.dungbeetle.util.MapAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Where(clause = "delete_time is null")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    // openid 是调用微信的 login 以后微信返回的用户的唯一标识
    private String openid;
    private String nickname;
    private String email;
    private String mobile;
    // 用户角色(用于权限控制)
    private String role;
    // 暂时不用: 微信登录时由于是依赖于微信的登录功能所以不需要 password 字段
    private String password;
    // 暂时不用: 微信登录时如果不使用 openid 而是使用 unionid 的话使用该字段
    private Long unifyUid;
    // 微信登录后的个人简介
    @Convert(converter = MapAndJson.class)
    private Map<String, Object> wxProfile;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "UserCoupon",
//            joinColumns = @JoinColumn(name = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "couponId"))
//    private List<Coupon> couponList;


    //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "user", fetch = FetchType.LAZY)

//    @OneToMany
//    @JoinColumn(name = "userId")
//    private List<Order> orders = new ArrayList<>();
}
