package org.ra.util;

import org.mindrot.jbcrypt.BCrypt;

public class Validation {
    //hash mật khẩu
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    // kiểm tra mật khẩu khi đăng nhập
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
