package com.example.foodordering.services.fcm;

import com.example.foodordering.entities.DeviceToken;
import com.example.foodordering.repositories.DeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {
    private final DeviceTokenRepository deviceTokenRepository;

    @Transactional
    public void saveToken(String token) { // token thiet bi gui thong bao
        Optional<DeviceToken> deviceToken = deviceTokenRepository.findByToken(token);

        if (deviceToken.isPresent()) {
            return;
        }

        DeviceToken newDeviceToken = new DeviceToken();
        newDeviceToken.setToken(token);
        deviceTokenRepository.save(newDeviceToken);
    }
}
