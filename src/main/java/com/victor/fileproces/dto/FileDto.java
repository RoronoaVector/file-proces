package com.victor.fileproces.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FileDto {

    private String name;
    private String sourName;
    private String priority;
    private String description;

}
