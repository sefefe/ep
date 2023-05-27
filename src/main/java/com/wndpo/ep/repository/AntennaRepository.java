package com.wndpo.ep.repository;

import com.wndpo.ep.entity.Antenna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

public interface AntennaRepository extends JpaRepository<Antenna, Long> , RevisionRepository<Antenna, Long, Long> {

  Antenna findFirstByLocalAntennaIdAndSectorId(Integer local_antennaId, Integer Id);

  //@Query("select a from Antenna a right join  right join a.sector sec  where  a.localAntennaId=:local_antennaId and sec.sectorId=:sectorId")

  Antenna findFirstByLocalAntennaIdAndSectorSectorId(Integer local_antennaId, Integer sectorId);
}
