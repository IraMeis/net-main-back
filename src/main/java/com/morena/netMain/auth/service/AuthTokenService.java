package com.morena.netMain.auth.service;

import com.morena.netMain.auth.jwt.JwtRequest;
import com.morena.netMain.auth.jwt.JwtResponse;
import com.morena.netMain.auth.jwt.LoginResponse;
import com.morena.netMain.logic.utils.Message;
import com.morena.netMain.auth.model.AuthUser;
import com.morena.netMain.auth.model.SysTokens;
import com.morena.netMain.auth.repository.SysTokensRepository;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.utils.ResponseIf;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthTokenService {

    private final AuthUserService authUserService;
    private final SysTokensRepository sysTokensRepository;
    private final JwtProvider jwtProvider;

    public ResponseIf<LoginResponse> login(JwtRequest authRequest) {
        if(authRequest == null)
            return new ResponseIf<>("Невалидные данные");

        Optional<SysUsers> sysUser = authUserService.getSysUserByLogin(authRequest.getUsername());
        if(sysUser.isEmpty())
            return new ResponseIf<>("Пользователь не найден");

        final AuthUser user = AuthUserService.mapToAuthUser(sysUser.get());

        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            Optional<SysTokens> token = sysTokensRepository.findByOwnerAndIsDeletedFalse(sysUser.get());
            if (token.isPresent()){
                token.get().setContent(refreshToken.getBytes(StandardCharsets.UTF_8));
                sysTokensRepository.save(token.get());
            }
            else {
                Optional<SysTokens> bannedtoken = sysTokensRepository.findByOwnerAndIsDeletedTrue(sysUser.get());
                if(bannedtoken.isPresent())
                    return new ResponseIf<>("Пользователь забанен");

                sysTokensRepository.save(new SysTokens(
                        UUID.randomUUID(),
                        false,
                        refreshToken.getBytes(StandardCharsets.UTF_8),
                        sysUser.get()));
            }
            sysUser.get().setIsActive(true);
            authUserService.saveSysUser(sysUser.get());
            return new ResponseIf<>(new LoginResponse(user, accessToken, refreshToken));
        } else {
            return new ResponseIf<>("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(String refreshToken) {
        if (refreshToken != null && jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();

            final Optional<SysUsers> sysUser = authUserService.getSysUserByLogin(login);
            if(sysUser.isEmpty())
                return buildSimpleAnswer("Пользователь не найден");

            final AuthUser user = AuthUserService.mapToAuthUser(sysUser.get());

            final SysTokens savedToken = sysUser.get().getToken();
            if (savedToken==null || savedToken.getIsDeleted())
                return buildSimpleAnswer("Пользователь забанен");

            final String saveRefreshToken = new String(savedToken.getContent(), StandardCharsets.UTF_8);

            if (saveRefreshToken.equals(refreshToken)) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return buildSimpleAnswer("Токен невалиден");
    }

    public JwtResponse refresh(String refreshToken) {
        if (refreshToken!=null && jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();

            final Optional<SysUsers> sysUser = authUserService.getSysUserByLogin(login);
            if(sysUser.isEmpty())
                return buildSimpleAnswer("Пользователь не найден");

            final AuthUser user = AuthUserService.mapToAuthUser(sysUser.get());

            final SysTokens saveRefreshToken = sysUser.get().getToken();
            if (saveRefreshToken==null || saveRefreshToken.getIsDeleted())
                return buildSimpleAnswer("Пользователь забанен");

            final String saveRefreshTokenStr = new String(saveRefreshToken.getContent(), StandardCharsets.UTF_8);

            if (saveRefreshTokenStr.equals(refreshToken)) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                saveRefreshToken.setContent(newRefreshToken.getBytes(StandardCharsets.UTF_8));
                sysTokensRepository.save(saveRefreshToken);

                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        return buildSimpleAnswer("Токен невалиден");
    }

    private static JwtResponse buildSimpleAnswer(String reason){
        return JwtResponse.builder()
                .accessToken(null)
                .refreshToken(null)
                .error(reason)
                .build();
    }
}
