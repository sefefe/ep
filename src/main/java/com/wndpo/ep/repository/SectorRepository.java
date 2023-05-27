package com.wndpo.ep.repository;

import com.wndpo.ep.entity.Sector;
import com.wndpo.ep.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

public interface SectorRepository extends JpaRepository<Sector,Integer>, RevisionRepository<Sector, Integer, Long> {


    public Sector findFirstBySectorIdAndSiteSiteId(Integer sectorId, Integer siteId);
}
