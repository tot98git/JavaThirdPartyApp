package thirdPartyToken;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class CryptographyUtil {

    private static final String ALGORITHM = "RSA";
    private byte[] publicKey,privateKey;
    	private KeyPair keypair;
    	private byte[] encryptedData; 
    	private byte[] decryptedData;
    	public CryptographyUtil(String value){
    		try {
				setKeypair();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		setPublicKey();
    		setPrivateKey();
    		setEncryptedData(value);
    		setDecryptedData();
    		
    	}
    	public byte[] getEncryptedData() {
			return encryptedData;
		}
		public void setEncryptedData(String value) {
	        try {
				this.encryptedData = encrypt(getPublicKey(), value.getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public byte[] getDecryptedData() {
			return decryptedData;
		}
		public void setDecryptedData() {
			try {
				this.decryptedData = decrypt(getPrivateKey(),getEncryptedData());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
    public byte[] getPublicKey() {
			return publicKey;
		}

		public void setPublicKey() {
	        publicKey = this.keypair.getPublic().getEncoded();
		}

		public byte[] getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey() {
	        privateKey = this.keypair.getPrivate().getEncoded();
		}

		public KeyPair getKeypair() {
			return keypair;
		}

		public void setKeypair() throws NoSuchAlgorithmException, NoSuchProviderException {
			this.keypair = generateKeyPair();
		}

	public static byte[] encrypt(byte[] publicKey, byte[] inputData)
            throws Exception {

        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(publicKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.PUBLIC_KEY, key);

        byte[] encryptedBytes = cipher.doFinal(inputData);

        return encryptedBytes;
    }

    public static byte[] decrypt(byte[] privateKey, byte[] inputData)
            throws Exception {

        PrivateKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(privateKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.PRIVATE_KEY, key);

        byte[] decryptedBytes = cipher.doFinal(inputData);

        return decryptedBytes;
    }

    public static KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // 512 is keysize
        keyGen.initialize(512, random);

        KeyPair generateKeyPair = keyGen.generateKeyPair();
        return generateKeyPair;
    }

    public static void run(String value) throws Exception {

        KeyPair generateKeyPair = generateKeyPair();

        byte[] publicKey = generateKeyPair.getPublic().getEncoded();
        byte[] privateKey = generateKeyPair.getPrivate().getEncoded();

        byte[] encryptedData = encrypt(publicKey,
                value.getBytes());

        byte[] decryptedData = decrypt(privateKey, encryptedData);
        System.out.println(new String(encryptedData));
        System.out.println(new String(decryptedData));

    }

}
