package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.IdentityUserConverter;
import dev.oguzhanercelik.converter.UserConverter;
import dev.oguzhanercelik.entity.User;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.UserAuthentication;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.LoginRequest;
import dev.oguzhanercelik.model.request.RegisterRequest;
import dev.oguzhanercelik.model.response.AuthenticationResponse;
import dev.oguzhanercelik.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final IdentityUserConverter identityUserConverter;
    private final HashingService hashingService;
    private final UserConverter userConverter;
    private final UserService userService;

    public AuthenticationResponse login(LoginRequest request) {
        request.setPassword(hashingService.hashAsMD5(request.getPassword()));
        final Optional<User> optional = userService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (optional.isEmpty()) {
            throw new ApiException(ErrorEnum.UNAUTHORIZED);
        }
        final User user = optional.get();

        // check if user is active

        final String token = tokenService.createToken(userConverter.toDto(user));
        return new AuthenticationResponse(token);
    }

    public Authentication getUserAuthentication(HttpServletRequest httpServletRequest) {
        final Claims claims = tokenService.getTokenClaims(httpServletRequest);
        return new UserAuthentication(identityUserConverter.getUser(claims));
    }

    @Transactional
    public void register(RegisterRequest registerRequest) {
        userService.checkEmailIfExist(registerRequest.getEmail());
        registerRequest.setPassword(hashingService.hashAsMD5(registerRequest.getPassword()));
        final User user = userConverter.toEntity(registerRequest);
        userService.save(user);

        // send email to user

    }
}