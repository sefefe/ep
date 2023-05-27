package com.wndpo.ep.repository;

import com.wndpo.ep.entity.EP;
import com.wndpo.ep.entity.RNC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RNCRepository extends JpaRepository<RNC,Integer> {
    public RNC findByRncLogicalName(EP.RNC rnc);
    }
