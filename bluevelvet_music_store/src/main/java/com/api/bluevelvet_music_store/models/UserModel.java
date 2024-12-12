package com.api.bluevelvet_music_store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_USERS")
public class UserModel implements UserDetails,Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false ,unique = true)
    private String email;

    private String name;
    private String surname;
    private String password;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] userPhoto;
    private String contentType;
    @Column(columnDefinition = "TINYINT")
    private boolean enable;

    @ManyToMany
    @JoinTable( name = "TB_USERS_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<RoleModel> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhoto() {
        return ( this.contentType + ";base64," + Base64.getEncoder()
                .encodeToString(this.userPhoto) );
    }

    public void setUserPhoto(@NotBlank String userPhoto) {
        String[] imgSliced = userPhoto.split(",");
        this.contentType = imgSliced[0].split(";")[0];
        this.userPhoto = Base64.getDecoder().decode(imgSliced[1]);
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }
}
