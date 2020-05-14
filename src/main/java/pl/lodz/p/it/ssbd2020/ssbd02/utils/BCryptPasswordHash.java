package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.PasswordHash;

/**
 * Klasa generuje skrót oraz weryfikuje hasło za pomocą algorytmu BCrypt.
 */
@ApplicationScoped
public class BCryptPasswordHash implements PasswordHash {
    private static final int logRounds = 10;

    /**
     * Metoda generująca skrót hasła.
     *
     * @param password hasło
     * @return wygenerowany skrót hasła
     */
    @Override
    public String generate(char[] password) {
        String salt = BCrypt.gensalt(logRounds);
        return BCrypt.hashpw(String.valueOf(password), salt);
    }

    /**
     * Metoda weryfikująca zgodność hasła ze skrótem.
     *
     * @param password hasło do weryfikacji
     * @param hash     skrót hasła
     * @return wartość logiczna
     */
    @Override
    public boolean verify(char[] password, String hash) {
        try {
            return BCrypt.checkpw(String.valueOf(password), hash);
        } catch (Exception e) {
            return false;
        }
    }
}