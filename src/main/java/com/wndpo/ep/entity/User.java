package com.wndpo.ep.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "user",
        uniqueConstraints = @UniqueConstraint(
                name ="username_unique",
                columnNames = "userName"
        )
)
@Audited
public class User implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long userid;
    private String lastname;
    private String firstName;
    private String userName;
    private String password;
    private String email;
    private Date lastLoginDate;
    private Date joinDate;
    private String role;
    private boolean isActive;
    private boolean isNotLocked;
    private boolean isNotExpired;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private List<Access> access;

    public void addAccess(Access access){
        if(this.access == null){
            this.access = new ArrayList<>();
        }
        this.access.add(access);
    }

    public User(String lastname, String firstName, String userName, String password, String email, Date lastLoginDate, Date joinDate, String role, boolean isActive, boolean isNotLocked, boolean isNotExpired, List<Access> access) {
        this.lastname = lastname;
        this.firstName = firstName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.lastLoginDate = lastLoginDate;
        this.joinDate = joinDate;
        this.role = role;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.isNotExpired = isNotExpired;
        this.access = access;
    }
}
