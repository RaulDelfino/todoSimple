package com.RaulDelfino.todosimple.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.RaulDelfino.todosimple.models.enums.ProfileEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSpringSecurity implements UserDetails{
    
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecurity(Long id, String username, String password, Set<ProfileEnum> profileEnums) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = profileEnums.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // nenhuma conta seja expirada

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // a conta n esta travada

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    } // credencial expirada

    @Override
    public boolean isEnabled() {
        return true;
    } // nenhuma conta desativada
    
    public boolean hasRole(ProfileEnum profileEnum){
        return getAuthorities().contains(new SimpleGrantedAuthority(profileEnum.getDescription()));
    } // retorna se contem o perfil ou n Adm ou Comum.


}
