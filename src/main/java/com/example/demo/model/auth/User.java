package com.example.demo.model.auth;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_data")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique=true)
    private String email;

    private String name;
    private Boolean isEnabled;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        Authority.getAuthorities(role).stream().map(role -> role.name()).forEach((e) -> authorities.add(new SimpleGrantedAuthority(e)) );
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Integer getId() {
        return userId;
    }


    public JSONObject userToUserDaoJson(){
        JSONObject userDaoToJsonObject = new JSONObject();
        userDaoToJsonObject.put("userId",this.getUserId());
        userDaoToJsonObject.put("name",this.getName());
        userDaoToJsonObject.put("email",this.getEmail());
        userDaoToJsonObject.put("isDeleted",!this.getIsEnabled());
        return userDaoToJsonObject;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", isEnabled=" + isEnabled +
                ", role=" + role +
                '}';
    }
}
