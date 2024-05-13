package com.travel.plan.service;

import com.travel.plan.crypto.PasswordEncoder;
import com.travel.plan.domain.User;
import com.travel.plan.exception.AlreadyExistsEmailException;
import com.travel.plan.exception.InvalidRequest;
import com.travel.plan.exception.InvalidSignInformation;
import com.travel.plan.repository.UserRepository;
import com.travel.plan.request.Login;
import com.travel.plan.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long singin(Login login){

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSignInformation::new);


        PasswordEncoder encoder = new PasswordEncoder();
        var matches = encoder.matches(login.getPassword(),user.getPassword());
        if (!matches){
            throw new InvalidSignInformation();
        }

        return user.getId();

    }

    public void signup(Signup signup){
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()){
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder encoder = new PasswordEncoder();

       String encryptedPassword = encoder.encrypt(signup.getPassword());


       User user = User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();

        userRepository.save(user);
    }


}
