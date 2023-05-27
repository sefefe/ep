package com.wndpo.ep.repository;

import com.wndpo.ep.entity.ElectricalController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricalControllerRepository extends JpaRepository<ElectricalController,Long> , RevisionRepository<ElectricalController, Long, Long> {

    public ElectricalController findFirstByLocalECidAndAntennaAntennaId(Integer ecid, Long antennaId);

}
