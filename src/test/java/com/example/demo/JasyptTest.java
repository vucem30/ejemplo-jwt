package net.ultrasist.demo;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JasyptTest {

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
    
    @Test
    public void decript() {
        String data="iWsGWSOHa0r6EdCKoLMc6y7z8L2xeGkNljvsd6Tr18F49Ccn3R2nw7HeHLnOcwRogqnkd7BtMic=";
        //data ="e973S3ZorUqIBo31/ClwTMzP+dLO9tFx";
        String password="password";
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(password.toCharArray());
        String plainText = textEncryptor.decrypt(data);
        log.info(plainText);
        assertEquals(data, data);
    }
}

