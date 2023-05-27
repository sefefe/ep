package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Audited
public class Access {
    @Id
    @SequenceGenerator(
            name = "access_sequence",
            sequenceName = "access_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "access_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long accessId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(
            name = "user_name",
            referencedColumnName = "userName"
    )
    private User user;
    @OneToOne
    @JoinColumn(
            name = "region_name",
            referencedColumnName = "regionName"
    )


    private Region region;
    private boolean hasWriteAccess;

    public Access(User user, Region region, boolean hasWriteAccess) {
        this.user = user;
        this.region = region;
        this.hasWriteAccess = hasWriteAccess;
    }
}
