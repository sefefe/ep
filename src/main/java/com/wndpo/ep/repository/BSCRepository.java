package com.wndpo.ep.repository;

import com.wndpo.ep.entity.BSC;
import com.wndpo.ep.entity.EP;
import com.wndpo.ep.entity.RNC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BSCRepository extends JpaRepository<BSC, Integer> {
    public BSC findByBscLogicalName(EP.BSC bsc);
}
