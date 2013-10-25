package org.n3r.web.security;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.n3r.web.entity.Admin;
import org.n3r.web.service.AdminService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findAdmin(username);
        if (admin == null) {
            throw new UsernameNotFoundException("管理员[" + username + "]信息不正确!");
        }
        admin.setAuthorities(getGrantedAuthorities(admin));
        return admin;
    }

    private Set<GrantedAuthority> getGrantedAuthorities(Admin admin) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return grantedAuthorities;
    }
}
