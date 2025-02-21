package org.example.board_project;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptApplicationTests {
    String encryptKey;

    @Test
    void contextLoads() {
    }

    @Test
    void jasypt() {
        encryptKey = System.getProperty("jasypt.encryptor.password");
        String url = "";
        String user = "";
        String password = "";

        String encryptUrl = jasyptEncoding(url);
        String encryptUser = jasyptEncoding(user);
        String encryptPwd = jasyptEncoding(password);


        System.out.println("encrypt url : " + encryptUrl);
        System.out.println("encrypt user : " + encryptUser);
        System.out.println("encrypt pwd : " + encryptPwd);
        System.out.println("decrypt url : " + jasyptDecrypt(encryptUrl));
        System.out.println("decrypt url : " + jasyptDecrypt(encryptUser));
        System.out.println("decrypt url : " + jasyptDecrypt(encryptPwd));

        Assertions.assertThat(url).isEqualTo(jasyptDecrypt(encryptUrl));
    }

    private String jasyptDecrypt(String val) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(encryptKey);
        return encryptor.decrypt(val);

    }

    private String jasyptEncoding(String val) {
        StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
        enc.setPassword(encryptKey);
        enc.setAlgorithm("PBEWithMD5AndDES");
        return enc.encrypt(val);
    }
}
