package com.xiaobitipao.dungbeetle.repository;


import com.xiaobitipao.dungbeetle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据微信的 openid 检索 user 信息
     */
    Optional<User> findByOpenid(String openid);

    User findByEmail(String email);

    User findFirstById(Long id);

    User findByUnifyUid(Long uuid);
}
