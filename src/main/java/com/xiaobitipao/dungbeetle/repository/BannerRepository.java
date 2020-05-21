package com.xiaobitipao.dungbeetle.repository;

import com.xiaobitipao.dungbeetle.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * jpa 是按照方法签名进行检索的，不需要写 sql 就可以完全完成查询操作
 */
public interface BannerRepository extends JpaRepository<Banner, Long> {

    Banner findOneById(Long id);

    Banner findOneByName(String name);
}
