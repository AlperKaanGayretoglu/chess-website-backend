package com.alpergayretoglu.chess_website_backend.security;

import com.alpergayretoglu.chess_website_backend.config.SecurityConfig;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final SecurityConfig securityConfig;
    private final UserRepository userRepository;

    @Autowired
    public JwtService(SecurityConfig securityConfig, UserRepository userRepository) {
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
    }

    public Authentication verifyToken(String token, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                byte[] signingKey = securityConfig.getJwtSecret().getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                String subject = parsedToken
                        .getBody()
                        .getSubject();

                if (StringUtils.isNotEmpty(subject)) {
                    User user = userRepository.findById(subject).orElseThrow(() -> new BusinessException(ErrorCode.AUTHENTICATED_USER_NOT_FOUND));
                    return new UsernamePasswordAuthenticationToken(subject, null, user.getAuthorities());
                }
            } catch (ExpiredJwtException exception) {
                logger.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                logger.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                logger.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                logger.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                logger.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }
        return null;
    }

    public String createToken(String subject) {
        byte[] signingKey = securityConfig.getJwtSecret().getBytes(StandardCharsets.UTF_8);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .compact();
    }

}
