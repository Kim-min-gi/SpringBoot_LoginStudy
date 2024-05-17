package com.travel.plan.service;

import com.travel.plan.domain.User;
import com.travel.plan.exception.AlreadyExistsEmailException;
import com.travel.plan.repository.UserRepository;
import com.travel.plan.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(Signup signup){
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()){
            throw new AlreadyExistsEmailException();
        }


       String encryptedPassword = passwordEncoder.encode(signup.getPassword());


       User user = User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();

        userRepository.save(user);
    }


}
