package com.wndpo.ep.service;

import com.wndpo.ep.DTO.*;
import com.wndpo.ep.entity.*;
import com.wndpo.ep.model.ImportResponse;
import com.wndpo.ep.model.StatusCode;
import com.wndpo.ep.model.UserRequest;
import com.wndpo.ep.model.UserResponse;
import com.wndpo.ep.repository.*;
import com.wndpo.ep.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wndpo.ep.model.StatusCode.*;

@Service
@RequiredArgsConstructor
public class EPService {

    private final SiteRepository siteRepository;

    private final ElectricalControllerRepository electricalControllerRepository;

    private final RNCRepository rncRepository;

    private final BSCRepository bscRepository;

    private final AntennaRepository antennaRepository;

    private final RegionRepository regionRepository;

    private final BandRepository bandRepository;

    private final PasswordEncoder passwordEncoder;

    private final Cell2Repository cellRepository;

    private final SectorRepository sectorRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JWTProvider jwtProvider;

    private final AccessRepository accessRepository;

    private final Mapper2 mapper;

    private final RFCRepository rfcRepository;
    public ImportExportDTO2 getSite(Integer id) {
        Cell2 cell = cellRepository.findAllFromSite(id).get(0);
            return cellRepository.getExportDataSingle(id, cell.getBand().getName(), cell.getCellId());
    }




    /*
    Create services
     */

    public ImportResponse createSite(ImportExportDTO2 ied, ImportResponse importResponse) {

        boolean exists = siteRepository.existsById(ied.getSiteId());
        Site newSite;
        if (!exists) {
            try {
                newSite = siteRepository.save(mapper.toRawSite(ied));
            } catch (Exception e) {

                importResponse.setSiteStatusCode(ERROR);
                return importResponse;
            }
            importResponse.setSiteStatusCode(StatusCode.CREATED);
            importResponse.getNe().setSite(newSite);
            return importResponse;
        } else
            return updateSite(ied, importResponse);


    }

    public ImportResponse createSector(Sector sector, ImportExportDTO2 ied, ImportResponse importResponse) {
        if (sector.getSectorId() == null)
            ied = findExistingData(ied.getSiteId(), null, ied.getRat(), ied.getCellId(), ied.getBand());

        Sector exists = sectorRepository.findFirstBySectorIdAndSiteSiteId(sector.getSectorId(), sector.getSite().getSiteId());

        Sector newsector;
        if (exists == null) {
            try {
                newsector = sectorRepository.save(sector);
            } catch (Exception e) {
                importResponse.setSectorStatusCode(ERROR);
                return importResponse;
            }
            importResponse.getNe().setSector(newsector);
            importResponse.setSectorStatusCode(CREATED);


        } else {
            return updateSector(ied, importResponse);

        }
        return importResponse;
    }


    public ImportResponse createAntenna(Antenna antenna, ImportExportDTO2 ied, ImportResponse importResponse) {
        //unique localAntennaId for every site and sector.

        Integer sectorId = antenna.getSector().getId();
        Antenna existingAntenna = antennaRepository.findFirstByLocalAntennaIdAndSectorId(antenna.getLocalAntennaId(), sectorId);
        Antenna newAntenna;
        if (existingAntenna == null) {
            try {
                newAntenna = antennaRepository.save(antenna);

            } catch (Exception e) {

                importResponse.setAntennaStatusCode(ERROR);
                return importResponse;
            }
            importResponse.getNe().setAntenna(newAntenna);
            importResponse.setAntennaStatusCode(StatusCode.CREATED);

            return importResponse;
        } else {
            ied.setAntennaId(existingAntenna.getAntennaId());
            return updateAntenna(ied, importResponse);
        }
    }

    public ImportResponse createElectricalController(ElectricalController ec, ImportExportDTO2 ied, ImportResponse importResponse) {

        //for every band there is a corresponding electrical controller except G900 and U900 share the same electrical controller

        ElectricalController existing = electricalControllerRepository.findFirstByLocalECidAndAntennaAntennaId(ec.getLocalECid(), ec.getAntenna().getAntennaId());
        ElectricalController newec;
        if (existing == null) {
            try {
                newec = electricalControllerRepository.save(ec);
            } catch (Exception e) {

                importResponse.setEcStatusCode(ERROR);
                return importResponse;
            }

            importResponse.getNe().setEc(newec);
            importResponse.setEcStatusCode(CREATED);
            return importResponse;
        } else {
            ied.setEcid(existing.getEcid());
            return updateElectricalController(ied, importResponse);
        }
    }

