package com.edu.miu.controller;

import com.edu.miu.payload.Requests.LoginRequest;
import com.edu.miu.payload.Requests.SignUpRequest;
import com.edu.miu.payload.Response.ApiResponse;
import com.edu.miu.payload.Response.JwtAuthenticationResponse;
import com.edu.miu.security.CurrentUser;
import com.edu.miu.security.JwtTokenProvider;
import com.edu.miu.security.UserPrincipal;
import com.edu.miu.service.UserService;

import com.edu.miu.model.User;
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

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;




    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;


    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken( @CurrentUser UserPrincipal u) {
        return ResponseEntity.ok(u);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,(UserPrincipal)authentication.getPrincipal()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        System.out.println("111");
        try{
            if(userService.existsByUsername(signUpRequest.getUsername())) {
                return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                        HttpStatus.BAD_REQUEST);
            }

            if(userService.existsByEmail(signUpRequest.getEmail())) {
                return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                        HttpStatus.BAD_REQUEST);
            }
            System.out.println("111");
            // Creating user's account
            User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                    signUpRequest.getEmail(), signUpRequest.getPassword(),signUpRequest.getRole() );
            System.out.println("333");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            System.out.println("444");
            //System.out.println(roleRepository.findAll().toString());
            //List<User> users = roleRepository.findAll();

            System.out.println("555");
           // RoleName userRoleName = signUpRequest.getRole();
            //System.out.println(userRoleName.name());
          //  Role userRole = getUserRole(userRoleName);
            String userRole = signUpRequest.getRole();

            System.out.println("666");
            user.setRole(userRole);
            System.out.println("777");
            User result = userService.save(user);

            //notificationServiceFactory.getService(NotificationType.Email).sendNotification(result,"Welcome to MIU Job Portal","Welcome "+result.getName()+" to MIU Job Portal");

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{username}")
                    .buildAndExpand(result.getUsername()).toUri();

            return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }



//    private Role getUserRole(RoleName userRoleName) {
//        return new Role(1," );
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });


   // @PostMapping("/signupx")
//    public ResponseEntity<?> registerUsexr( @RequestBody SignUpRequest signUpRequest) {
//        if (userService.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new AppException("Error: Username is already taken!"));
//        }
//
//        if (userService.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new AppException("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getName(),signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                passwordEncoder.encode(signUpRequest.getPassword()));
//        String strRoles = signUpRequest.getRole().name();
//        Set<Role> roles = new HashSet<>();
//
//
//        // if (strRoles == null) {
//        Role userRole = roleRepository.findByName(RoleName.ROLE_COMPANY).get();
//              //  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//        roles.add(userRole);
//        //  }
////        } else {
////            strRoles.forEach(role -> {
////                switch (role) {
////                    case "admin":
////                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////                        roles.add(adminRole);
////
////                        break;
////                    case "mod":
////                        Role modRole = roleRepository.findByName(RoleName.ROLE_COMPANY)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////                        roles.add(modRole);
////
////                        break;
////                    default:
////                        Role userRole = roleRepository.findByName(RoleName.ROLE_JOBSEEKER)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////                        roles.add(userRole);
////                }
////            });
////        }
//
//        user.setRoles(roles);
//        userService.save(user);
//
//        return ResponseEntity.ok(new AppException("User registered successfully!"));
//    }
    }



