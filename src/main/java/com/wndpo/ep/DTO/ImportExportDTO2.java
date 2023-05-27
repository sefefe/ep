package com.wndpo.ep.DTO;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wndpo.ep.entity.EP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class ImportExportDTO2 {
    private Integer siteId;
    private EP.Region regionName;
    private String adminRegion;
    private String city;
    private String wereda;
    private EP.SiteClass siteClass;
    private EP.RNC rnc;
    private EP.BSC bsc;
    private String carrierConfiguration;
    private String siteName;
    private Double longitude;
    private Double latitude;
    private Boolean roofTop;
    private Boolean outdoor;
    private Long antennaId;
    private Integer localAntennaId;
    private Integer sectorId;
    private String antennaModel;
    private Double height;
    private Integer azimuth;
    private Float mechanicalDownTilt;
    private String sectors;
    private Long ecid;
    private Integer localECid;
    private Float electricalDownTilt;
    private Float HorizontalBeamWidth;
    private Float VerticalBeamWidth;
    private Long bandid;
    private EP.Band band;
    private EP.RAT rat;
    private Long id;
    private Integer cellId;
    private Integer lac;
    private Integer rac;
    private String cellName;
    private Boolean active;
    private Integer bcch;
    private Integer bsic;
    private String tch;
    private Integer psc;
    private Integer downlinkUarfcn;
    private Integer pcpichPower;
    private Integer maxPower;
    private Integer tac;
    private Float bandWidth;
    private Integer pci;
    private Integer downlinkEarfcn;
    private EP.Vendor vendor;
    private String description;
    private Integer towerHeight;
}
