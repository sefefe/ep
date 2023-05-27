package com.wndpo.ep.model;

import com.wndpo.ep.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ne {
    private Site site;

    private Sector sector;

    private Antenna antenna;

    private ElectricalController ec;

    private Band band;

    private Cell2 cell;


}
