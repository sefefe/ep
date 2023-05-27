package com.wndpo.ep.revision;


import com.wndpo.ep.entity.User;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;


public class UserRevisionListener implements RevisionListener {
    public void newRevision(Object revisionEntity) {
       UserRevEntity userRevEntity = (UserRevEntity) revisionEntity;
       String username = SecurityContextHolder.getContext().getAuthentication().getName();

        userRevEntity.setUsername(username);

    }
}