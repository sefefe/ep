package com.wndpo.ep.model;

import com.wndpo.ep.entity.EP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportResponse {
    private Integer rowNumber;
    private StatusCode siteStatusCode;
    private StatusCode sectorStatusCode;
    private StatusCode antennaStatusCode;
    private StatusCode ecStatusCode;
    private StatusCode bandStatusCode;
    private StatusCode cellStatusCode;
    private Ne ne = new Ne();
    private StatusCode status;
    private EP.Region region;
}