    public ImportResponse createBand(Band band, ImportExportDTO2 ied, ImportResponse importResponse) {
        //a sector would have only one sector antenna per electrical controller

        Band existing = bandRepository.findFirstByNameAndElectricalControllerEcid(band.getName(), band.getElectricalController().getEcid());
        Band newsa;
        if (existing == null) {
            try {
                newsa = bandRepository.save(band);
            } catch (Exception e) {


                importResponse.setBandStatusCode(ERROR);
                return importResponse;
            }
            importResponse.setBandStatusCode(CREATED);
            importResponse.getNe().setBand(newsa);
            return importResponse;


        } else {
            ied.setBandid(existing.getId());
            return updateBand(ied, importResponse);
        }
    }

    public ImportResponse createCell(Cell2 cell, ImportExportDTO2 ied, ImportResponse importResponse) {

        Cell2 exisingcell = cellRepository.findByRatAndSiteIdAndCellId(ied.getRat(), ied.getSiteId(), cell.getCellId());
//        Cell2 exisingcell = null;
//        if (exisingcells.size() > 0)
//            exisingcell = exisingcells.get(0);
        Cell2 newcell;

        if (exisingcell == null) {
            try {
                newcell = cellRepository.save(cell);
            } catch (Exception e) {

                importResponse.setCellStatusCode(ERROR);
                return importResponse;
            }
            importResponse.setCellStatusCode(CREATED);
            importResponse.getNe().setCell(newcell);
            return importResponse;
        } else {

            ied.setId(exisingcell.getId());
            return updateCell(ied, importResponse);
        }
    }

