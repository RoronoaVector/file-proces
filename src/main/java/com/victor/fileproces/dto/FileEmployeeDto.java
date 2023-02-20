package com.victor.fileproces.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FileEmployeeDto {

    private String name;
    private String surname;
    private String telephone;
    private String mail;
    private String enterprise;

}
