package com.wndpo.ep.repository;

import com.wndpo.ep.DTO.ImportExportDTO2;
import com.wndpo.ep.DTO.TreeDTO;
import com.wndpo.ep.entity.Cell2;
import com.wndpo.ep.entity.EP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Cell2Repository extends JpaRepository<Cell2,Long>, RevisionRepository<Cell2, Long, Long> {

    @Query("select new com.wndpo.ep.DTO.ImportExportDTO2(" +
            "s.siteId,s.region.regionName,s.adminRegion,s.city,s.wereda,s.siteClass,"+
            "s.rnc.rncLogicalName,s.bsc.bscLogicalName,s.carrierConfiguration,s.siteName,s.longitude,s.latitude,s.roofTop,s.outdoor"+
            ",a.antennaId,a.localAntennaId,sec.sectorId,a.antennaModel,a.height,a.azimuth,a.mechanicalDownTilt,a.sectors," +
            "e.ecid,e.localECid,e.electricalDownTilt, e.HorizontalBeamWidth, e.VerticalBeamWidth,b.id,b.name,b.rat,c.id,c.cellId,c.lac,c.rac," +
            "c.cellName,c.active,c.bcch,c.bsic,c.tch,c.psc,c.downlinkUarfcn,c.pcpichPower,c.maxPower,c.tac,c.bandWidth,c.pci,c.downlinkEarfcn,s.vendor,s.description,s.towerHeight) from Cell2 c  " +
            "left join c.band b " +
            "left join b.electricalController e " +
            "left join e.antenna a " +
            "left join a.sector sec " +
            "left join sec.site s " +
            "where s.region.regionName IN :regions and b.rat=:rat")

    public List<ImportExportDTO2> getExportData(@Param("regions") List<EP.Region> regions,@Param("rat")EP.RAT rat);

    @Query("select new com.wndpo.ep.DTO.ImportExportDTO2(" +
            "s.siteId,s.region.regionName,s.adminRegion,s.city,s.wereda,s.siteClass,"+
            "s.rnc.rncLogicalName,s.bsc.bscLogicalName,s.carrierConfiguration,s.siteName,s.longitude,s.latitude,s.roofTop,s.outdoor,"+
            "a.antennaId,a.localAntennaId,sec.sectorId,a.antennaModel,a.height,a.azimuth,a.mechanicalDownTilt,a.sectors," +
            "e.ecid,e.localECid,e.electricalDownTilt, e.HorizontalBeamWidth, e.VerticalBeamWidth,b.id,b.name,b.rat,c.id,c.cellId,c.lac,c.rac," +
            "c.cellName,c.active,c.bcch,c.bsic,c.tch,c.psc,c.downlinkUarfcn,c.pcpichPower,c.maxPower,c.tac,c.bandWidth,c.pci,c.downlinkEarfcn,s.vendor,s.description,s.towerHeight) from Cell2 c  " +
            "left join c.band b " +
            "left join b.electricalController e " +
            "left join e.antenna a " +
            "left join a.sector sec " +
            "left join sec.site s " +
            "where s.siteId=:siteId and c.cellId=:cellId and b.name=:band")

    public ImportExportDTO2 getExportDataSingle(@Param("siteId")Integer siteId, @Param("band")EP.Band band, @Param("cellId")Integer cellId);


    @Query("select c from Cell2 c right join c.band b right join b.electricalController e right join e.antenna a right join a.sector sec right join sec.site s  where c.cellId=:cellId and b.name=:band and s.siteId=:siteId ")
    public List<Cell2> findByCellIdAndBandAndSiteId(Integer cellId, EP.Band band, Integer siteId);


//    @Query("select c from Cell2 c right join c.band b right join b.electricalController e right join e.antenna a right join a.sector sec right join sec.site s  where  b.name=:band and s.siteId=:siteId ")
//    public List<Cell2> findFirstByBandAndSiteId(EP.Band band,Integer siteId);

    @Query("select new com.wndpo.ep.DTO.TreeDTO(s.region.regionName,s.siteId,sec.sectorId,b.name,c.cellId) from Cell2 c  right join c.band b right join b.electricalController e right join e.antenna a right join a.sector sec right join sec.site s where s.region.regionName IN :regions ORDER BY s.region.regionName,s.siteId ASC, sec.sectorId ASC, b.name ASC, c.cellId ASC")
    public List<TreeDTO> getTreeData(List<EP.Region> regions);

    @Query("select c from Cell2 c right join c.band b right join b.electricalController e right join e.antenna a right join a.sector sec right join sec.site s  where  s.siteId=:siteId ")
    public List<Cell2> findAllFromSite(@Param("siteId")Integer siteId);

    @Query("select c from Cell2 c right join c.band b right join b.electricalController e right join e.antenna a right join a.sector sec right join sec.site s  where  b.name=:band and sec.sectorId=:sectorId and s.siteId=:siteId ")
    public List<Cell2> findFirstBySiteSectorAndBand(@Param("siteId")Integer siteId, @Param("sectorId")Integer sectorId,@Param("band")EP.Band band);

    @Query("select c from Cell2 c right join c.band b right join b.electricalController e right join e.antenna a right join a.sector sec right join sec.site s  where  b.rat=:rat and s.siteId=:siteId and c.cellId=:cellId")
    public Cell2 findByRatAndSiteIdAndCellId(@Param("rat") EP.RAT rat,@Param("siteId")Integer siteId,@Param("cellId") Integer cellId);



}
