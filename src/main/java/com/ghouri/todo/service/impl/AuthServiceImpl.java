package com.ghouri.todo.service.impl;

import com.ghouri.todo.dto.JwtAuthResponse;
import com.ghouri.todo.dto.LoginDto;
import com.ghouri.todo.dto.RegisterDto;
import com.ghouri.todo.entity.Role;
import com.ghouri.todo.entity.User;
import com.ghouri.todo.exception.TodoAPIException;
import com.ghouri.todo.repository.RoleRepository;
import com.ghouri.todo.repository.UserRepository;
import com.ghouri.todo.security.JwtTokenProvider;
import com.ghouri.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String registerUser(RegisterDto registerDto) {
        //check if username is already exist in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"username is already exist!Try another one.");
        }

        //check if email is already exist in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw  new TodoAPIException(HttpStatus.BAD_REQUEST,"email is already exist!Try another one.");
        }

        User user=new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles=new HashSet<>();
        Role userRole=roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return "User registered Successfully!.";
    }

    @Override
    public JwtAuthResponse loginUser(LoginDto loginDto) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtTokenProvider.generateToken(authentication);
        Optional<User> optionalUser=userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),loginDto.getUsernameOrEmail());
        String role=null;
        if(optionalUser.isPresent()){
            User loggedInUser=optionalUser.get();
            Optional<Role> optionalRole=loggedInUser.getRoles().stream().findFirst();
            if(optionalRole.isPresent()){
                Role userRole=optionalRole.get();
                role=userRole.getName();
            }
        }
        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);
        return jwtAuthResponse;
    }
}
