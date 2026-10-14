/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.digitalsignature;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class DigitalSignature {

    public static void main(String[] args) throws Exception {

        // Generate the RSA key pair
        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        KeyPair rsaKeys = generator.generateKeyPair();

        // Define the message to be signed
        String originalText = "Transfer $1000 to Mark";

        // Create a digital signature using the private key
        Signature digitalSigner =
                Signature.getInstance("SHA256withRSA");

        digitalSigner.initSign(rsaKeys.getPrivate());
        digitalSigner.update(
                originalText.getBytes(StandardCharsets.UTF_8));

        byte[] signedData = digitalSigner.sign();

        // Convert the signature to readable text
        String encodedSignature =
                Base64.getEncoder().encodeToString(signedData);

        System.out.println("Message: " + originalText);
        System.out.println("Signature: " + encodedSignature);

        // Check the signature using the public key
        Signature signatureChecker =
                Signature.getInstance("SHA256withRSA");

        signatureChecker.initVerify(rsaKeys.getPublic());
        signatureChecker.update(
                originalText.getBytes(StandardCharsets.UTF_8));

        boolean originalResult =
                signatureChecker.verify(signedData);

        System.out.println(
                "Original Message Valid: " + originalResult);

        // Modify the message to test signature verification
        String changedText = "Transfer $1000 to Oscar";

        signatureChecker.initVerify(rsaKeys.getPublic());
        signatureChecker.update(
                changedText.getBytes(StandardCharsets.UTF_8));

        boolean changedResult =
                signatureChecker.verify(signedData);

        System.out.println(
                "Modified Message Valid: " + changedResult);
    }
}
