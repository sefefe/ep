package com.wndpo.ep.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerDTO {
    private String region;
    private String bsc;
    private String rnc;
    private String city;
    private String name;
}
