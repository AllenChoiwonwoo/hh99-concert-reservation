package com.hh99.hh5concertreservation.waiting.domain;

import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueService {
    private final ITokenRepository tokenRepo;

    public TokenEntity add(Long userId) {
        // FIXME : 서비스 안에서 토큰엔티티를 생성하고 insert 하는게 나은가 OR 인자값 (userId, 0)만 넘기고 repositry 에서 생성해서 넣는게 나은가?
        TokenEntity newToken = new TokenEntity(userId, 0);
        TokenEntity token = tokenRepo.add(newToken);
        return token;
    }

    public CheckStateResult checkState(CheckStateCommand command) {
        TokenEntity token = tokenRepo.findToken(command.getToken());
        if (token.getStatus() == 1) {
            return CheckStateResult.builder().status(token.getStatus()).waitingCount(0L).build();
        }
        tokenRepo.save(token.renewExpiredAt());
        Long lastEnteredTokenId = tokenRepo.findLastEnteredTokenId(); // FIXME : lastEnteredTokenId 라는 변수명을 생성자에서는 다르게 쓰는게 맞는 방식인가? (생성자는 순수 함수로를 의도 )
        return new CheckStateResult(token.getId(), lastEnteredTokenId);
    }

    public boolean isValidate(String tokenStr) {
        return tokenRepo.findByToken(tokenStr).isPresent();
    }

    public void expireToken(Long tokenId) {
        TokenEntity token = tokenRepo.findById(tokenId);
        tokenRepo.save(token.setExpire());
    }
    
    public void expireInactiveToken() {
        tokenRepo.expireInactiveToken(System.currentTimeMillis());
    }
}
