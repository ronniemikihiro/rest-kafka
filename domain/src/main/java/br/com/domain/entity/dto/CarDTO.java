package br.com.domain.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class CarDTO implements IDTO {

    private String name;
    private String brand;

}
