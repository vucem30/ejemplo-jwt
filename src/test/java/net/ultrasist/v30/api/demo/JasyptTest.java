package net.ultrasist.v30.api.demo;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is to show how to use Jasypt to encrypt and decrypt strings
 */
@Slf4j
public class JasyptTest {

    /** 
     * This test is to show how to encrypt a string with a password
     */
    @Test
    public void test() {
        String data="no-reply@kebblar.io";
        String password = "password";
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(password.toCharArray());
        String myEncryptedText = textEncryptor.encrypt(data);
        String plainText = textEncryptor.decrypt(myEncryptedText);
        log.info(myEncryptedText);
        assertEquals(plainText, data);
    }
    
    /** 
     * This test is to show how to decrypt a string that was encrypted with a password
     */
    @Test
    public void decript() {
        String original = "6LeypzsdAAAAANyPUAiSL4J17HurKtioya_3C1-L";
        String data="iWsGWSOHa0r6EdCKoLMc6y7z8L2xeGkNljvsd6Tr18F49Ccn3R2nw7HeHLnOcwRogqnkd7BtMic=";
        String password="password";
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(password.toCharArray());
        String plainText = textEncryptor.decrypt(data);
        log.info(plainText);
        assertEquals(original, plainText);
    }
}

