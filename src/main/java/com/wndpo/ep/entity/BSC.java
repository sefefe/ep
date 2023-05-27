package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "bsc_name_unique",
                columnNames = "bscLogicalName"
        )
)
@Audited
public class BSC implements Serializable {
    @Id
    @SequenceGenerator(
            name = "bsc_sequence",
            sequenceName = "bsc_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "bsc_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Integer bscId;
    private String bscName;
    @Enumerated(
            value = EnumType.STRING
    )
    private EP.BSC bscLogicalName;
    @JsonIgnore
    private String City;
    @ManyToMany(
            mappedBy = "bscs"
    )
    //@JsonIgnore
    @JsonBackReference
    private List<Region> regions;
    @JsonManagedReference
    @OneToMany(
            mappedBy = "bsc"

    )
    List<Site> site;
    public void addRegion(Region region){
        if(regions==null){
            regions = new ArrayList<>();
        }
        regions.add(region);
    }
}
