package com.example.store.controller.user_management;

import java.util.List;

import javax.validation.*;

import com.example.store.model.user_management.Role;
import com.example.store.model.user_management.RoleName;
import com.example.store.model.user_management.User;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.common.response.JwtAuthenticationResponse;
import com.example.store.payload.user_management.request.ChangePasswordRequest;
import com.example.store.payload.user_management.request.CreateUserRequest;
import com.example.store.payload.user_management.request.LoginRequest;
import com.example.store.payload.user_management.request.UpdateUserRequest;
import com.example.store.payload.user_management.response.CreateUserResponse;
import com.example.store.payload.user_management.response.ResetPasswordResponse;
import com.example.store.payload.user_management.response.UserInforResponse;
import com.example.store.payload.user_management.response.UserSummaryResponse;
import com.example.store.repository.user_management.RoleRepository;
import com.example.store.repository.user_management.UserRepository;
import com.example.store.security.CurrentUser;
import com.example.store.security.JwtTokenProvider;
import com.example.store.security.RandomPasswordGenerator;
import com.example.store.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
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


    // After login, user get summary information
    @GetMapping("/me/profile")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummaryResponse userSummaryResponse = new UserSummaryResponse(true, 
                                                        currentUser.getId(), 
                                                        currentUser.getUsername(), 
                                                        currentUser.getName());
        return new ResponseEntity<>(userSummaryResponse,
                                    HttpStatus.ACCEPTED);
    }

    // User delete his own account
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteOwnAccount(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElse(null);
        userRepository.delete(user);
        return new ResponseEntity<>(new ApiResponse(true, "success", "delete user successful"),
                                HttpStatus.ACCEPTED);
    }

    // User get his own information
    @GetMapping("/me")
    public ResponseEntity<?> getOwnInfor(@CurrentUser UserPrincipal currentUser) {
        UserInforResponse userInforResponse = new UserInforResponse(true, currentUser.getId(), 
                                                                    currentUser.getName(), 
                                                                    currentUser.getUsername(), 
                                                                    currentUser.getSalary(), 
                                                                    currentUser.getEmail(), 
                                                                    currentUser.getAddress(), 
                                                                    currentUser.getMobileNo());
        for(Role role: currentUser.getRoles()) {
            userInforResponse.addRole(role.getName().toString());
        }
        return new ResponseEntity<>(userInforResponse,
                                    HttpStatus.ACCEPTED);
    }

    // User update his own information
    @PutMapping("/me")
    public ResponseEntity<?> updateUserInfor(@CurrentUser UserPrincipal currentUser, 
                                            @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(currentUser.getId()).orElse(null);
        if(updateUserRequest.getName() != null) user.setName(updateUserRequest.getName());
        if(updateUserRequest.getUsername() != null) user.setUsername(updateUserRequest.getUsername());
        if(updateUserRequest.getEmail() != null) user.setEmail(updateUserRequest.getEmail());
        if(updateUserRequest.getAddress() != null) user.setAddress(updateUserRequest.getAddress());
        if(updateUserRequest.getMobileNo() != null) user.setMobileNo(updateUserRequest.getMobileNo());

        userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse(true, "update_successful", "update profile successful"),
                                HttpStatus.ACCEPTED);
    }

    // User change his own password
    @PutMapping("/me/change_password")
    public ResponseEntity<?> changeOwnPassword(@CurrentUser UserPrincipal currentUser, 
                                            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        
        // Check old passsword and new password if indentity
        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), currentUser.getPassword()) == false){
            return new ResponseEntity<>(new ApiResponse(false, "change_password_fail", "old password is not correct"),
                                HttpStatus.ACCEPTED);
        };

        // Save new password to database
        User user = userRepository.findById(currentUser.getId()).orElse(null);
        String password = changePasswordRequest.getNewPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        currentUser.getUsername(),
                        changePasswordRequest.getNewPassword()
                )
        );

        // return new token for user
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
        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        String password = randomPasswordGenerator.generatePassayPassword();
        User user = new User(createUserRequest.getUsername(), 
                            createUserRequest.getEmail(), 
                            passwordEncoder.encode(password));

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

        return new ResponseEntity<>(new CreateUserResponse(true, result.getId(), password),
                                    HttpStatus.ACCEPTED);
    }

    // Admin get a user information
    @GetMapping("/users/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserInfor(@PathVariable(value = "user_id") String user_id) {
        try {
            User user;
            user = userRepository.findById(Long.parseLong(user_id)).orElse(null);
            UserInforResponse userInforResponse = new UserInforResponse(true, user.getId(), user.getName(), user.getUsername(), 
                                                user.getSalary(), user.getEmail(), user.getAddress(), user.getMobileNo());
            for(Role role: user.getRoles()) {
                userInforResponse.addRole(role.getName().toString());
            }
            return new ResponseEntity<>(userInforResponse,
                                        HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with user id"),
                                    HttpStatus.ACCEPTED);
        }
    }

    // Admin update a user information
    @PutMapping("/users/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserInfor(@PathVariable(value = "user_id") String user_id, 
                                            @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        try{
            User user;
            user = userRepository.findById(Long.parseLong(user_id)).orElse(null);
            if(updateUserRequest.getName() != null) user.setName(updateUserRequest.getName());
            if(updateUserRequest.getUsername() != null) user.setUsername(updateUserRequest.getUsername());
            if(updateUserRequest.getEmail() != null) user.setEmail(updateUserRequest.getEmail());
            if(updateUserRequest.getAddress() != null) user.setAddress(updateUserRequest.getAddress());
            if(updateUserRequest.getMobileNo() != null) user.setMobileNo(updateUserRequest.getMobileNo());
            if(updateUserRequest.getSalary() != null) user.setSalary(updateUserRequest.getSalary());
            
            try {
                List<String> roles = updateUserRequest.getRoles();
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

            userRepository.save(user);
            return new ResponseEntity<>(new ApiResponse(true, "update_successful", "update successful"),
                                    HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with user id"),
                                    HttpStatus.ACCEPTED);
        }
    }

    // Admin reset user password
    @GetMapping("/users/{user_id}/reset_password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> resetUserPassword(@PathVariable(value = "user_id") String user_id){
        try {
            User user;
            user = userRepository.findById(Long.parseLong(user_id)).orElse(null);
            // Create a new random password and save encoded password
            RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
            String password = randomPasswordGenerator.generatePassayPassword();
            user.setPassword(passwordEncoder.encode(password));

            userRepository.save(user);

            return new ResponseEntity<>(new ResetPasswordResponse(true, password),
                                        HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with user id"),
                                    HttpStatus.ACCEPTED);
        }
    }

    // Admin delete a user
    @DeleteMapping("/users/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user_id") String user_id) {
        try {
            User user;
            user = userRepository.findById(Long.parseLong(user_id)).orElse(null);
            userRepository.delete(user);
            return new ResponseEntity<>(new ApiResponse(true, "success", "delete user successful"),
                                    HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with user id"),
                                    HttpStatus.ACCEPTED);
        }
    }
}