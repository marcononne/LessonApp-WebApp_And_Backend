package utils;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

public class SessionManager {

    private static final HashMap<String, HttpSession> tokens = new HashMap<>();

    public static String createToken(HttpSession session) {
        byte[] bytes = new byte[16];
        new Random().nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        tokens.put(token, session);
        return token;
    }

    public static String checkToken(String token) {
        if (tokens.containsKey(token))
            return (String) tokens.get(token).getAttribute("role");
        else
            return "";
    }

    public static void invalideSession(String token) {
        tokens.get(token).invalidate();
        tokens.remove(token);
    }
}
