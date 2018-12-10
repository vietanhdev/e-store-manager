package com.example.store.controller.user_management;

import java.util.List;
import java.util.Set;

import javax.validation.*;

import com.example.store.model.user_management.Role;
import com.example.store.model.user_management.RoleName;
import com.example.store.model.user_management.User;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.user_management.request.ChangePasswordRequest;
import com.example.store.payload.user_management.request.CreateUserRequest;
import com.example.store.payload.user_management.request.LoginRequest;
import com.example.store.payload.user_management.request.UpdateUserRequest;
import com.example.store.payload.user_management.response.AllUserInforResponse;
import com.example.store.payload.user_management.response.ChangePasswordResponse;
import com.example.store.payload.user_management.response.CreateUserResponse;
import com.example.store.payload.user_management.response.LoginResponse;
import com.example.store.payload.user_management.response.UserInfor;
import com.example.store.payload.user_management.response.UserInforResponse;
import com.example.store.repository.user_management.RoleRepository;
import com.example.store.repository.user_management.UserRepository;
import com.example.store.security.CurrentUser;
import com.example.store.security.JwtTokenProvider;
import com.example.store.security.UserPrincipal;
import com.example.store.util.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
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

    /** Login into Store **/
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

        // search user and pass user summary information
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        LoginResponse loginResponse = new LoginResponse(user.getId(), 
                                                        user.getName(), 
                                                        user.getUsername(), 
                                                        user.getSalary(), 
                                                        user.getEmail(), 
                                                        user.getAddress(), 
                                                        user.getMobileNo(),
                                                        jwt);
        
        Set<Role> roles = user.getRoles();
        if(roles != null){
            for(Role role: roles) {
                loginResponse.addRole(role.getName().toString());
            }
        }
        return new ResponseEntity<>(loginResponse,
                                    HttpStatus.OK);
    }

    // User delete his own account
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteOwnAccount(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElse(null);
        userRepository.delete(user);
        return new ResponseEntity<>(new ApiResponse(true, "delete_user_successful", "delete user successful"),
                                HttpStatus.OK);
    }

    // User get his own information
    @GetMapping("/me")
    public ResponseEntity<?> getOwnInfor(@CurrentUser UserPrincipal currentUser) {
        UserInforResponse userInforResponse = new UserInforResponse(currentUser.getId(), 
                                                                    currentUser.getName(), 
                                                                    currentUser.getUsername(), 
                                                                    currentUser.getSalary(), 
                                                                    currentUser.getEmail(), 
                                                                    currentUser.getAddress(), 
                                                                    currentUser.getMobileNo());

        Set<Role> roles = currentUser.getRoles();
        if(roles != null){
            for(Role role: roles) {
                userInforResponse.addRole(role.getName().toString());
            }
        }
        return new ResponseEntity<>(userInforResponse,
                                    HttpStatus.OK);
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
        return new ResponseEntity<>(new ApiResponse(true, "update_user_information_successful", "update your information successful"),
                                HttpStatus.OK);
    }

    // User change his own password
    @PutMapping("/me/change_password")
    public ResponseEntity<?> changeOwnPassword(@CurrentUser UserPrincipal currentUser, 
                                            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        
        // Check old passsword and new password if indentity
        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), currentUser.getPassword()) == false){
            return new ResponseEntity<>(new ApiResponse(false, "old_password_not_correct", "old password is not correct"),
                                        HttpStatus.OK);
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
        
        return new ResponseEntity<>(new ChangePasswordResponse(jwt),
                                    HttpStatus.OK);
    }

    // Admin create a new user
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        if( userRepository.existsByUsername( createUserRequest.getUsername() ) ){
            return new ResponseEntity<>(new ApiResponse(false, "username_taken", "Username is already taken!"),
                                        HttpStatus.OK);
        }

        if( userRepository.existsByEmail( createUserRequest.getEmail() ) ){
            return new ResponseEntity<>(new ApiResponse(false, "email_taken", "email is already taken!"),
                                        HttpStatus.OK);
        }

        // username, email, password are required
        User user = new User(createUserRequest.getUsername(), 
                            createUserRequest.getEmail(), 
                            passwordEncoder.encode(createUserRequest.getPassword()));

        // name, salary, mobileNo, address, role are optional
        user.setName(createUserRequest.getName());
        user.setSalary(createUserRequest.getSalary());
        user.setAddress(createUserRequest.getAddress());
        user.setMobileNo(createUserRequest.getMobileNo());

        String r = "";
        try {
            List<String> roles = createUserRequest.getRoles();
            if(roles != null){
                for(String role: roles) {
                    r = role;
                    Role userRole = roleRepository.findByName(RoleName.valueOf(role)).orElse(null);
                    user.addRole(userRole);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_role", "role " + r + " does not exist"),
                                    HttpStatus.OK);
        }

        // save to database
        User result = userRepository.save(user);

        return new ResponseEntity<>(new CreateUserResponse(result.getId()),
                                    HttpStatus.OK);
    }

    // Admin get all user summary informations
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUsersPagable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        
        if(page < 0) {
            return new ResponseEntity<>( new ApiResponse(false, "page_num_unacceptable", "Page number cannot be less than zero."),
                                    HttpStatus.OK);
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            return new ResponseEntity<>( new ApiResponse(false, "page_size_unacceptable", "Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE),
                                    HttpStatus.OK);
        }

        AllUserInforResponse allUserInforResponse = new AllUserInforResponse();

        // find all users with page and size parameter
        Page<User> users = userRepository.getAllUsersPagable(PageRequest.of(page, size));
        
        for(User user: users.getContent()) {
            UserInfor userInfor = new UserInfor(user.getId(), 
                                                user.getName(),
                                                user.getUsername(),
                                                user.getSalary(), 
                                                user.getEmail(), 
                                                user.getAddress(), 
                                                user.getMobileNo());
            
            Set<Role> roles = user.getRoles();
            if(roles != null){
                for(Role role: roles) {
                    userInfor.addRole(role.getName().toString());
                }
            }
            allUserInforResponse.addUserInfor(userInfor);
        }

        return new ResponseEntity<>(allUserInforResponse,
                                    HttpStatus.OK);                                          
    }

    // Admin get a user information
    @GetMapping("/users/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserInfor(@PathVariable(value = "user_id") String user_id) {
        try {
            User user = userRepository.findById(Long.parseLong(user_id)).orElse(null);
            UserInforResponse userInforResponse = new UserInforResponse(user.getId(), 
                                                                        user.getName(), 
                                                                        user.getUsername(), 
                                                                        user.getSalary(), 
                                                                        user.getEmail(), 
                                                                        user.getAddress(), 
                                                                        user.getMobileNo());
            Set<Role> roles = user.getRoles();
            if(roles != null){
                for(Role role: roles) {
                    userInforResponse.addRole(role.getName().toString());
                }
            }
            return new ResponseEntity<>(userInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_user_id", "user id " + user_id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin update a user information
    @PutMapping("/users/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserInfor(@PathVariable(value = "user_id") String user_id, 
                                            @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        try{
            User user = userRepository.findById(Long.parseLong(user_id)).orElse(null);

            if(updateUserRequest.getUsername() != null){
                if( !updateUserRequest.getUsername().equals(user.getUsername()) ){
                    if( userRepository.existsByUsername( updateUserRequest.getUsername() ) ){
                        return new ResponseEntity<>(new ApiResponse(false, "username_taken", "Username is already taken!"),
                                                    HttpStatus.OK);
                    }
                }
                
                user.setUsername(updateUserRequest.getUsername());
            }

            if(updateUserRequest.getEmail() != null) {
                if( !updateUserRequest.getEmail().equals(user.getEmail()) ){
                    if( userRepository.existsByEmail( updateUserRequest.getEmail() ) ){
                        return new ResponseEntity<>(new ApiResponse(false, "email_taken", "Email is already taken!"),
                                                    HttpStatus.OK);
                    }
                }
                user.setEmail(updateUserRequest.getEmail());
            }
            

            if(updateUserRequest.getName() != null) user.setName(updateUserRequest.getName());
            if(updateUserRequest.getPassword() != null) user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            if(updateUserRequest.getAddress() != null) user.setAddress(updateUserRequest.getAddress());
            if(updateUserRequest.getMobileNo() != null) user.setMobileNo(updateUserRequest.getMobileNo());
            if(updateUserRequest.getSalary() != null) user.setSalary(updateUserRequest.getSalary());
            
            String r = "";
            try {
                List<String> roles = updateUserRequest.getRoles();
                if(roles != null){
                    for(String role: roles) {
                        r = role;
                        Role userRole = roleRepository.findByName(RoleName.valueOf(role)).orElse(null);
                        user.addRole(userRole);
                    }
                }
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, "wrong_role", "role " + r + " does not exist"),
                                        HttpStatus.OK);
            }

            userRepository.save(user);
            return new ResponseEntity<>(new ApiResponse(true, "update_user_information_successful", "update user information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_user_id", "user id " + user_id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin delete a user
    @DeleteMapping("/users/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user_id") String user_id) {
        try {
            User user = userRepository.findById(Long.parseLong(user_id)).orElse(null);
            userRepository.delete(user);
            return new ResponseEntity<>(new ApiResponse(true, "delete_user_successful", "delete user successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_user_id", "user id " + user_id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // JUST FOR TEST: create default admin account
    @GetMapping("/default_initial")
    public ResponseEntity<?> defaultInitial() {
        if(roleRepository.findByName(RoleName.ROLE_ADMIN).orElse(null) == null) roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        if(roleRepository.findByName(RoleName.ROLE_MANAGER).orElse(null) == null) roleRepository.save(new Role(RoleName.ROLE_MANAGER));
        if(roleRepository.findByName(RoleName.ROLE_CASHIER).orElse(null) == null) roleRepository.save(new Role(RoleName.ROLE_CASHIER));

        if( userRepository.existsByUsername( "admin" ) ){
            return new ResponseEntity<>(new ApiResponse(false, "username_taken", "username is already taken!"),
                                        HttpStatus.OK);
        }

        if( userRepository.existsByEmail( "admin@gmail.com" ) ){
            return new ResponseEntity<>(new ApiResponse(false, "email_taken", "email is already taken!"),
                                        HttpStatus.OK);
        }

        User user = new User("admin", 
                            "admin@gmail.com", 
                            passwordEncoder.encode("admin@gmail.com"));

        Role userRole = roleRepository.findByName(RoleName.valueOf("ROLE_ADMIN")).orElse(null);
        user.addRole(userRole);
        userRepository.save(user);
        
        return new ResponseEntity<>(new ApiResponse(true, "intial_successfull", "intial successfull"),
                                    HttpStatus.OK);
    }
}