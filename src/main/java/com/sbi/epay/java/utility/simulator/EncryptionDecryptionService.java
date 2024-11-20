package com.sbi.epay.java.utility.simulator;

import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.service.KeyGeneratorService;
import com.sbi.epay.encryptdecrypt.service.KeyProviderService;
import com.sbi.epay.encryptdecrypt.util.*;

import javax.crypto.SecretKey;

public class EncryptionDecryptionService {
    public static void main(String[] args) throws Exception {
        try {
            EncryptionService encryptionService = new EncryptionService();
            DecryptionService decryptionService = new DecryptionService();
            KeyGeneratorService keyGeneratorService = new KeyGeneratorService();
            KeyProviderService keyProviderService = new KeyProviderService();

            //Step 1 : Key generator
            String aek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println("aek: " + aek);
            String kek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println("kek: " + kek);
            String mek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println("mek: " + mek);

            //Step 2 : Key Encryption
            String encryptedKeK = encryptionService.encryptSecretKey(kek, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("encryptedKeK : " + encryptedKeK);
            String encryptedMeK = encryptionService.encryptSecretKey(mek, kek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("encryptedMeK : " + encryptedMeK);

            //Step : Key Decryption
            kek = decryptionService.decryptKey(encryptedKeK, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("decryptedKek : " + kek);
            mek = decryptionService.decryptKey(encryptedMeK, kek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("decryptedMek : " + mek);

            //Step 3 : Key Provider
            SecretKey mekSecretKey = keyProviderService.getDecryptedMEK(encryptedMeK, encryptedKeK, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("mekSecretKey : " + mekSecretKey);

            //Step 4 : Value Encryption by Key
            String json = "{\"name\":\"ranjan\",\"phoneNumber\":\"9818809058\",\"email\":\"ran@gmail.com\",\"gstIn\":\"27AAACS8577K2ZO\"}";
            byte[] encryptedValue = encryptionService.encryptValue(mekSecretKey, json, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("encryptedValue : " + encryptedValue);

            //Step 5 : Value decryption by Key
            String decryptionValue = decryptionService.decryptValue(encryptedValue, mekSecretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("decryptionValue : " + decryptionValue);
        } catch (NullPointerException e) {
            throw new Exception(e.getMessage(), e);
        }
    }
}