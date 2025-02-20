package com.strawdecks.strawdeck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.UsersDao;
import com.strawdecks.strawdeck.modelo.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
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
    public Optional<Users> findUser(String name){
        return usersDao.find(name);
    }
    public List<Users> getAllUsers(){
        return usersDao.getAllActive();
    }
    
}
