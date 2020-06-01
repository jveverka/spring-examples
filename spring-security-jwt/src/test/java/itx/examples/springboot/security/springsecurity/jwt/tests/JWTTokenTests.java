package itx.examples.springboot.security.springsecurity.jwt.tests;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import itx.examples.springboot.security.springsecurity.jwt.services.JWTUtils;
import itx.examples.springboot.security.springsecurity.jwt.services.KeyStoreInitializationException;
import itx.examples.springboot.security.springsecurity.jwt.services.KeyStoreService;
import itx.examples.springboot.security.springsecurity.jwt.services.KeyStoreServiceImpl;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.UserId;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JWTTokenTests {

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateAndVerifyJWTToken() throws KeyStoreInitializationException {
        KeyStoreService keyStoreService = new KeyStoreServiceImpl();
        long nowDate = LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.ofHours(0))*1000;
        long expirationDate = (nowDate + 3600*24)*1000;
        UserId userName = UserId.from("UserName");
        String issuer = "Issuer";
        Set<String> roles = Sets.newSet("ROLE_USER", "ROLE_ADMIN");
        Key key = keyStoreService.createUserKey(userName);
        assertNotNull(key);

        //1. create JWT token
        String jwToken = Jwts.builder()
                .setSubject(userName.getId())
                .signWith(key)
                .setExpiration(new Date(expirationDate))
                .setIssuer(issuer)
                .setIssuedAt(new Date(nowDate))
                .claim("roles", roles)
                .compact();
        assertNotNull(jwToken);

        //2. verify JWT token
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwToken);
        assertNotNull(claimsJws);
        List<String> rolesFromJWT = (List<String>)claimsJws.getBody().get("roles");
        assertNotNull(rolesFromJWT);
        assertEquals(2, rolesFromJWT.size());

        String subjectFromJWT = claimsJws.getBody().getSubject();
        assertEquals(userName, UserId.from(subjectFromJWT));

        String issuerFromJWT = claimsJws.getBody().getIssuer();
        assertEquals(issuer, issuerFromJWT);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testReadJWTTokenWithoutVerification() throws KeyStoreInitializationException {
        KeyStoreService keyStoreService = new KeyStoreServiceImpl();
        long nowDate = LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.ofHours(0))*1000;
        long expirationDate = (nowDate + 3600*24)*1000;
        UserId userName = UserId.from("UserName");
        String issuer = "Issuer";
        Set<String> roles = Sets.newSet("ROLE_USER", "ROLE_ADMIN");
        Key key = keyStoreService.createUserKey(userName);
        assertNotNull(key);

        //1. create JWT token
        String jwToken = Jwts.builder()
                .setSubject(userName.getId())
                .signWith(key)
                .setExpiration(new Date(expirationDate))
                .setIssuer(issuer)
                .setIssuedAt(new Date(nowDate))
                .claim("roles", roles)
                .compact();
        assertNotNull(jwToken);

        //2. read content of JWT token without signature (without knowing proper key)
        String jwtTokenWithoutSignature = JWTUtils.removeSignature(jwToken);
        JwtParser parser = Jwts.parserBuilder().build();
        Jwt jwt = parser.parse(jwtTokenWithoutSignature);
        DefaultClaims body = (DefaultClaims)jwt.getBody();
        String subjectFromJWT = body.get(Claims.SUBJECT, String.class);
        assertEquals(userName, UserId.from(subjectFromJWT));
    }

    @Test
    public void testKeystoreServiceCache() throws KeyStoreInitializationException {
        KeyStoreService keyStoreService = new KeyStoreServiceImpl();
        UserId userName = UserId.from("UserName");
        Key userNameKey = keyStoreService.createUserKey(userName);
        assertNotNull(userNameKey);
        Optional<Key> userKey = keyStoreService.getUserKey(userName);
        assertTrue(userKey.isPresent());
        boolean removed = keyStoreService.removeUserKey(userName);
        assertTrue(removed);
        userKey = keyStoreService.getUserKey(userName);
        assertTrue(userKey.isEmpty());
    }

}
