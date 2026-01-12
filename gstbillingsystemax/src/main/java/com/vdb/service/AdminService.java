package com.vdb.service;

import com.vdb.entity.Admin;
import com.vdb.exception.RecordNotFoundException;
import com.vdb.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin signUp(Admin admin) {
        admin.setPass(passwordEncoder.encode(admin.getPass()));
        return adminRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByName(username).orElseThrow(() ->
                new RecordNotFoundException("admin is not found"));

        return new User(admin.getName(), admin.getPass(), new ArrayList<>());
    }
}
