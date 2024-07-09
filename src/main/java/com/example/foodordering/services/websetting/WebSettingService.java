package com.example.foodordering.services.websetting;

import com.example.foodordering.entities.WebSetting;
import com.example.foodordering.repositories.WebSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.font.OpenType;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebSettingService {
    private final WebSettingRepository webSettingRepository;



    @Transactional
    public WebSetting getWebSetting() {
        return webSettingRepository.findWebSettingByIsChooseTrue();
    }
    @Transactional
    public void addWebSetting(WebSetting webSetting) {
        webSettingRepository.save(webSetting);
    }

    @Transactional
    public void updateWebSetting(WebSetting webSetting) {
        Optional<WebSetting> webSettingOptional = webSettingRepository.findById(webSetting.getId());

        if(webSettingOptional.isPresent()) {
            WebSetting webSettingUpdate = webSettingOptional.get();
            webSettingUpdate.setName(webSetting.getName());
            webSettingUpdate.setEmail(webSetting.getEmail());
            webSettingUpdate.setPhone(webSetting.getPhone());
            webSettingUpdate.setAddress(webSetting.getAddress());
            webSettingUpdate.setImageUrl(webSetting.getImageUrl());
            webSettingRepository.save(webSettingUpdate);
        }

    }
}
