package com.hh99.hh5concertreservation.waiting.domain;

import com.hh99.hh5concertreservation.common.CustomException;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateCommand;
import com.hh99.hh5concertreservation.waiting.application.dto.CheckStateResult;
import com.hh99.hh5concertreservation.waiting.domain.RepositoryInterface.ITokenRepository;
import com.hh99.hh5concertreservation.waiting.domain.model.TokenEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QueueService {
    private final ITokenRepository tokenRepo;

    private final Integer WAIT = 0;
    private final Integer ENTER = 1;

    public TokenEntity add(Long userId) {
        // FIXME : 서비스 안에서 토큰엔티티를 생성하고 insert 하는게 나은가 OR 인자값 (userId, 0)만 넘기고 repositry 에서 생성해서 넣는게 나은가?
        TokenEntity newToken = new TokenEntity(userId, 0);
        TokenEntity token = tokenRepo.add(newToken);
        return token;
    }

    public CheckStateResult checkState(CheckStateCommand command) {
        TokenEntity token = tokenRepo.findByToken(command.getToken()).orElseThrow(() -> new CustomException(CustomException.ErrorEnum.NO_TOKEN));
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

    public void expireToken(Long userId) {
        TokenEntity token = tokenRepo.findByUserId(userId).orElseThrow(() -> new CustomException(CustomException.ErrorEnum.NO_TOKEN));
        tokenRepo.save(token.setExpire()); // FIXME : 이부분을 repo 로 옮길 수 있을것 같지만, 비즈니스 로직 같아서 놔둠 , 이게 맞나?
    }
    
    public void expireInactiveToken() {
        List<TokenEntity> tokens = tokenRepo.findTokensByStatus(WAIT);
        if (Objects.isNull(tokens) || tokens.isEmpty()) return;

        List<TokenEntity> entities = tokens.stream().map(i -> i.checkExpired()).collect(Collectors.toList());
//        tokenRepo.expireInactiveToken(System.currentTimeMillis());
        tokenRepo.saveAll(entities);
    }
}
