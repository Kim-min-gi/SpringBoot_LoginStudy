package com.travel.plan.service;

import com.travel.plan.domain.Session;
import com.travel.plan.domain.User;
import com.travel.plan.exception.InvalidSignInformation;
import com.travel.plan.repository.UserRepository;
import com.travel.plan.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long singin(Login login){

        User user = userRepository.findByEmailAndPassword(login.getEmail(),login.getPassword())
                .orElseThrow(InvalidSignInformation::new);

      Session session = user.addSession();

     //return session.getAccessToken();
        return user.getId();

    }


}
