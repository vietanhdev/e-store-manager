package com.example.store.controller.user_management;

import java.util.List;

import javax.validation.*;

import com.example.store.model.user_management.Role;
import com.example.store.model.user_management.RoleName;
import com.example.store.model.user_management.User;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.common.response.JwtAuthenticationResponse;
import com.example.store.payload.user_management.request.CreateUserRequest;
import com.example.store.payload.user_management.request.LoginRequest;
import com.example.store.payload.user_management.response.CreateUserResponse;
import com.example.store.repository.user_management.RoleRepository;
import com.example.store.repository.user_management.UserRepository;
import com.example.store.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/v1")
public class UseController {

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

    // Login into Store
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
    // Admin create a new user
    @PostMapping("/users")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        if( userRepository.existsByUsername( createUserRequest.getUsername() ) ){
            return new ResponseEntity<>(new ApiResponse(false, "username_is_taken", "Username is already taken!"),
                                        HttpStatus.ACCEPTED);
        }

        if( userRepository.existsByEmail( createUserRequest.getEmail() ) ){
            return new ResponseEntity<>(new ApiResponse(false, "email_is_taken", "email is already taken!"),
                                        HttpStatus.ACCEPTED);
        }

        // username, email, password are required
        User user = new User(createUserRequest.getUsername(), createUserRequest.getEmail(), createUserRequest.getPassword());

        // name, salary, mobileNo, address, role are optional
        user.setName(createUserRequest.getName());
        user.setSalary(createUserRequest.getSalary());
        user.setAddress(createUserRequest.getAddress());
        user.setMobileNo(createUserRequest.getMobileNo());

        try {
            List<String> roles = createUserRequest.getRoles();
            if(roles != null){
                for(String role: roles) {
                    Role userRole = roleRepository.findByName(RoleName.valueOf(role)).orElse(null);
                    user.addRole(userRole);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with role field"),
                                    HttpStatus.ACCEPTED);
        }

        // save to database
        User result = userRepository.save(user);

        return new ResponseEntity<>(new CreateUserResponse(true, result.getId().toString()),
                                    HttpStatus.ACCEPTED);
    }

    // Admin 
}