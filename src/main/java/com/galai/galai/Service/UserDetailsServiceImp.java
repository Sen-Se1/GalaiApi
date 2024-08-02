package com.galai.galai.Service;

import com.galai.galai.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository UR;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UR.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Admin not found"));
    }
}
