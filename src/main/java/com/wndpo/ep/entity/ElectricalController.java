package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
public class ElectricalController {
    @Id
    @SequenceGenerator(
            name = "ec_sequence",
            sequenceName = "ec_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "ec_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long ecid;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(
            name = "antenna_id",
            referencedColumnName = "antennaId"
    )
    private Antenna antenna;
    private Integer localECid;

    @JsonManagedReference
    @OneToMany(
           mappedBy = "electricalController"

    )
    private List<Band> bands;
    private Float electricalDownTilt;
    private Float HorizontalBeamWidth;
    private Float VerticalBeamWidth;

    public ElectricalController(Antenna antenna, Integer localECId,List<Band> bands, Float electricalDownTilt, Float horizontalBeamWidth, Float verticalBeamWidth) {
        this.antenna = antenna;
        this.localECid = localECId;
        this.bands = bands;
        this.electricalDownTilt = electricalDownTilt;
        HorizontalBeamWidth = horizontalBeamWidth;
        VerticalBeamWidth = verticalBeamWidth;
    }
}
