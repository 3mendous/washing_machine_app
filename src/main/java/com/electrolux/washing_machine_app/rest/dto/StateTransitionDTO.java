package com.electrolux.washing_machine_app.rest.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateTransitionDTO {
    private String initialState;
    private String nextState;
}
