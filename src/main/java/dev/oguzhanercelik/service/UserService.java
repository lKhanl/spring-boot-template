package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.UserConverter;
import dev.oguzhanercelik.entity.User;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.dto.UserDto;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public void checkEmailIfExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ApiException(ErrorEnum.EMAIL_ALREADY_EXIST);
        }
    }

    public UserDto getUserInfo(Long uid) {
        final User merchantUser = userRepository.findById(uid)
                .orElseThrow(() -> new ApiException(ErrorEnum.USER_NOT_FOUND));
        return userConverter.toDto(merchantUser);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
