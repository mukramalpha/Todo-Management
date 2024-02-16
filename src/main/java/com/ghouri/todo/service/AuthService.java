package com.ghouri.todo.service;

import com.ghouri.todo.dto.JwtAuthResponse;
import com.ghouri.todo.dto.LoginDto;
import com.ghouri.todo.dto.RegisterDto;

public interface AuthService {

    String registerUser(RegisterDto registerDto);
    JwtAuthResponse loginUser(LoginDto loginDto);
}
