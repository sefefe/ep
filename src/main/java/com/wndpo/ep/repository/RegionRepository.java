package com.wndpo.ep.repository;

import com.wndpo.ep.entity.EP;
import com.wndpo.ep.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer> {
    public Region findByRegionName(EP.Region rg);

}
