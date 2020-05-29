package itx.examples.springboot.security.springsecurity.jwt.services;

import itx.examples.springboot.security.springsecurity.jwt.services.dto.UserId;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KeyStoreServiceImpl implements KeyStoreService {

    private final KeyStore keystore;
    private final Key caKey;
    private final Map<UserId, KeyPair> keyCache;
    private final KeyPairGenerator keyPairGenerator;

    public KeyStoreServiceImpl() throws KeyStoreInitializationException {
        try {
            keystore = KeyStore.getInstance("JKS");
            InputStream is = KeyStoreServiceImpl.class.getResourceAsStream("/keystore.jks");
            keystore.load(is, "secret".toCharArray());
            caKey = keystore.getKey("organization", "secret".toCharArray());
            keyCache = new ConcurrentHashMap<>();
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");
            keyPairGenerator.initialize(2048, secureRandom);
        } catch (Exception e) {
            throw new KeyStoreInitializationException(e);
        }
    }

    @Override
    public Key getCertificationAuthorityKey() {
        return caKey;
    }

    @Override
    public Key createUserKey(UserId userId) {
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //TODO: sign user's certificate using company CA.
        keyCache.put(userId, keyPair);
        return keyPair.getPrivate();
    }

    @Override
    public Optional<Key> getUserKey(UserId userId) {
        KeyPair pair = keyCache.get(userId);
        if (pair != null) {
            return Optional.of(keyCache.get(userId).getPrivate());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean removeUserKey(UserId userId) {
        return keyCache.remove(userId) != null;
    }

}
