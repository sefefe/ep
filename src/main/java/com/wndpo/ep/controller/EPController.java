package com.wndpo.ep.controller;

import com.wndpo.ep.DTO.*;
import com.wndpo.ep.entity.Cell2;
import com.wndpo.ep.entity.EP;
import com.wndpo.ep.entity.RFC;
import com.wndpo.ep.model.ImportResponse;
import com.wndpo.ep.model.UserResponse;
import com.wndpo.ep.service.EPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.wndpo.ep.model.StatusCode.ERROR;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EPController {



    private final EPService epService;


    private final Mapper2 mapper;

    @GetMapping(path = "/site/{siteId}")
    public ImportExportDTO2 getSite(@PathVariable Integer siteId) {
        return this.epService.getSite(siteId);
    }
    @GetMapping(path = "/rfc/{rfcId}")
    public RFC getRFC(@PathVariable Integer rfcId) {
        return this.epService.getRFC(rfcId);
    }


    @PostMapping(path = "/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) throws Exception {
        return epService.authenticate(loginRequest);

    }
    @PostMapping(path = "/changepass")
    public ResponseEntity<LoginResponse> changePassword(@RequestBody LoginRequest loginRequest) throws Exception {
        return epService.changePassword(loginRequest);

    }

    @PutMapping(path = "/deleteData/{deleteType}")

     public ResponseEntity<List<ImportResponse>> deleteData(@RequestBody ImportExportDTO2[] IEDS,@PathVariable String deleteType) {
      return new ResponseEntity<>(epService.deleteData(IEDS,deleteType),HttpStatus.OK);

    }

    @PostMapping(path = "/importData")
    @Transactional
    public ResponseEntity<List<ImportResponse>> importData(@RequestBody ImportExportDTO2[] IEDS) {
            Integer index = 0;

        List<ImportResponse> importResponseList = new ArrayList<>();
        for(ImportExportDTO2 IED: IEDS) {
             ImportResponse importResponse = new ImportResponse();
             boolean onlysite = (IED.getCellId()==null&&IED.getBand()==null);
             importResponse = epService.createSite(IED,importResponse);
          if(importResponse.getSiteStatusCode()!=ERROR&&!onlysite) {
              if(IED.getLocalAntennaId()==null||IED.getBand()==null||IED.getLocalECid()==null||IED.getSectorId()==null)
                  IED =  convertUpdate(IED);

                 importResponse = epService.createSector(mapper.toRawSector(importResponse.getNe().getSite(),IED),IED, importResponse);
                 if(importResponse.getSectorStatusCode()!=ERROR) {
                 importResponse = epService.createAntenna(mapper.toRawAntenna(importResponse.getNe().getSector(),IED),IED, importResponse);
                  if (importResponse.getAntennaStatusCode() != ERROR) {
                    importResponse = epService.createElectricalController(mapper.toRawElectricalController(importResponse.getNe().getAntenna(),IED),IED, importResponse);

                  if (importResponse.getEcStatusCode() != ERROR) {
                        importResponse = epService.createBand(mapper.toRawBand(importResponse.getNe().getEc(),IED),IED, importResponse);
                    if (importResponse.getBandStatusCode() != ERROR) {

                               if(IED.getCellId()!=null)
                                epService.createCell(mapper.toRawCell(importResponse.getNe().getBand(),IED),IED, importResponse);

                        }
                    }
               }
              }

         }

            importResponse.setRowNumber(index++);
            importResponseList.add(importResponse);
        }
            return new ResponseEntity<>(importResponseList,HttpStatus.OK);

    }
    @PostMapping(path = "/importControllerData")
    @Transactional
    public ResponseEntity<List<ImportResponse>> importControllerData(@RequestBody ControllerDTO[] data) {

        return epService.importControllerData(data);


    }
    private ImportExportDTO2 convertUpdate(ImportExportDTO2 IED){

            ImportExportDTO2 ed = epService.findExistingData(IED.getSiteId(), IED.getSectorId(), IED.getRat(), IED.getCellId(), IED.getBand());
            if(ed!=null) {
                if (IED.getLocalAntennaId() == null) IED.setLocalAntennaId(ed.getLocalAntennaId());
                if (IED.getLocalECid() == null) IED.setLocalECid(ed.getLocalECid());
                if (IED.getSectorId() == null) IED.setSectorId(ed.getSectorId());
                if (IED.getBand() == null) IED.setBand(ed.getBand());
            }

       return IED;
    }

    @PutMapping(path = "/site/{siteId}")


    public ImportResponse updateSite(@RequestBody ImportExportDTO2 siteDTO) {

        return epService.updateSite(siteDTO,new ImportResponse());
    }
     @PostMapping(path="/checkForUpdateOrNewImport")
     public ResponseEntity<List<ImportResponse>> checkForUpdateOrNewImport(@RequestBody ImportExportDTO2[] IEDS){

        return new ResponseEntity<>(epService.checkForUpdateOrNewImport(IEDS),HttpStatus.OK);

     }
    @GetMapping(path = "/Antenna/")
    public ImportExportDTO2 getAntenna(@RequestParam(name = "siteId") Integer siteId, @RequestParam(name = "sectorId") Integer sectorId, @RequestParam(name = "band") EP.Band band) {

        return epService.getAntenna(siteId, sectorId, band);
    }

    @GetMapping(path = "/Cell/")
    public Cell2 getCell(@RequestParam(name = "siteId") Integer siteId, @RequestParam(name = "cellId") Integer cellId, @RequestParam(name = "band") EP.Band band) {

       return epService.getCell(siteId, cellId, band);
    }

    @PutMapping(path = "/Antenna")
    @Transactional
    public ResponseEntity<List<ImportResponse>> updateAntenna(@RequestBody ImportExportDTO2 antennaDTO) {
        List<ImportResponse> importResponseList = new ArrayList<>();
        ImportResponse aimportResponse = epService.updateAntenna(antennaDTO, new ImportResponse());
        ImportResponse ecimportResponse = epService.updateElectricalController(antennaDTO, new ImportResponse());
        ImportResponse bimportResponse = epService.updateBand(antennaDTO, new ImportResponse());
        importResponseList.add(aimportResponse);
        importResponseList.add(ecimportResponse);
        importResponseList.add(bimportResponse);
        return new ResponseEntity<>(importResponseList,HttpStatus.OK);
        }


    @PutMapping(path = "/EC")
    public ResponseEntity<ImportResponse> updateElectricalController(@RequestBody ImportExportDTO2 electricalController) {
        return new ResponseEntity<>(epService.updateElectricalController(electricalController,new ImportResponse()),HttpStatus.OK);
    }


    @PostMapping(path = "/user")
    public ResponseEntity<UserResponse> addNewUser(@RequestBody com.wndpo.ep.model.UserRequest userRequest) {
        return epService.creatUser(userRequest);

    }
    @GetMapping(path = "/treeData")
    public ResponseEntity<List<TreeDTO>> getTreeData() {
        return epService.getTreeData();

    }



    @GetMapping(path="/export")
    public ResponseEntity<List<ImportExportDTO2>> export(@RequestParam String regions,@RequestParam String rat){
        String[] exportregions = regions.split(",");
        List<String> regionsList = Arrays.stream(exportregions).collect(Collectors.toList());

        return epService.export(regionsList,rat);

    }

    @PutMapping(path = "/Cell")
    @Transactional
    public ResponseEntity<ImportResponse> updateCell(@RequestBody ImportExportDTO2 cellDTO) {

      return new ResponseEntity<>(epService.updateCell(cellDTO,new ImportResponse()),HttpStatus.OK);
    }
    @PutMapping(path = "/saverfc")
    @Transactional
    public ResponseEntity<RFC> updateRFC(@RequestBody RFC rfc) {
        return epService.updateRFC(rfc);
    }

    @GetMapping(path = "/rfcTreeData")
    public List<RFC> getRFCTreeData(){
        return epService.getRFCTreeData();
    }

    @PostMapping(path = "/newcity")
    public ResponseEntity<RFC> newCity(@RequestBody RFC rfc){
          return epService.newRFC(rfc);
    }

    @DeleteMapping(path = "/deleteRFC/{id}")
    public void deleteRFC(@PathVariable Integer id){
         epService.deleteRFC(id);
    }
}


