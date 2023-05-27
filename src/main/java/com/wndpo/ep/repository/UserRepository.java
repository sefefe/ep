package com.wndpo.ep.repository;

import com.wndpo.ep.entity.Site;
import com.wndpo.ep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;


public interface UserRepository extends JpaRepository<User, Long>,RevisionRepository<User, Long, Long> {
    public User findByUserName(String userName);
}
