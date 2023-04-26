package com.example.shopping.service;
import com.example.shopping.entity.data.Admin;
import com.example.shopping.entity.data.User;
import com.example.shopping.repository.AdminRepository;
import com.example.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        Admin admin = adminRepository.findByUsername(username);

        if (user != null) {
            return  user;
        }

        if (admin != null) {
            return admin;
        }

        throw new UsernameNotFoundException("User: " + username + " not found!");
    }

}
