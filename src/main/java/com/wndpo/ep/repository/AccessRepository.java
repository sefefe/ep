package com.wndpo.ep.repository;

import com.wndpo.ep.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access,Long>, RevisionRepository<Access, Long, Long> {

public Access findFirstByRegionRegionNameAndUserUserName(EP.Region region,String username);

}
