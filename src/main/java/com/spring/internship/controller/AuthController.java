package com.spring.internship.controller;

import com.spring.internship.exception.BadRequestException;
import com.spring.internship.model.AuthProvider;
import com.spring.internship.model.Role;
import com.spring.internship.model.RoleName;
import com.spring.internship.model.User;
import com.spring.internship.payload.ApiResponse;
import com.spring.internship.payload.LoginRequest;
import com.spring.internship.payload.LoginResponse;
import com.spring.internship.payload.SignUpRequest;
import com.spring.internship.repository.RoleRepository;
import com.spring.internship.repository.UserRepository;
import com.spring.internship.security.TokenProvider;
import com.spring.internship.service.IRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    	//authenticationManager.authenticate is passing the UsernamePasswordAuthenticationToken to the default AuthenticationProvider, 
    	//which will use the userDetailsService to get the user based on username and compare that user's password with the one in the authentication token.
    	// return with an indication of "Authenticated", "Unauthenticated", or "Could not authenticate"
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, user));
    }

    //@RequestBody annotation maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization of the inbound HttpRequest body onto a Java object with same properties as the JSON sent
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        
        Role userRole = roleService.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        //Create dynamically a path localhost/user/me that will be send to the authenticated user
        //This path return the logged user as a json, the definition is in the UserController 
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        // Put the location (.../user/me) in the header of the response
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

}
