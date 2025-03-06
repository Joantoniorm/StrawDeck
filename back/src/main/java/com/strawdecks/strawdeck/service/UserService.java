package com.strawdecks.strawdeck.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.UsersDao;
import com.strawdecks.strawdeck.modelo.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UsersDao usersDao;
    public void createUser(Users users){
        usersDao.create(users);
    }
    public void updateUser(Users users){
        usersDao.update(users);
    }
    public void deleteUser(int id){
        usersDao.delete(id, false);
    }
    public Optional<Users> findUser(int id){
        return usersDao.find(id);
    }
    public Optional<Users> findUserByName(String name){
        return usersDao.findByName(name);
    }
    public List<Users> getAllUsers(){
        return usersDao.getAllActive();
    }

     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersDao.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.emptyList()) 
                .build();
    }
}
