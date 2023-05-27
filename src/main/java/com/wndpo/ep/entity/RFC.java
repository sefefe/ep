package com.wndpo.ep.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class RFC {
    @Id
     @SequenceGenerator(
            name = "rfc_sequence",
            sequenceName = "rfc_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "rfc_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;
    private String city;
    @Enumerated(
            value = EnumType.STRING
    )
    private EP.Region region;
    private Integer year;
    private Integer quarter;
    private Integer cluster;
    private LocalDate DTStartDate;
    private LocalDate DTFinishedDate;
    private Boolean isDTFinished;
    private LocalDate analysisStartDate;
    private LocalDate analysisFinishedDate;
    private Boolean isAnalysisFinished;
    private LocalDate optStartDate;
    private LocalDate optFinishedDate;
    private Boolean isOptFinished;
    private Boolean isImprovementAchieved;
    private Float LTERSRPBefore;
    private Float LTERSRPAfter;
    private Float LTERSINRBefore;
    private Float LTESINRAfter;
    private Float UMTSRSCPBefore;
    private Float UMTSRSCPAfter;
    private Float UMTSECNOBefore;
    private Float UMTSECNOAfter;
    private Float GSMRXLEVBefore;
    private Float GSMRXLEVAfter;
    private Float GSMRXQUALBefore;
    private Float GSMRXQUALAfter;




}
