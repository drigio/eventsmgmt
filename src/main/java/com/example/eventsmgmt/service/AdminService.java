package com.example.eventsmgmt.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.example.eventsmgmt.model.Role;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.repository.RoleRepository;
import com.example.eventsmgmt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService
 */
@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //Role admin = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));

    public void addAdmin(User user) {

        //Set admin role
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found")));
        user.setRole(roles);     

        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User getById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found By Id"));
        Role admin = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        if(user.getRole().contains(admin)) {
            return user;
        }
        return null;
	}

	public List<User> getAllAdmins() {
        Set<Role> roles =  new HashSet<Role>();
        Role admin = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(admin);
		return userRepository.findByRole(roles);
	}

	public User getByUsername(String username) {
        User user =  userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found by username"));
        Role admin = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        if(user.getRole().contains(admin)) {
            return user;
        }
        return null;
	}

	public void deleteById(int id) {
        userRepository.deleteById(id);
	}

	public void updateAdminById(int id, User user) {
        User oldDetails = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find user"));
        user.setId(id);
        //Created at will always be same
        user.setCreatedAt(oldDetails.getCreatedAt());
        //Change the last updated details to now
        Date date = new Date();
        user.setLastUpdated(new Timestamp(date.getTime()));
        //Check if admin has updated password
        String password = user.getPassword();
        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        if (!BCRYPT_PATTERN.matcher(password).matches() && password != null && password.length() > 0) {
            //If updated password then change the password 
            password = passwordEncoder.encode(password);
            user.setPassword(password);
        } else {
            //Set the password to old password If password not changed
            user.setPassword(oldDetails.getPassword());
        }
        //Roles will always be the same
        user.setRole(oldDetails.getRole());
        userRepository.save(user);
	}

}