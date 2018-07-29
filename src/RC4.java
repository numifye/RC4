// Author: Naomi Campbell

public class RC4 {
    private int[] s; // should be unsigned bytes, but alas we are programming in Java
    private int i, j;
    
    /**
     * key should be unsigned bytes, but this is Java...
     */
    public RC4(int[] key) {
        if (key.length<1 || key.length > 256) {
            throw new RuntimeException("Key length must be between 1 and 256 bytes");
        }
        
        // initialize s array with initial permutation
        s = new int[256];
        int[] k = new int[256];
        for(i = 0; i <= 255; i++){
        	s[i] = i;
        	// k filled by repeating key until array is full
        	k[i] = key[i % key.length];
        }
        j = 0;
        for(i = 0; i <= 255; i++){
        	j = (j + s[i] + k[i]) % 256;
        	swap(s, i, j);
        }
        i = j = 0;
    }
    
    /**
     * Return the next byte of the keystream.
     */
    public byte getKeystreamByte() {
        int keystreamByte = 0;
        
        // my code below
        i = (i + 1) % 256;
        j = (j + s[i]) % 256;
        swap(s, i, j);
        int t = (s[i] + s[j]) % 256;
        keystreamByte = s[t];
        
        return posIntToByte(keystreamByte);
    }
 
    /**
     * Helper method swaps the elements at positions i and j of the s array
     */
    private void swap(int[] s, int i, int j) {
        int tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }
    
    /**
     * Converts a positive int (0-256) to a byte (-128-127).
     * We need this method because Java does not have unsigned bytes.
     */
    private byte posIntToByte(int i) {
        int b = i>=128 ? i-256 : i;
        return (byte) b;
    }
    
    /**
     * Encrypts the plaintext s with the specified key.
     * (Use the bitwise operator '^').
     * Returns the ciphertext as an array of bytes.
     */
    public static byte[] encrypt(int[] key, String s) {
        RC4 r = new RC4(key);
        byte[] ciphertext = new byte[s.length()];
        // encrypt each character with a different keystream byte
        byte keyStreamByte, currentToByte, resultXOR;
        for(int n = 0; n < s.length(); n++){
        	currentToByte = (byte) s.charAt(n);;
        	keyStreamByte = r.getKeystreamByte();
        	resultXOR = (byte) (currentToByte ^ keyStreamByte);
        	ciphertext[n] = resultXOR;
        }
        return ciphertext;
    }
    
    /**
     * Decrypts ciphertext using key, returning a String
     * of the original plaintext.
     */
    public static String decrypt(int[] key, byte[] ciphertext) {
        RC4 r = new RC4(key);
        char[] plaintext = new char[ciphertext.length];

        byte keyStreamByte, resultXOR;
        char byteToChar;
        for(int n = 0; n < ciphertext.length; n++){
        	keyStreamByte = r.getKeystreamByte();
        	resultXOR = (byte) (ciphertext[n] ^ keyStreamByte);
        	byteToChar = (char) resultXOR;
        	plaintext[n] = byteToChar;
        }
        
        return new String(plaintext);
    }
    
    /**
     * Helper method to print a byte 
     */
    public static String byteArrayToString(byte[] arr) {
        String s = "";
        for (byte b : arr) {
            s += ((int) b) + " ";
        }
        return s;
    }
    
}