package com.wndpo.ep.DTO;

import com.wndpo.ep.entity.EP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeDTO {
    private EP.Region region;
    private Integer siteId;
    private Integer sectorId;
    private EP.Band band;
    private Integer cellId;

}
