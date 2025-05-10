package org.univaq.swa.framework.security;
import jakarta.ws.rs.core.UriInfo;
import java.util.UUID;

/**
 *
 * Una classe di utilità di supporto all'autenticazione
 * qui tutto è finto, non usiamo JWT o altre tecnologie
 *
 */
public class AuthHelpers {

    private static AuthHelpers instance = null;

    public AuthHelpers() {

    }

    public boolean authenticateUser(String username, String password) {
        if(username.equals("u") && password.equals("p"))
            return true;
        return false;
    }

    public String issueToken(UriInfo context, String username) {        
        String token = JWTHelpers.getInstance().issueToken(context, username);
        return token;
    }

    public void revokeToken(String token) {
        JWTHelpers.getInstance().revokeToken(token);
    }

    public String validateToken(String token) {
        return JWTHelpers.getInstance().validateToken(token);
  
    }

    public static AuthHelpers getInstance() {
        if (instance == null) {
            instance = new AuthHelpers();
        }
        return instance;
    }

}
