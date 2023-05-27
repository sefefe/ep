package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.Audited;
import java.util.List;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
public class Sector {
    @Id
    @SequenceGenerator(
            name = "sector_sequence",
            sequenceName = "sector_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "sector_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;

    private Integer sectorId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(
            name ="site_id",
            referencedColumnName = "siteId"
    )
    private Site site;
@JsonManagedReference
@OneToMany(
  fetch=EAGER,
        mappedBy = "sector"

)
    private List<Antenna> antennas;

    public Sector(Integer sectorId, Site site, List<Antenna> antennas) {
        this.sectorId = sectorId;
        this.site = site;
        this.antennas = antennas;
    }


}
