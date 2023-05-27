package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
@Getter
@Setter
//@MappedSuperclass


//@JsonIgnoreProperties(value = { "antennas" })

public class Site implements Serializable {

    @Id
      private Integer siteId;
    @NotNull
    @ManyToOne(
            fetch = FetchType.EAGER
           )
    @JoinColumn(
            name="region",
            referencedColumnName = "regionName"
    )

//    @JsonIgnoreProperties(value = {"id","bscs","rncs"})
//    @JsonUnwrapped
    @JsonBackReference
    private Region region;




    private String adminRegion;
    private String city;
    private String wereda;
    @JsonManagedReference
    @OneToMany(
            mappedBy = "site"



    )

    private List<Sector> sectors;
@Enumerated(
        value = EnumType.ORDINAL
)

    private EP.SiteClass siteClass;
    @ManyToOne(optional = true)
    @JoinColumn(

            name = "rnc",
            referencedColumnName = "rncLogicalName",
            nullable = true
     )

//    @JsonIgnoreProperties(value = {"rncId","regions","rncName"})
//    @JsonUnwrapped
    @JsonBackReference
    private RNC rnc;

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "bsc",
            referencedColumnName = "bscLogicalName",
            nullable = true

    )
//    @JsonIgnoreProperties(value = {"bscId","regions","bscName"})
//    @JsonUnwrapped
    @JsonBackReference
    private BSC bsc;
//@OneToOne
//@JoinColumn(
//        name = "bts",
//        referencedColumnName = "btsId"
//)
////
//  private BTS bts;
    private String carrierConfiguration;
    @Column(
            nullable = false
    )
    private String siteName;
    @Column(
            nullable = false
    )
    private Double longitude;
    @Column(
            nullable = false
    )
    private Double latitude;


    private Boolean roofTop;
    @Enumerated(
            value = EnumType.STRING
    )
    private EP.Vendor vendor;

    private Boolean outdoor;
    private String description;
    private Integer towerHeight;

    public Site(Integer siteId, Region region, String adminRegion, String city, String wereda, EP.SiteClass siteClass, RNC rnc, BSC bsc, String carrierConfiguration, String siteName, Double longitude, Double latitude, boolean roofTop, EP.Vendor vendor, boolean outdoor, String description, Integer towerHeight) {
        this.siteId = siteId;
        this.region = region;
        this.adminRegion = adminRegion;
        this.city = city;
        this.wereda = wereda;
        this.siteClass = siteClass;
        this.rnc = rnc;
        this.bsc =bsc;
        this.carrierConfiguration = carrierConfiguration;
        this.siteName = siteName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.roofTop = roofTop;
        this.vendor = vendor;
        this.outdoor = outdoor;
        this.description = description;
        this.towerHeight = towerHeight;
    }


}
