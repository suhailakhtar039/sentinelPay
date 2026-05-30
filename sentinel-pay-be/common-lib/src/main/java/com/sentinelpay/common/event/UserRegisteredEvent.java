package com.sentinelpay.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisteredEvent {

    private String correlationID;

    private Long userId;

    private String email;

    private String name;
}
