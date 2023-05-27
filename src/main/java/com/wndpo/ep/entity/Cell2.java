package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
 @Entity


public class Cell2 {
    @Id
    @SequenceGenerator(
            name = "cell_sequence",
            sequenceName = "cell_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "cell_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @JsonBackReference
    @ManyToOne

    @JoinColumn(
            name = "band_id",
            referencedColumnName = "id"
    )

    private Band band;
    private Integer cellId;
    private String cellName;
    private Boolean active;
    private Integer bcch;
    private Integer bsic;
    private String tch;
    private Integer rac;
    private Integer lac;
    private Integer tac;
    private Integer psc;
    private Integer downlinkUarfcn;
    private Integer pcpichPower;
    private Integer maxPower;
    private Integer pci;
    private Integer downlinkEarfcn;
    private Float bandWidth;





}