    public ResponseEntity<UserResponse> creatUser(UserRequest userRequest) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user").toUriString());
        User exitingUser = userRepository.findByUserName(userRequest.getUsername());
        User user = new User(null, null, userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()), null, null, null, userRequest.getRole(), true, true, true, null);

        if (exitingUser == null && !userRequest.isExisting()) {
            User newUser = userRepository.save(user);
            addAccess(userRequest);
            return new ResponseEntity<>(new UserResponse("USER CREATED", "User Created Successfully!"), HttpStatus.OK);
        } else if (exitingUser == null && userRequest.isExisting()) {
            //user existing
            return new ResponseEntity<>(new UserResponse("USER NOT_FOUND", "User not found!"), HttpStatus.OK);
        } else if (exitingUser != null && !userRequest.isExisting()) {
            return new ResponseEntity<>(new UserResponse("USER FOUND", "User already found!"), HttpStatus.OK);
        } else {
            List<String> cr = addAccess(userRequest);
            String up = "ACCESS GRANTED";
            if (!exitingUser.getRole().equals(userRequest.getRole())) {
                up = "USER UPDATED";
                exitingUser.setRole(userRequest.getRole());
                userRepository.save(exitingUser);
            }
            return new ResponseEntity<>(new UserResponse(up, cr.toString() + " acess added to user!"), HttpStatus.OK);
        }


    }

    /*
    Update Services
     */
    public ImportResponse updateSite(ImportExportDTO2 ied, ImportResponse importResponse) {
        Site updatedsite;
        try {
//            site.setRegion(regionRepository.findByRegionName(site.getRegion().getRegionName()));
//            if(site.getRnc()!=null)
//            site.setRnc(rncRepository.findByRncLogicalName(site.getRnc().getRncLogicalName()));
//         //   site
//            //site.setBsc(bscRepository.findByBscLogicalName(site.getBsc().getBscLogicalName()));
            updatedsite = siteRepository.save(mapper.toSite(ied, siteRepository.getById(ied.getSiteId())));
        } catch (Exception e) {

            importResponse.setSiteStatusCode(ERROR);
            return importResponse;
        }
        importResponse.setSiteStatusCode(StatusCode.UPDATED);
        importResponse.getNe().setSite(updatedsite);
        return importResponse;
    }

    public ImportResponse updateSector(ImportExportDTO2 ied, ImportResponse importResponse) {
        Sector oldSector = sectorRepository.findFirstBySectorIdAndSiteSiteId(ied.getSectorId(), ied.getSiteId());

        Sector updatedSector;
        try {
            updatedSector = sectorRepository.save(oldSector);
        } catch (Exception e) {

            importResponse.setSectorStatusCode(ERROR);
            return importResponse;
        }
        importResponse.getNe().setSector(updatedSector);
        importResponse.setSectorStatusCode(UPDATED);

        return importResponse;

    }

    public ImportResponse updateAntenna(ImportExportDTO2 ied, ImportResponse importResponse) {
        Antenna oldAntenna = antennaRepository.getById(ied.getAntennaId());
        Antenna antenna = mapper.toAntenna(ied, oldAntenna);
        try {

            antenna.setControllers(oldAntenna.getControllers());
            antenna.setSector(oldAntenna.getSector());
            antenna = antennaRepository.save(antenna);
        } catch (Exception e) {

            importResponse.setAntennaStatusCode(ERROR);
            return importResponse;
        }
        importResponse.getNe().setAntenna(antenna);
        importResponse.setAntennaStatusCode(StatusCode.UPDATED);

        return importResponse;
    }

    public ImportResponse updateElectricalController(ImportExportDTO2 ied, ImportResponse importResponse) {

        ElectricalController oldEC = electricalControllerRepository.getById(ied.getEcid());
        ElectricalController ec = mapper.toElectricalController(ied, oldEC);
        try {
            ec.setBands(oldEC.getBands());
            ec.setAntenna(oldEC.getAntenna());
            ec = electricalControllerRepository.save(ec);
        } catch (Exception e) {

            importResponse.setEcStatusCode(ERROR);
            return importResponse;

        }

        importResponse.getNe().setEc(ec);
        importResponse.setEcStatusCode(UPDATED);
        return importResponse;
    }

    public ImportResponse updateBand(ImportExportDTO2 ied, ImportResponse importResponse) {


        Band oldBand = bandRepository.getById(ied.getBandid());
        Band band = mapper.toBand(ied, oldBand);
        try {
            band.setElectricalController(oldBand.getElectricalController());
            band.setCells(oldBand.getCells());
            band = bandRepository.save(band);
        } catch (Exception e) {

            importResponse.setBandStatusCode(ERROR);
            return importResponse;
        }
        importResponse.getNe().setBand(band);
        importResponse.setBandStatusCode(UPDATED);
        return importResponse;

    }

    public ImportResponse updateCell(ImportExportDTO2 ied, ImportResponse importResponse) {


        Cell2 oldCell = cellRepository.getById(ied.getId());
        Cell2 cell = mapper.toCell(ied, oldCell);
        try {
            cell.setBand(oldCell.getBand());
            cell = cellRepository.save(cell);
        } catch (Exception e) {

            importResponse.setCellStatusCode(ERROR);
            return importResponse;
        }
        importResponse.getNe().setCell(cell);
        importResponse.setCellStatusCode(UPDATED);
        return importResponse;
    }

    public ResponseEntity<LoginResponse> authenticate(LoginRequest loginRequest) throws Exception {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        try {

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginResponse(false, null, null, null, null, false, true, true, true), HttpStatus.OK);
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        User user = userRepository.findByUserName(loginRequest.getUsername());

        boolean firstlogin = (user.getLastLoginDate() == null);

        if (!userDetails.isAccountNonExpired() || !userDetails.isEnabled() || !userDetails.isAccountNonLocked())
            return new ResponseEntity<>(new LoginResponse(true, null, null, null, null, false, userDetails.isEnabled(), userDetails.isAccountNonLocked(), userDetails.isAccountNonExpired()), HttpStatus.OK);

        if (firstlogin) {
            user.setLastLoginDate(new Date((System.currentTimeMillis())));

            userRepository.save(user);
        }
        //usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(loginRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        final String token = jwtProvider.generateToken(userDetails);

        List<Region> regions = getWriteAccessedRegions();

        List<String> regionString = new ArrayList<>();
        for (Region region : regions) {
            regionString.add(region.getRegionName().name());
        }


        return new ResponseEntity<LoginResponse>(new LoginResponse(true, user.getUserName(), token, regionString, userDetails.getAuthorities().toString(), firstlogin, userDetails.isEnabled(), userDetails.isAccountNonLocked(), userDetails.isAccountNonExpired()), HttpStatus.OK);


    }

    public ResponseEntity<List<TreeDTO>> getTreeData() {

        List<EP.Region> regions = new ArrayList<>();
        for (Region r : getAccessedRegions())
            regions.add(r.getRegionName());

        return new ResponseEntity<List<TreeDTO>>(cellRepository.getTreeData(regions), HttpStatus.OK);
    }

    private List<String> addAccess(UserRequest userRequest) {

        String[] regions = userRequest.getRegions();
        User user = userRepository.findByUserName(userRequest.getUsername());
        List<String> cr = new ArrayList<>();
        for (String regionName : regions) {

            Region region = regionRepository.findByRegionName(EP.Region.valueOf(regionName));
            Access exist = accessRepository.findFirstByRegionRegionNameAndUserUserName(region.getRegionName(), userRequest.getUsername());

            if (exist == null) {
                cr.add(regionName);
                Access access = new Access(user, region, userRequest.isHasWriteAccess());
                accessRepository.save(access);
            } else {

                if (exist.isHasWriteAccess() != userRequest.isHasWriteAccess()) {

                    cr.add(regionName);
                    exist.setHasWriteAccess(userRequest.isHasWriteAccess());
                    //  new Access(exist.getAccessId(), user, region, userRequest.isHaswriteaccess());
                    accessRepository.save(exist);
                }
            }

        }
        return cr;
    }

    public ResponseEntity<List<ImportExportDTO2>> export(List<String> regionsList, String rat) {
        ResponseEntity<List<ImportExportDTO2>> re = null;
        List<EP.Region> epr = new ArrayList<>();
        for (String region : regionsList) {
            epr.add(EP.Region.valueOf(region));
        }

        re = new ResponseEntity<>(cellRepository.getExportData(epr, EP.RAT.valueOf(rat)), HttpStatus.OK);

        return re;

    }

    private boolean hasAccess(Region region) {
        List<Region> regions = getAccessedRegions();
        return regions.contains(region);
    }

    private boolean hasWriteAccess(Region region) {

        List<Region> regions = getWriteAccessedRegions();

        return regions.contains(region);


    }

    private List<Region> getWriteAccessedRegions() {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Access> accesses = user.getAccess();
        List<Region> regions = new ArrayList<>();
        for (Access ac : accesses) {
            if (ac.isHasWriteAccess())
                regions.add(ac.getRegion());
        }
        return regions;

    }

    private List<Region> getAccessedRegions() {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Access> accesses = user.getAccess();
        List<Region> regions = new ArrayList<>();
        for (Access ac : accesses) {
            regions.add(ac.getRegion());
        }
        return regions;
    }

    public Cell2 getCell(Integer siteId, Integer cellId, EP.Band band) {

        Cell2 cell = cellRepository.findByCellIdAndBandAndSiteId(cellId, band, siteId).get(0);


        return cell;
    }


    public ResponseEntity<LoginResponse> changePassword(LoginRequest loginRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword());
        try {

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginResponse(false, username, null, null, null, false, true, true, true), HttpStatus.OK);
        }

        User user = userRepository.findByUserName(username);
        String pass = passwordEncoder.encode(loginRequest.getNewpassword());
        user.setPassword(pass);
        userRepository.save(user);
        return new ResponseEntity<>(new LoginResponse(true, username, null, null, null, true, true, true, true), HttpStatus.OK);
    }


    public ResponseEntity<List<ImportResponse>> importControllerData(ControllerDTO[] data) {
        initUser();
        String rat = (data[0].getBsc() == null) ? "UMTS" : "GSM";
        for (ControllerDTO cd : data) {
            String regionName = cd.getRegion();

            Region region = regionRepository.findByRegionName(EP.Region.valueOf(regionName));

            if (region == null) {
                region = new Region(null, EP.Region.valueOf(regionName), null, null, null);
            }
            if (rat.equals("UMTS")) {

                RNC rnc = rncRepository.findByRncLogicalName(EP.RNC.valueOf(cd.getRnc()));
                if (rnc == null) {
                    rnc = new RNC(null, cd.getName(), cd.getCity(), EP.RNC.valueOf(cd.getRnc()), null, null);
                }
                rncRepository.save(rnc);
//                if (region.getRncs() != null) {
//                    if (!region.getRncs().contains(rnc)) {
//                        region.addRNCs(rnc);
//                        regionRepository.save(region);
//                    }
//                } else {
//                    region.addRNCs(rnc);
//                    regionRepository.save(region);
//                }
            } else if (rat.equals("GSM")) {
                BSC bsc = bscRepository.findByBscLogicalName(EP.BSC.valueOf(cd.getBsc()));
                if (bsc == null) {
                    bsc = new BSC(null, cd.getName(), EP.BSC.valueOf(cd.getBsc()), cd.getCity(), null, null);
                }

                bscRepository.save(bsc);
                if (region.getBscs() != null) {
                    if (!region.getBscs().contains(bsc)) {
                        region.addBSCs(bsc);
                        regionRepository.save(region);
                    }
                } else {
                    region.addBSCs(bsc);
                    regionRepository.save(region);
                }


            }
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private void initUser() {
        User user = userRepository.findByUserName("sefefe");
        if (user == null) {
            java.util.List<Access> accesses = new ArrayList<>();

            user = new User("Aragie", "Sefefe", "sefefe", passwordEncoder.encode("sefefe"), "sefefe.aragie@ethiotelecom.et",
                    null, null, "Admin", true, true, true, null);
            userRepository.save(user);

            for (EP.Region r : EP.Region.values()) {

                Region region = new Region(null, r, null, null, null);
                regionRepository.save(region);
                Access access = new Access(user, region, true);
                accessRepository.save(access);
                accesses.add(access);

            }

            userRepository.save(user);
        }
    }


    public ImportExportDTO2 getAntenna(Integer siteId, Integer sectorId, EP.Band band) {
        Integer cellId = cellRepository.findFirstBySiteSectorAndBand(siteId, sectorId, band).get(0).getCellId();
        return cellRepository.getExportDataSingle(siteId, band, cellId);
    }

    private void deleteSector(Sector s) {
        for (Antenna a : s.getAntennas()) {
                   deleteAntenna(a);
        }
        sectorRepository.delete(s);
    }

    private void deleteAntenna(Antenna antenna) {

        for (ElectricalController e : antenna.getControllers())
            deleteElectricalController(e);

        antennaRepository.delete(antenna);
    }

    private void deleteElectricalController(ElectricalController ec) {

        for (Band b : ec.getBands())
            deleteBand(b);
        electricalControllerRepository.delete(ec);

    }

    private void deleteBand(Band band) {
        for (Cell2 c : band.getCells())
            cellRepository.delete(c);
        bandRepository.delete(band);

    }

    private void deleteSite(Site site) {

        for (Sector s : site.getSectors())
            deleteSector(s);
        siteRepository.delete(site);


    }

    public List<ImportResponse> checkForUpdateOrNewImport(ImportExportDTO2[] ieds) {

        ImportExportDTO2 ed = null;

        List<ImportResponse> importResponseList = new ArrayList<>();

        for (ImportExportDTO2 ied : ieds) {

            ImportResponse ir = new ImportResponse();
            if (!siteRepository.existsById(ied.getSiteId()))
                ir.setSiteStatusCode(CREATED);

            else
                ir.setSiteStatusCode(UPDATED);

            Integer sectorId = getSectorId(ied.getSectorId(), ied.getCellId());

            ed = getFullRow(ied.getSiteId(), sectorId, ied.getBand());


            if (ed != null) {
                ir.setRegion(ed.getRegionName());
                ir.setAntennaStatusCode(UPDATED);
                ir.setBandStatusCode(UPDATED);

            }
            else{
                ir.setAntennaStatusCode(CREATED);
                ir.setBandStatusCode(CREATED);
            }

            if (ied.getCellId() != null) {

                Cell2 cell = cellRepository.findByRatAndSiteIdAndCellId(ied.getRat(),ied.getSiteId(),ied.getCellId());


                //Conflict one misplaced sector
                if(ied.getSectorId()!=null&&getSectorId(null,ied.getCellId())!=ied.getSectorId())
                    ir.setStatus(CELL_AND_SECTOR_MISMATCH);
                else{
                  if(cell!=null){

                     if(cell.getBand().getName()!=ied.getBand())
                      ir.setStatus(BAND_AND_CELL_ID_DID_NOT_MATCH);
                     else
                         ir.setCellStatusCode(UPDATED);
                  }
                  else {

                      if(ed==null||(ed!=null&&!ed.getBand().name().equals("U2100")&&bandRepository.getById(ed.getBandid()).getCells().size()==0))
                      ir.setCellStatusCode(CREATED);
                      else
                          ir.setStatus(MORE_THAN_ONE_CELL_PER_BAND);

                  }
                }

            }


            importResponseList.add(ir);
        }

        return importResponseList;


    }



    public List<ImportResponse> deleteData(ImportExportDTO2[] ieds, String deleteType) {

        List<ImportResponse> importResponseList = new ArrayList<>();
        ImportExportDTO2 ed = null;
        for (ImportExportDTO2 ied : ieds) {
            ImportResponse importResponse = new ImportResponse();
            //delete site
            if (deleteType.equals("site")) {
                if (siteRepository.existsById(ied.getSiteId())) {
                    deleteSite(siteRepository.getById(ied.getSiteId()));
                    importResponse.setStatus(DELETED);
                } else
                    importResponse.setStatus(NOT_FOUND);

            }
            else if (deleteType.equals("band")) {
                if (getSectorId(ied.getSectorId(), ied.getCellId()) == null || ied.getBand() == null)
                    importResponse.setStatus(ERROR);
                else {
                    Cell2 cell = findFirstBySiteIdAndSectorIdAndBand(ied.getSiteId(), getSectorId(ied.getSectorId(), ied.getCellId()), ied.getBand());
                    if (cell != null) {
                        ed = cellRepository.getExportDataSingle(ied.getSiteId(), ied.getBand(), cell.getCellId());
                         Antenna a = cell.getBand().getElectricalController().getAntenna();
                          if (cell.getBand().getElectricalController().getBands().size() == 1 && a.getControllers().size() == 1) {
                            if (hasOnlyOneAntenna(ed.getSiteId())) {
                                importResponse.setStatus(DELETED);
                                deleteSite(siteRepository.getById(ed.getSiteId()));
                            } else {
                                List<Antenna> as = a.getSector().getAntennas();
                                as.remove(a);
                                a.getSector().setAntennas(as);
                                importResponse.setStatus(DELETED);
                                deleteAntenna(a);

                            }
                        }else {
                             List<Band> bs = bandRepository.getById(ed.getBandid()).getElectricalController().getBands();
                             bs.remove(bandRepository.getById(ed.getBandid()));
                             bandRepository.getById(ed.getBandid()).getElectricalController().setBands(bs);
                             importResponse.setStatus(DELETED);
                             deleteBand(bandRepository.getById(ed.getBandid()));
                        }
                        } else
                            importResponse.setStatus(NOT_FOUND);

                }
            } else if (deleteType.equals("cell")) {
                if (ied.getCellId() != null) {
                    Cell2 cell = cellRepository.findByRatAndSiteIdAndCellId(ied.getRat(), ied.getSiteId(), ied.getCellId());
                    Band band = null;
                    if (cell != null) {

                        Antenna a  = cell.getBand().getElectricalController().getAntenna();
                        if (cell.getBand().getCells().size()==1&&cell.getBand().getElectricalController().getBands().size() == 1 && a.getControllers().size() == 1) {
                            if (hasOnlyOneAntenna(ied.getSiteId())) {
                                importResponse.setStatus(DELETED);
                                deleteSite(siteRepository.getById(ied.getSiteId()));
                            } else {
                                List<Antenna> as = a.getSector().getAntennas();
                                as.remove(a);
                                a.getSector().setAntennas(as);
                                importResponse.setStatus(DELETED);
                                deleteAntenna(a);

                            }
                        }
                       else {
                           if(cell.getBand().getCells().size()==1){
                               List<Band> bs = cell.getBand().getElectricalController().getBands();
                               bs.remove(cell.getBand());
                               cell.getBand().getElectricalController().setBands(bs);
                               importResponse.setStatus(DELETED);
                               deleteBand(cell.getBand());
                           }
                         else {
                               cellRepository.delete(cell);
                               importResponse.setStatus(DELETED);
                           }
                         }
                    } else
                        importResponse.setStatus(NOT_FOUND);
                } else importResponse.setStatus(ERROR);

            }
            importResponseList.add(importResponse);
        }



        return importResponseList;

    }

    private boolean hasOnlyOneAntenna(Integer siteId) {
        Integer count = 0;
        for(Sector s:siteRepository.getById(siteId).getSectors())
            count += s.getAntennas().size();
        return count<=1;
    }






    private Integer getSectorId(Integer sectorId, Integer cellId) {
        if (sectorId == null && cellId != null) {
            String cs = Integer.toString(cellId);
            sectorId = Math.floorMod(Integer.valueOf(cs.substring(cs.length() - 1)), 3);
            if (sectorId == 0)
                sectorId = 3;
        }
        return sectorId;
    }

    public ImportExportDTO2 findExistingData(Integer siteId, Integer sectorId, EP.RAT rat, Integer cellId, EP.Band band) {

        if (cellId != null && rat != null) {
            Cell2 cell = cellRepository.findByRatAndSiteIdAndCellId(rat, siteId, cellId);
            if (cell != null)
                band = cell.getBand().getName();

        } else if (cellId == null) {
            if (band != null && sectorId != null) {
                List<Cell2> cells = cellRepository.findFirstBySiteSectorAndBand(siteId, sectorId, band);
                if (cells.size() > 0)
                    cellId = cells.get(0).getCellId();
            }

        }
        return cellRepository.getExportDataSingle(siteId, band, cellId);
    }

    private ImportExportDTO2 getFullRow(Integer siteId, Integer sectorId, EP.Band band) {

        Cell2 cell = findFirstBySiteIdAndSectorIdAndBand(siteId, sectorId, band);
        ImportExportDTO2 lai = null;

        if (cell != null)
            lai = cellRepository.getExportDataSingle(siteId, band, cell.getCellId());


        return lai;
    }

    private Cell2 findFirstBySiteIdAndSectorIdAndBand(Integer siteId, Integer sectorId, EP.Band band) {
        if (sectorId != null && band != null) {
            List<Cell2> cells = cellRepository.findFirstBySiteSectorAndBand(siteId, sectorId, band);
            if (cells.size() > 0)
                return cells.get(0);
        }
        return null;


    }

    public List<RFC> getRFCTreeData() {
        List<EP.Region> regions = new ArrayList<>();
        for (Region r : getAccessedRegions())
            regions.add(r.getRegionName());
        return rfcRepository.getTreeData(regions);
    }

    public RFC getRFC(Integer rfcId) {
        return rfcRepository.getById(rfcId);
    }
    public ResponseEntity<RFC> newRFC(RFC rfc){
        Boolean exists =  rfcRepository.findFirstByYearAndQuarterAndRegionAndCityAndCluster(rfc.getYear(),rfc.getQuarter(),rfc.getRegion(),rfc.getCity(),rfc.getCluster())!=null;
        if(!exists)
        return  new ResponseEntity<>(rfcRepository.save(rfc),HttpStatus.OK);
        else
        return  new ResponseEntity<>(null,HttpStatus.FOUND);
    }

    public void deleteRFC(Integer id) {
        rfcRepository.delete(rfcRepository.getById(id));
    }

    public ResponseEntity<RFC> updateRFC(RFC rfc) {
        return new ResponseEntity<>(rfcRepository.save(rfc),HttpStatus.OK);
    }
}
