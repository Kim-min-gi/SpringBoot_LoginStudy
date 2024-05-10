package com.travel.plan.config;

import com.travel.plan.config.data.UserSession;
import com.travel.plan.exception.Unauthorized;
import com.travel.plan.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final AppConfig appConfig;
    private final SessionRepository sessionRepository;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("Authorization");

        if (jws == null || jws.isEmpty()){
            throw new Unauthorized();
        }

        SecretKey originalKey = appConfig.getJwtKey();

        try {

         Jws<Claims> claims = Jwts.parser()
                    .verifyWith(originalKey)
                    .build()
                    .parseSignedClaims(jws);

         String userId = claims.getPayload().getSubject();
         return new UserSession(Long.parseLong(userId));

        } catch (JwtException e) {
            throw new Unauthorized();
        }

//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//
//        if (servletRequest == null){
//            log.error("servletRequest null");
//            throw new Unauthorized();
//        }
//
//        Cookie[] cookies = servletRequest.getCookies();
//
//        if (cookies.length == 0){
//            log.error("쿠키가 없음!");
//            throw new Unauthorized();
//        }
//
//        String accessToken = cookies[0].getValue();
//
//        Session session = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(Unauthorized::new);


        //return new UserSession(session.getUser().getId());
    }
}
