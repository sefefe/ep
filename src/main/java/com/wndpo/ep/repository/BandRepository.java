package com.wndpo.ep.repository;

import com.wndpo.ep.entity.EP;
import com.wndpo.ep.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface BandRepository extends JpaRepository<Band, Long>, RevisionRepository<Band, Long, Long> {
  public Band findFirstByNameAndElectricalControllerEcid(EP.Band name, Long ecid);
}
