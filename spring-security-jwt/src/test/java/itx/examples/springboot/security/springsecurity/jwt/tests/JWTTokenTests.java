package itx.examples.springboot.security.springsecurity.jwt.tests;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.lang.Assert;
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

public class JWTTokenTests {

    @Test
    public void testCreateAndVerifyJWTToken() throws KeyStoreInitializationException {
        KeyStoreService keyStoreService = new KeyStoreServiceImpl();
        long nowDate = LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.ofHours(0))*1000;
        long expirationDate = (nowDate + 3600*24)*1000;
        UserId userName = UserId.from("UserName");
        String issuer = "Issuer";
        Set<String> roles = Sets.newSet("ROLE_USER", "ROLE_ADMIN");
        Key key = keyStoreService.createUserKey(userName);
        Assert.notNull(key);

        //1. create JWT token
        String jwToken = Jwts.builder()
                .setSubject(userName.getId())
                .signWith(key)
                .setExpiration(new Date(expirationDate))
                .setIssuer(issuer)
                .setIssuedAt(new Date(nowDate))
                .claim("roles", roles)
                .compact();
        Assert.notNull(jwToken);

        //2. verify JWT token
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwToken);
        Assert.notNull(claimsJws);
        List<String> rolesFromJWT = (List<String>)claimsJws.getBody().get("roles");
        Assert.notNull(rolesFromJWT);
        Assert.isTrue(rolesFromJWT.size() == 2);

        String subjectFromJWT = claimsJws.getBody().getSubject();
        Assert.isTrue(userName.equals(UserId.from(subjectFromJWT)));

        String issuerFromJWT = claimsJws.getBody().getIssuer();
        Assert.isTrue(issuer.equals(issuerFromJWT));
    }

    @Test
    public void testReadJWTTokenWithoutVerification() throws KeyStoreInitializationException {
        KeyStoreService keyStoreService = new KeyStoreServiceImpl();
        long nowDate = LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.ofHours(0))*1000;
        long expirationDate = (nowDate + 3600*24)*1000;
        UserId userName = UserId.from("UserName");
        String issuer = "Issuer";
        Set<String> roles = Sets.newSet("ROLE_USER", "ROLE_ADMIN");
        Key key = keyStoreService.createUserKey(userName);
        Assert.notNull(key);

        //1. create JWT token
        String jwToken = Jwts.builder()
                .setSubject(userName.getId())
                .signWith(key)
                .setExpiration(new Date(expirationDate))
                .setIssuer(issuer)
                .setIssuedAt(new Date(nowDate))
                .claim("roles", roles)
                .compact();
        Assert.notNull(jwToken);

        //2. read content of JWT token without signature (without knowing proper key)
        String jwtTokenWithoutSignature = JWTUtils.removeSignature(jwToken);
        JwtParser parser = Jwts.parserBuilder().build();
        Jwt jwt = parser.parse(jwtTokenWithoutSignature);
        DefaultClaims body = (DefaultClaims)jwt.getBody();
        String subjectFromJWT = body.get(Claims.SUBJECT, String.class);
        Assert.isTrue(userName.equals(UserId.from(subjectFromJWT)));
    }

    @Test
    public void testKeystoreServiceCache() throws KeyStoreInitializationException {
        KeyStoreService keyStoreService = new KeyStoreServiceImpl();
        UserId userName = UserId.from("UserName");
        Key userNameKey = keyStoreService.createUserKey(userName);
        Assert.notNull(userNameKey);
        Optional<Key> userKey = keyStoreService.getUserKey(userName);
        Assert.isTrue(userKey.isPresent());
        boolean removed = keyStoreService.removeUserKey(userName);
        Assert.isTrue(removed);
        userKey = keyStoreService.getUserKey(userName);
        Assert.isTrue(userKey.isEmpty());
    }

}
