package com.example.foodordering.repositories;

import com.example.foodordering.entities.WebSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSettingRepository extends JpaRepository<WebSetting, Long>{
    WebSetting findWebSettingByIsChooseTrue();
}
