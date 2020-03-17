package com.electrolux.washing_machine_app.rest.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WashingProgramDTO {
    private String name;
    private String material;
    private int temperature;
    private int duration;
    private int spinningSpeed;
}
