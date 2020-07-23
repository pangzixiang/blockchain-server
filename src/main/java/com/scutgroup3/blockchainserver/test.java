package com.scutgroup3.blockchainserver;

import com.scutgroup3.blockchainserver.Login.person;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test {
    public static void main(String[] args) throws IOException {
        String rootPath = "C:/Users/PZX/Desktop/";
        String walletPath = rootPath+"user01"+".id";
        person person = null;
        String content = "{\"version\":1,\"mspId\":\"Org1MSP\",\"type\":\"X.509\",\"credentials\":{\"certificate\":\"-----BEGIN CERTIFICATE-----\\nMIICUDCCAfegAwIBAgIUK3KorcCoWjDrnKz53GhE7TnTuC8wCgYIKoZIzj0EAwIw\\ncDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQH\\nEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2Nh\\nLm9yZzEuZXhhbXBsZS5jb20wHhcNMjAwNzIzMDIyNTAwWhcNMjEwNzIzMDIzMDAw\\nWjAiMQ8wDQYDVQQLEwZjbGllbnQxDzANBgNVBAMTBnVzZXIwMTBZMBMGByqGSM49\\nAgEGCCqGSM49AwEHA0IABLiWSh7rZs/d/ksoa/3M7NOHI1sJXsxTWHIf1bUi0soL\\nxjdAjojf+hwBuBMv8QqPf+RD5S/l8Mui4QOSS0nKIsSjgbwwgbkwDgYDVR0PAQH/\\nBAQDAgeAMAwGA1UdEwEB/wQCMAAwHQYDVR0OBBYEFIs5ZnkG/KY6oFAV1tx3HhI9\\nLjrHMB8GA1UdIwQYMBaAFL3QpXocHGqOim7dWZoS5QwTSqByMFkGCCoDBAUGBwgB\\nBE17ImF0dHJzIjp7ImhmLkFmZmlsaWF0aW9uIjoiIiwiaGYuRW5yb2xsbWVudElE\\nIjoidXNlcjAxIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNHADBE\\nAiBU87TXVZQ1d5VK59hCzjda1yYvcxgzF1w28CpJmmy4IAIgRIphIu+YH6dcvVQA\\nB5y/SCd1tfyl2sUbJ2HtssA1mkY=\\n-----END CERTIFICATE-----\\n\",\"privateKey\":\"-----BEGIN PRIVATE KEY-----\\nMIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgFqSUONRzDRmQ5KUs\\nUiCq7LvK9FzjaGPbn3JmWzfFgIKgCgYIKoZIzj0DAQehRANCAAS4lkoe62bP3f5L\\nKGv9zOzThyNbCV7MU1hyH9W1ItLKC8Y3QI6I3/ocAbgTL/EKj3/kQ+Uv5fDLouED\\nkktJyiLE\\n-----END PRIVATE KEY-----\\n\"}}";
        try {
            BufferedReader in = new BufferedReader(new FileReader(walletPath));
            String str;
            while ((str = in.readLine()) != null) {
                 person = new person("user01",str);
            }
        } catch (IOException e) {
        }
        System.out.println(person.getId());

        Wallet wallet = Wallets.newFileSystemWallet(Paths.get(rootPath));
        System.out.println(wallet.get("user01").getMspId());
    }
}
