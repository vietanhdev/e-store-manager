package com.example.store.controller;

import javax.validation.Valid;

import com.example.store.model.User;
import com.example.store.payload.*;
import com.example.store.repository.RoleRepository;
import com.example.store.repository.UserRepository;
import com.example.store.security.CurrentUser;
import com.example.store.security.JwtTokenProvider;
import com.example.store.security.UserPrincipal;
import com.example.store.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
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

    // User use to check if username is availability
    @GetMapping("/users/checkUsernameAvailability")
    @PreAuthorize("hasAnyRole('CASHIER','MANAGER','ADMIN')")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }   

    // User use to check if email is availability
    @GetMapping("/users/checkEmailAvailability")
    @PreAuthorize("hasAnyRole('CASHIER','MANAGER','ADMIN')")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    // User sign in his account
    @PostMapping("/public/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    // User change his password
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @CurrentUser UserPrincipal currentUser) {
        // Find current user object
        Long id = currentUser.getId();
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        // Check old password
        String old_password = changePasswordRequest.getOldPassword();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(currentUser.getUsername(),
                        old_password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Set new password and save
        String new_password = changePasswordRequest.getNewPassword();
        user.setPassword(passwordEncoder.encode(new_password));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Your Password is changed"));
    }

    // User get his own profile
    @GetMapping("/me")
    public UserProfile getUserProfile(@CurrentUser UserPrincipal currentUser) {
        // Create new userProfile with information of current user
        UserProfile userProfile = new UserProfile(currentUser.getId(), currentUser.getName(), currentUser.getUsername(), currentUser.getSalary(), currentUser.getEmail(), currentUser.getAddress(), currentUser.getMobileNo());

        userProfile.setRoles(currentUser.getRoles());

        return userProfile;
    }

    // User change his own information
    @PutMapping("/me")
    public ResponseEntity<?> changeUserInfor(@Valid @RequestBody ChangeUserInforRequest changeUserInforRequest, @CurrentUser UserPrincipal currentUser) {
        // Find current user object
        Long id = currentUser.getId();
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        // Set new information
        user.setName(changeUserInforRequest.getName());
        user.setUsername(changeUserInforRequest.getUsername());
        user.setEmail(changeUserInforRequest.getEmail());
        user.setAddress(changeUserInforRequest.getAddress());
        user.setMobileNo(changeUserInforRequest.getMobileNo());
        userRepository.save(user);
        
        return ResponseEntity.ok(new ApiResponse(true, "Your Account Information is changed"));
    }

    // User delete his account
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount(@CurrentUser UserPrincipal currentUser) {
        // Find current user object
        Long id = currentUser.getId();
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        // Delete user
        userRepository.delete(user);

        return ResponseEntity.ok(new ApiResponse(true, "Your Account is deleted"));
    }

    // Admin reset other user password
    @PutMapping("/users/{username}/changePassword")
    public ResponseEntity<?> changeOtherPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Set new password and save
        String new_password = changePasswordRequest.getNewPassword();
        user.setPassword(passwordEncoder.encode(new_password));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "User Password is changed"));
    }

    // Admin get other user profile
    @GetMapping("/users/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserProfile getOtherUserInfor(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getName(), user.getUsername(), user.getSalary(), user.getEmail(), user.getAddress(), user.getMobileNo());

        userProfile.setRoles(user.getRoles());

        return userProfile;
    }

    // Admin change other user profile
    @PutMapping("/users/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeOtherUserProfile(
            @Valid @RequestBody ChangeUserProfileRequest changeUserProfileRequest,
            @PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Set new user profile
        user.setName(changeUserProfileRequest.getName());
        user.setUsername(changeUserProfileRequest.getUsername());
        user.setSalary(changeUserProfileRequest.getSalary());
        user.setEmail(changeUserProfileRequest.getEmail());
        user.setAddress(changeUserProfileRequest.getAddress());
        user.setMobileNo(changeUserProfileRequest.getMobileNo());
        
        for (String role: changeUserProfileRequest.getRoles()){
            Role userRole = roleRepository.findByName(RoleName.valueOf(role))
                    .orElseThrow(() -> new AppException(role + " not set."));
            user.addRole(userRole);
        }

        userRepository.save(user);
        
        return ResponseEntity.ok(new ApiResponse(true, "User Profile is changed"));
    }

    // Admin delete other user
    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOtherUser(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Delete user
        userRepository.delete(user);

        return ResponseEntity.ok(new ApiResponse(true, "Delete user successful"));
    }
}