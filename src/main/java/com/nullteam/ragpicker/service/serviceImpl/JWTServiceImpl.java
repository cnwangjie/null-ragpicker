package com.nullteam.ragpicker.service.serviceImpl;

import com.nullteam.ragpicker.config.JWTConfig;
import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.repository.CollectorRepository;
import com.nullteam.ragpicker.repository.UserRepository;
import com.nullteam.ragpicker.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {

    private final JWTConfig jwtConfig;

    private final UserRepository userRepository;

    private final CollectorRepository collectorRepository;

    @Autowired
    public JWTServiceImpl(UserRepository userRepository, JWTConfig jwtConfig, CollectorRepository collectorRepository) {
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
        this.collectorRepository = collectorRepository;
    }

    private String genJWTToken(Map claims) {
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date((new Date()).getTime() + 360000000))
            .signWith(SignatureAlgorithm.HS512, jwtConfig.getJWTSecret())
            .compact();
    }

    private Claims parseJWTToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getJWTSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = new DefaultClaims();
        }
        return claims;
    }

    @Override
    public String genUserToken(User user) {
        Map claims = new HashMap();
        claims.put("sub", user.getId());
        claims.put("idt", "user");
        return genJWTToken(claims);
    }

    @Override
    public String genCollectorToken(Collector collector) {
        Map claims = new HashMap();
        claims.put("sub", collector.getId());
        claims.put("idt", "collector");
        return genJWTToken(claims);
    }

    @Override
    public String getIdentityFromToken(String token) {
        return (String) parseJWTToken(token).get("idt");
    }

    @Override
    public User getUserFromToken(String token) {
        String id = parseJWTToken(token).getSubject();
        if (id == null) return null;
        return userRepository.findOne(Integer.valueOf(id));
    }

    @Override
    public Collector getCollectorFromToken(String token) {
        String id = parseJWTToken(token).getSubject();
        if (id == null) return null;
        return collectorRepository.findOne(Integer.valueOf(id));
    }
}
