import static org.junit.Assert.*;

import org.junit.Test;

public class RC4Test {

    @Test
    public void testKeystream() {
        RC4 r = new RC4(new int[]{27, 250, 10, 19, 81, 42, 66});
        byte b1 = r.getKeystreamByte();
        assertEquals(70,b1);
        byte b2 = r.getKeystreamByte();
        assertEquals(32,b2);
    }
    
    @Test
    public void testEncrypt() {
        int[] key = new int[]{19, 50, 10, 219, 8, 24, 6};
        byte[] ciphertext = RC4.encrypt(key, "Hello world.");
        assertEquals("-20 -80 98 80 -61 54 -45 102 23 67 -111 47 ", 
                RC4.byteArrayToString(ciphertext));
        ciphertext = RC4.encrypt(key, "The Lannisters send their regards.");
        assertEquals("-16 -67 107 28 -32 119 -54 103 12 92 -127 100 31 25 -48 -32 78 -2 87 76 -23 -125 -83 57 -18 74 60 -116 -93 81 114 -29 -116 -75 ",
                RC4.byteArrayToString(ciphertext));
    }

    @Test
    public void testDecrypt() {
        int[] key = new int[]{72, 50, 10, 219, 8, 24, 6};
        String plaintext = "Gentlemen do not read each other's mail.";
        byte[] ciphertext = RC4.encrypt(key, plaintext);
        assertEquals(plaintext, RC4.decrypt(key, ciphertext));
    }
}