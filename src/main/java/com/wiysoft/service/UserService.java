package com.wiysoft.service;

import com.wiysoft.exceptions.*;
import com.wiysoft.persistence.model.User;
import com.wiysoft.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by weiliyang on 1/11/16.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(String name, String email, String password) throws UserManagementException {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        if (userRepository.countByName(name) > 0) {
            throw new DuplicateUserNameException("Name already exists.", null, user);
        } else if (userRepository.countByEmail(email) > 0) {
            throw new DuplicateUserEmailException("Email already exists.", null, user);
        } else {
            User savedUser = userRepository.save(user);
            return savedUser;
        }
    }

    @Transactional
    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    @Transactional
    public User changePassword(long id, String password, String newPassword) throws UserManagementException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new UserIdNotFoundException("User ID " + id + " not found.", null, null);
        }

        if (!user.getPassword().equals(password)) {
            throw new WrongPasswordException("Wrong password.", null, user);
        }

        user.setPassword(newPassword);

        return userRepository.save(user);
    }
}
