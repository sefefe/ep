package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Audited

public class Band {
  @Id
  @SequenceGenerator(
          name = "band_sequence",
          sequenceName = "band_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          generator = "band_sequence",
          strategy = GenerationType.SEQUENCE
  )
  private Long id;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(
          name = "electrical_controller",
          referencedColumnName = "ecid"
  )
  private ElectricalController electricalController;
  @Enumerated(
          value = EnumType.STRING
  )
  private EP.Band name;
  @Enumerated(
          value = EnumType.STRING
  )
  private EP.RAT rat;




  @JsonManagedReference
  @OneToMany(

          mappedBy = "band"

  )
  private List<Cell2> cells;

  public Band(EP.Band name, EP.RAT rat, ElectricalController ec,List<Cell2> cells) {
    this.name = name;
    this.rat = rat;
    this.electricalController = ec;
    this.cells = cells;
  }

  public Band(Long id,EP.Band name, EP.RAT rat, ElectricalController ec,List<Cell2> cells) {
    this.id =id;
    this.name = name;
    this.rat = rat;
    this.electricalController = ec;
    this.cells = cells;
  }
}
