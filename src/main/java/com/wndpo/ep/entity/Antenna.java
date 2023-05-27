package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Audited
/*

Physical antenna with physical characteristics
For Example
Site-X
  S1-GU900-DCS1800-U2100-L1800 // single antenna for all technologies
  S1-GU900
  S1-DCS1800
  S1-U2100   // Multiple antennas different antenna for different technologies


*/

public class Antenna{
    @Id
    @SequenceGenerator(
            name = "antenna_sequence",
            sequenceName = "antenna_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "antenna_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long antennaId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(


            name = "sector_id",
            referencedColumnName = "id"
    )
    private Sector sector;
    @JsonManagedReference
    @OneToMany(
            mappedBy = "antenna"
                       )

    private List<ElectricalController> controllers;

    private Integer localAntennaId;
    private String antennaModel;
    private Double height;
    private Integer azimuth;
    private Float mechanicalDownTilt;
    private String sectors;

    public Antenna(Sector sector, List<ElectricalController> controllers,Integer localAntennaId, String antennaModel, Double height, Integer azimuth, Float mechanicalDownTilt, String sectors) {
        this.sector = sector;
        this.controllers = controllers;
        this.localAntennaId = localAntennaId;
        this.antennaModel = antennaModel;
        this.height = height;
        this.azimuth = azimuth;
        this.mechanicalDownTilt = mechanicalDownTilt;
        this.sectors = sectors;
    }


}
