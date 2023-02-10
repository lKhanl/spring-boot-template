package dev.oguzhanercelik.service;

import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.error.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
@Service
public class HashingService {

    public String hashAsMD5(String password) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] value = password.getBytes(StandardCharsets.UTF_16LE);
            byte[] a = m.digest(value);
            return Base64.getEncoder().encodeToString(a);
        } catch (Exception ex) {
            log.error("Exception occurred while password hashing: {}", ex.getMessage());
            throw new ApiException(ErrorEnum.USER_NOT_CREATED);
        }
    }
}