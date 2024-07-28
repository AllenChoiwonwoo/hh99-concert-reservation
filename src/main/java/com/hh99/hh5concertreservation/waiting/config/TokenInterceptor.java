package com.hh99.hh5concertreservation.waiting.config;


import com.hh99.hh5concertreservation.waiting.application.usecase.QueueUsecase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    private final QueueUsecase queueUsecase;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/favicon.ico")) return true;

        if (method.equals("GET") && requestURI.equals("/concert")){
            return true;
        }
        if (requestURI.startsWith("/concert/options")) return true;

        if (method.equals("GET") && requestURI.startsWith("/concert/schedules") && requestURI.startsWith("/concert/options")){
            return true;
        }
        if (requestURI.startsWith("/user")){
            return true;
        }
        if (requestURI.equals("/token")){
            return true;
        }


        String token = request.getHeader("Token");
        if (token != null && queueUsecase.validateToken(token)) {
            return true;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        log.info("Unauthorized access: {} {} , token : {}", method, requestURI, token);
        return false;
    }
}
