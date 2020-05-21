package com.xiaobitipao.dungbeetle.service;

import com.xiaobitipao.dungbeetle.model.Banner;
import com.xiaobitipao.dungbeetle.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    public Banner getByName(String name) {
        return bannerRepository.findOneByName(name);
    }
}
