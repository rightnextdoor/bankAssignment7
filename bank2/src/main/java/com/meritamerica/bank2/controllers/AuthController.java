package com.meritamerica.bank2.controllers;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.meritamerica.bank2.JWT.JwtTokenProvider;
import com.meritamerica.bank2.exception.AppException;
import com.meritamerica.bank2.models.Role;
import com.meritamerica.bank2.models.RoleName;
import com.meritamerica.bank2.models.User;
import com.meritamerica.bank2.payload.ApiResponse;
import com.meritamerica.bank2.payload.JwtAuthenticationResponse;
import com.meritamerica.bank2.payload.LoginRequest;
import com.meritamerica.bank2.payload.SignUpRequest;
import com.meritamerica.bank2.repository.RoleRepository;
import com.meritamerica.bank2.repository.UserRepository;
import com.meritamerica.bank2.security.UserPrincipal;



@RestController
public class AuthController {

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

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/authenticate/createUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                 signUpRequest.getPassword(), signUpRequest.isActive());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
	
		
		if (strRoles == null) {
        
        Role userRole = roleRepository.findByName(RoleName.AccountHolder)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "Administrator":
					Role adminRole = roleRepository.findByName(RoleName.Administrator)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					user.setRoles(Collections.singleton(adminRole));

			break;
				default:
					Role userRole = roleRepository.findByName(RoleName.AccountHolder)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					user.setRoles(Collections.singleton(userRole));
				}
			});
		}
		
		userRepository.save(user);
		return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
//        user.setRoles(Collections.singleton(userRole));
//
//        User result = userRepository.save(user);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/api/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();

     //   return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
