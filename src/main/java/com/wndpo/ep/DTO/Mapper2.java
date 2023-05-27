package com.wndpo.ep.DTO;

import com.wndpo.ep.entity.*;
import com.wndpo.ep.repository.BSCRepository;
import com.wndpo.ep.repository.BandRepository;
import com.wndpo.ep.repository.RNCRepository;
import com.wndpo.ep.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper2 {
    private final RegionRepository regionRepository;
    private final RNCRepository rncRepository;
    private final BSCRepository bscRepository;


    public Sector toRawSector(Site site, ImportExportDTO2 ied) {
        return new Sector(ied.getSectorId(), site, null);
    }

    public Antenna toRawAntenna(Sector sector, ImportExportDTO2 ied) {
        return new Antenna(ied.getAntennaId(),
                sector,
                null,
                ied.getLocalAntennaId(),
                ied.getAntennaModel(),
                ied.getHeight(),
                ied.getAzimuth(),
                ied.getMechanicalDownTilt(),
                ied.getSectors());

    }

    public Antenna toAntenna(ImportExportDTO2 ied, Antenna ed) {
        return new Antenna(
                ied.getAntennaId(),
                null,
                null,
                ied.getLocalAntennaId(),
                (ied.getAntennaModel() != null) ? ied.getAntennaModel() : ed.getAntennaModel(),
                (ied.getHeight() != null) ? ied.getHeight() : ed.getHeight(),
                (ied.getAzimuth() != null) ? ied.getAzimuth() : ed.getAzimuth(),
                (ied.getMechanicalDownTilt() != null) ? ied.getMechanicalDownTilt() : ed.getMechanicalDownTilt(),
                (ied.getSectors() != null) ? ied.getSectors() : ed.getSectors());

    }

    public ElectricalController toRawElectricalController(Antenna antenna, ImportExportDTO2 ied) {
        return new ElectricalController(antenna, ied.getLocalECid(), null, ied.getElectricalDownTilt(), ied.getHorizontalBeamWidth(), ied.getVerticalBeamWidth());
    }

    public ElectricalController toElectricalController(ImportExportDTO2 ied, ElectricalController ec) {
        return new ElectricalController(

                ied.getEcid(),
                null,
                ied.getLocalECid(),
                null,
                (ied.getElectricalDownTilt() != null) ? ied.getElectricalDownTilt() : ec.getElectricalDownTilt(),
                (ied.getHorizontalBeamWidth() != null) ? ied.getHorizontalBeamWidth() : ec.getHorizontalBeamWidth(),
                (ied.getVerticalBeamWidth() != null) ? ied.getVerticalBeamWidth() : ec.getVerticalBeamWidth());
    }

    public Band toRawBand(ElectricalController ec, ImportExportDTO2 ied) {
        return new Band(ied.getBandid(), ied.getBand(), ied.getRat(), ec, null);
    }

    public Band toBand(ImportExportDTO2 ied, Band band) {
        return new Band(
                ied.getBandid(),
                ied.getBand(),
                ied.getRat(), null, null);
    }

    public Site toSite(ImportExportDTO2 ied, Site ed) {

        return new Site(
                ied.getSiteId(),
                (ied.getRegionName() != null)?regionRepository.findByRegionName(ied.getRegionName()):ed.getRegion(),
                (ied.getAdminRegion() != null) ? ied.getAdminRegion() : ed.getAdminRegion(),
                (ied.getCity() != null) ? ied.getCity() : ed.getCity(),
                (ied.getWereda() != null) ? ied.getWereda() : ed.getWereda(),
                (ied.getSiteClass() != null) ? ied.getSiteClass() : ed.getSiteClass(),
                (ied.getRnc()!=null)?rncRepository.findByRncLogicalName(ied.getRnc()):ed.getRnc(),
                (ied.getBsc()!=null)?bscRepository.findByBscLogicalName(ied.getBsc()):ed.getBsc(),
                (ied.getCarrierConfiguration() != null)?ied.getCarrierConfiguration() : ed.getCarrierConfiguration(),
                (ied.getSiteName() != null) ? ied.getSiteName() : ed.getSiteName(),
                (ied.getLongitude() != null) ? ied.getLongitude() : ed.getLongitude(),
                (ied.getLatitude() != null) ? ied.getLatitude() : ed.getLatitude(),
                (ied.getRoofTop()!=null) ? ied.getRoofTop() : ed.getRoofTop(),
                (ied.getVendor() != null) ? ied.getVendor() : ed.getVendor(),
                (ied.getOutdoor() != null) ? ied.getOutdoor() : ed.getOutdoor(),
                (ied.getDescription() != null) ? ied.getDescription() : ed.getDescription(),
                (ied.getTowerHeight() != null) ? ied.getTowerHeight() : ed.getTowerHeight());
    }

    public Cell2 toCell(ImportExportDTO2 cellDTO, Cell2 ed) {
        return new Cell2(
                cellDTO.getId(),
                null,
                (cellDTO.getCellId() != null) ? cellDTO.getCellId() : ed.getCellId(),
                (cellDTO.getCellName() != null) ? cellDTO.getCellName() : ed.getCellName(),
                (cellDTO.getActive() != null) ? cellDTO.getActive() : ed.getActive(),
                (cellDTO.getBcch() != null) ? cellDTO.getBcch() : ed.getBcch(),
                (cellDTO.getBsic() != null) ? cellDTO.getBsic() : ed.getBsic(),
                (cellDTO.getTch() != null) ? cellDTO.getTch() : ed.getTch(),
                (cellDTO.getRac() != null) ? cellDTO.getRac() : ed.getRac(),
                (cellDTO.getLac() != null) ? cellDTO.getLac() : ed.getLac(),
                (cellDTO.getTac() != null) ? cellDTO.getTac() : cellDTO.getTac(),
                (cellDTO.getPsc() != null) ? cellDTO.getPsc() : ed.getPsc(),
                (cellDTO.getDownlinkUarfcn() != null) ? cellDTO.getDownlinkUarfcn() : ed.getDownlinkUarfcn(),
                (cellDTO.getPcpichPower() != null) ? cellDTO.getPcpichPower() : ed.getPcpichPower(),
                (cellDTO.getMaxPower() != null) ? cellDTO.getMaxPower() : ed.getMaxPower(),
                (cellDTO.getPci() != null) ? cellDTO.getPci() : ed.getPci(),
                (cellDTO.getDownlinkEarfcn() != null) ? cellDTO.getDownlinkEarfcn() : ed.getDownlinkEarfcn(),
                (cellDTO.getBandWidth() != null) ? cellDTO.getBandWidth() : cellDTO.getBandWidth());
    }


    public Site toRawSite(ImportExportDTO2 siteDTO) {
        return new Site(

                siteDTO.getSiteId(),
                (siteDTO.getRegionName() != null) ? regionRepository.findByRegionName(siteDTO.getRegionName()) : null,
                siteDTO.getAdminRegion(),
                siteDTO.getCity(),
                siteDTO.getWereda(),
                siteDTO.getSiteClass(),
                (siteDTO.getRnc() != null) ? rncRepository.findByRncLogicalName(siteDTO.getRnc()) : null,
                (siteDTO.getBsc() != null) ? bscRepository.findByBscLogicalName(siteDTO.getBsc()) : null,
                siteDTO.getCarrierConfiguration(),
                siteDTO.getSiteName(),
                siteDTO.getLongitude(),
                siteDTO.getLatitude(),
                siteDTO.getRoofTop(),
                siteDTO.getVendor(),
                siteDTO.getOutdoor(),
                siteDTO.getDescription(),
                siteDTO.getTowerHeight());
    }

    public Cell2 toRawCell(Band band, ImportExportDTO2 ied) {
        return new Cell2(ied.getId(),
                band,
                ied.getCellId(),
                ied.getCellName(),
                ied.getActive(),
                ied.getBcch(),
                ied.getBsic(),
                ied.getTch(),
                ied.getRac(),
                ied.getLac(),
                ied.getTac(),
                ied.getPsc(),
                ied.getDownlinkUarfcn(),
                ied.getPcpichPower(),
                ied.getMaxPower(),
                ied.getPci(),
                ied.getDownlinkEarfcn(),
                ied.getBandWidth());

    }
}

