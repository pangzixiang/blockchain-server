package com.scutgroup3.blockchainserver.demo;

import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class demoService {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = "mycc_java02";

    public String doQuery(String orgName, String userName, String functionName, String key)
            throws IOException, ContractException {
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Identity identity = wallet.get(userName);

        if (identity == null) {
            System.out.println("The identity \"" + userName + "@"+ orgName + "\" doesn't exists in the wallet");
            return null;
        }

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.evaluateTransaction(functionName, key);
            return new String(result);
        }
    }
}
