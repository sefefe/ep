package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "region_name_unique",
                columnNames = "regionName"
        )
)
@Audited
@JsonIgnoreProperties(value = { "rncs","bscs" })
public class Region implements Serializable {
    @Id
    @SequenceGenerator(
            name = "region_sequence",
            sequenceName = "region_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "region_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;
    @Enumerated(
            value = EnumType.STRING
    )
    private EP.Region regionName;

   @ManyToMany
   @JoinTable(
           name = "region_rnc",
           joinColumns = @JoinColumn(
                   name = "region_name",
                   referencedColumnName = "regionName"
           ),
           inverseJoinColumns = @JoinColumn(
                   name="rnc_name",
                   referencedColumnName = "rncLogicalName"
           )
   )

    @JsonManagedReference
    private List<RNC> rncs;
    @ManyToMany
    @JoinTable(
            name = "region_bsc",
            joinColumns = @JoinColumn(
                    name = "region_name",
                    referencedColumnName = "regionName"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="bsc_name",
                    referencedColumnName = "bscLogicalName"
            )
    )

    @JsonManagedReference
    private List<BSC> bscs;

    @JsonManagedReference
    @OneToMany(
     mappedBy = "region"
    )

    private List<Site> sites;

    public void addRNCs(RNC rnc){
        if(rncs==null) rncs = new ArrayList<RNC>();
   rncs.add(rnc);
    }
    public void addBSCs(BSC bsc){
        if(bscs==null) bscs = new ArrayList<BSC>();
     bscs.add(bsc);
    }

}
