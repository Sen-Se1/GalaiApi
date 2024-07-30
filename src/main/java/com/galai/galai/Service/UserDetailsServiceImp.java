package com.galai.galai.Service;

import com.galai.galai.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository UR;

    public UserDetailsServiceImp(UserRepository ur) {
        UR = ur;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UR.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Admin not found"));
    }
}
