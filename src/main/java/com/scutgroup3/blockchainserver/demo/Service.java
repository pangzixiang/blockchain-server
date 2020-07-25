package com.scutgroup3.blockchainserver.demo;

import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;


@Service
public class Service {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }


    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = "mycc_java02";


    //buyer
    private String doBuyerInit(String orgName, String userName, String functionName, String userID,
                               String sellerID,String groupBuyingID,
                               String goodID, String discountRuleID)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.evaluateTransaction(functionName, userID, sellerID, groupBuyingID, goodID, discountRuleID);
            return new String(result);
        }
    }

    private String doParticipate(String orgName, String userName, String functionName, String userID, String groupBuyingID)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.evaluateTransaction(functionName, userID, groupBuyingID);
            return new String(result);
        }
    }

    private String doQueryGroupBuying(String orgName, String userName, String functionName, String groupBuyingID)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.evaluateTransaction(functionName, groupBuyingID);
            return new String(result);
        }
    }

    //seller
    private String doSellerInit(String orgName, String userName, String functionName, String sellerID, String discountRuleID, String goodID, String groupNum, String firstBuyerPrice, String otherBuyerPrice)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.evaluateTransaction(functionName, sellerID, discountRuleID, goodID, groupNum, firstBuyerPrice, otherBuyerPrice);
            return new String(result);
        }
    }

    private String doOpen(String orgName, String userName, String functionName, String discountRuleID, String duration)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.submitTransaction(functionName, discountRuleID, duration);
            return new String(result);
        }
    }

    private String doClose(String orgName, String userName, String functionName, String discountRuleID)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.submitTransaction(functionName, discountRuleID);
            return new String(result);
        }
    }

    private String doQueryParticipation(String orgName, String userName, String functionName, String discountRuleID)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, discountRuleID);
            return new String(result);
        }
    }

    private String doQueryState(String orgName, String userName, String functionName, String discountRuleID)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, discountRuleID);
            return new String(result);
        }
    }

    //platform
    private String doInitCredit(String orgName, String userName, String functionName, String userID)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, userID);
            return new String(result);
        }
    }

    private String doChangeCredit(String orgName, String userName, String functionName, String userID, String changeValue)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, userID, changeValue);
            return new String(result);
        }
    }

    private String doInitTrans(String orgName, String userName, String functionName, String discountRuleID, String groupBuyingID)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, discountRuleID, groupBuyingID);
            return new String(result);
        }
    }

    private String doChangeTrans(String orgName, String userName, String functionName, String transID, String transState)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, transID, transState);
            return new String(result);
        }
    }

    private String doQueryTrans(String orgName, String userName, String functionName, String transID)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, transID);
            return new String(result);
        }
    }

    private String doQueryCredit(String orgName, String userName, String functionName, String userID)
            throws IOException, ContractException{
        Path walletPath = Paths.get("/home/ubuntu/workspace/app/example02_java/wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get( "/home/ubuntu/workspace/app/example02_java/profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            //评估从分类账查询状态的交易
            byte[] result = contract.evaluateTransaction(functionName, userID);
            return new String(result);
        }
    }
}
