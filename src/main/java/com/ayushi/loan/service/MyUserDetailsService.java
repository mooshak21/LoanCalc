package com.ayushi.loan.service;

import com.ayushi.loan.config.Users;
import com.ayushi.loan.config.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UsersRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Users user= userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Invalid username or password.");
        else
            return new User(user.getUserName(), user.getPassword(),user.isEnabled()==1?true:false,true,true,true, mapToGrantedAuthorities(user.getRoleList()),user.getFirstName(),user.getLastName());

    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName().toString()))
                .collect(Collectors.toList());
    }

}