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

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "rnc_name_unique",
                columnNames = "rncLogicalName"
        )
)
@Audited

public class RNC implements Serializable {
    @Id
    @SequenceGenerator(
            name = "rnc_sequence",
            sequenceName = "rnc_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "rnc_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Integer rncId;
    private String rncName;
    @JsonIgnore
    private String City;

  @Column(
          nullable = false
  )
  @Enumerated(
          value = EnumType.STRING
  )
    private EP.RNC rncLogicalName;

  @ManyToMany(
          mappedBy = "rncs"
  )
  //@JsonIgnore
  @JsonBackReference
   private List<Region> regions;

   @JsonManagedReference
    @OneToMany
            (
                    mappedBy = "rnc"
            )
    List<Site> sites;

   public void addRegion(Region region){
       if(regions==null){
           regions = new ArrayList<>();
       }
       regions.add(region);
   }
}
