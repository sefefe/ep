package com.wndpo.ep.repository;

import com.wndpo.ep.entity.EP;
import com.wndpo.ep.entity.RFC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RFCRepository extends JpaRepository<RFC,Integer> {
   @Query("select r from RFC r where r.region IN :regions ORDER BY r.year DESC, r.quarter DESC, r.region ASC, r.city ASC,r.cluster ASC")
   public List<RFC> getTreeData(@Param("regions") List<EP.Region> regions);
   public RFC findFirstByYearAndQuarterAndRegionAndCityAndCluster(Integer year,Integer quarter,EP.Region region,String city,Integer cluster );
}
