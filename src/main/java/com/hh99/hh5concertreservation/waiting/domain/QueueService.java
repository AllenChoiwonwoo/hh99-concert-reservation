package com.hh99.hh5concertreservation.waiting.domain;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class QueueService {
    private final ITokenRepository tokenRepo;

    private final Integer WAIT = 0;
    private final Integer ENTER = 1;

    public TokenEntity add(Long userId) {
        // FIXME : 서비스 안에서 토큰엔티티를 생성하고 insert 하는게 나은가 OR 인자값 (userId, 0)만 넘기고 repositry 에서 생성해서 넣는게 나은가?
        TokenEntity newToken = new TokenEntity(userId, 0);
        TokenEntity token = tokenRepo.addToWaitList(newToken);
        return token;
    }

    public CheckStateResult checkState(CheckStateCommand command) {
        Optional<String> token = tokenRepo.findByToken(command.getToken());
        if (token.isPresent()) {
            return new CheckStateResult(1, 0);
        }
        Integer number = tokenRepo.findTurnNumber(command.getToken());
        return new CheckStateResult(0, number);
    }

    public boolean isValidate(String tokenStr) {
        Set<String> tokens = tokenRepo.findEnteredTokens();
        return tokens.stream().filter(token -> token.startsWith(tokenStr)).findFirst().isPresent();
    }

    public void expireTokenFromActive(String userToken) {
        Set<String> tokens = tokenRepo.findEnteredTokens();
        tokens.stream().filter(token -> token.startsWith(userToken)).findFirst().ifPresent(tokenRepo::expireFromActives);

    }
    
    public void expireInactiveEnterToken() {
        // enter token 개수를 가져온다.
        Set<String> tokens = tokenRepo.findEnteredTokens();
        tokens.stream()
                .filter(token -> System.currentTimeMillis() > Long.parseLong(token.split(":")[1]))
                .forEach(tokenRepo::expireFromActives);
    }

    public void moveWaitingToEnter() {
        List<String> waitings = tokenRepo.findWaitings(2);
        for (String tokenStr : waitings) {
            tokenRepo.addToActive(tokenStr);
            tokenRepo.removeFromWait(tokenStr);
        }
    }

    public void addToActive(String token) {
        tokenRepo.addToActive(token);
    }
}
