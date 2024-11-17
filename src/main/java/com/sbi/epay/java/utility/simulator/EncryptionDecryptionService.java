package com.sbi.epay.java.utility.simulator;

import com.sbi.epay.encryptdecrypt.constant.EncryptionDecryptionConstants;
import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.service.KeyGeneratorService;
import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;
import com.sbi.epay.encryptdecrypt.util.KeyGenerationAlgo;
import com.sbi.epay.encryptdecrypt.util.SecretKeyLength;

import javax.crypto.SecretKey;
import java.text.MessageFormat;

public class EncryptionDecryptionService {
    public static void main(String[] args) throws Exception {
        try {
            KeyGeneratorService keyGeneratorService = new KeyGeneratorService();
            String s = keyGeneratorService.generateKeyByDefaultAlgo(SecretKeyLength.AES_128);
            System.out.println(s);
            SecretKey secretKey = keyGeneratorService.getSecretKey(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println(secretKey);
            EncryptionService encryptionService = new EncryptionService();
            byte[] bytes = encryptionService.encryptValue(secretKey, "{name : Ranu}", EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println(bytes);
            DecryptionService decryptionService = new DecryptionService();
            String decryptionValue = decryptionService.decryptValue(bytes, secretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println(decryptionValue);
        } catch (NullPointerException e) {
            throw new Exception(e.getMessage() ,e);
        }
    }
}