package com.electrolux.washing_machine_app.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "washing_program")
public class WashingProgram {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column (updatable = false, nullable = false)
    private UUID id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String material;

    @Column (nullable = false)
    private int temperature;

    @Column (nullable = false)
    private int duration;

    @Column (name = "spinning_speed", nullable = false)
    private int spinningSpeed;
}
