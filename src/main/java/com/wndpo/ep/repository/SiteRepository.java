package com.wndpo.ep.repository;

import com.wndpo.ep.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site,Integer>, RevisionRepository<Site, Integer, Long> {


}
