package utils;

import org.apache.commons.codec.digest.DigestUtils;

public class CryptPassword {
    public static String encryptSHA2(String testoChiaro){
        String key = DigestUtils.sha256Hex(testoChiaro).toUpperCase();
        return key;
    }
}
