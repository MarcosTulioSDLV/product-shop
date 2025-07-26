package com.springboot.product_shop.security;

import com.springboot.product_shop.models.UserEntity;
import com.springboot.product_shop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional//Note:This is necessary because roles are Lazy be default
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user= userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username: "+username+" not found!"));
        user.getRoles().size();//Note:This is necessary because roles are Lazy be default
        return new CustomUserDetails(user);
    }

}
