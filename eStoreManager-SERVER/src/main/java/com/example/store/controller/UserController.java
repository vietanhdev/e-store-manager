package com.example.store.controller;

import javax.validation.*;

import com.example.store.exception.AppException;
import com.example.store.model.Role;
import com.example.store.model.RoleName;
import com.example.store.model.User;
import com.example.store.payload.request.CreateUserRequest;
import com.example.store.payload.response.ApiResponse;
import com.example.store.repository.RoleRepository;
import com.example.store.repository.UserRepository;
import com.example.store.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/v1")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    // Admin create a new user
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        if( userRepository.existsByUsername( createUserRequest.getUsername() ) ){
            return new ResponseEntity<>(new ApiResponse(false, "username_is_taken", "Username is already taken!"),
                                        HttpStatus.BAD_REQUEST);
        }

        User user = new User( createUserRequest.getUsername(), createUserRequest.getEmail(), createUserRequest.getPassword() );

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        for(String role: createUserRequest.getRoles()) {
            Role userRole = roleRepository.findByName(RoleName.valueOf(role))
                                .orElseThrow(() -> new AppException("User Role not set."));
            user.addRole(userRole);
        }

        return new ResponseEntity<>(new ApiResponse(true, "hello_world", "hello world"),
                                    HttpStatus.ACCEPTED);

        // userRepository.save(user);
        // User result = userRepository.save(user);

        // return new ResponseEntity<>(new ApiResponse(true, "user_id", result.getId().toString()),
        //                             HttpStatus.ACCEPTED);
    }

}