package com.example.foodordering.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
public class WebSettingUpdateDTO {
    @Hidden
    private Long id;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String address;
}
