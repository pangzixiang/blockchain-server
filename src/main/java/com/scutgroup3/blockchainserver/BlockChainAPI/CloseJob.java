package com.scutgroup3.blockchainserver.BlockChainAPI;

import org.hyperledger.fabric.gateway.*;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

public class CloseJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String functionName = "Close";
        String discountRuleID = jobExecutionContext.getJobDetail().getJobDataMap().getString("discountRuleID");
        String contractName = jobExecutionContext.getJobDetail().getJobDataMap().getString("contractName");
        String userName = jobExecutionContext.getJobDetail().getJobDataMap().getString("userName");
        String role = jobExecutionContext.getJobDetail().getJobDataMap().getString("role");
        try {
            String result = sdk(contractName,userName,role,functionName,discountRuleID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }
    private static final String CHANNEL_NAME = "mychannel";

    public String sdk(String contractName,String userName,String role,String functionName, String...args)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        String CONTRACT_NAME = contractName;
        Path networkConfigPath = Paths.get( "/home/ubuntu/group3BlockChain/profiles", role, "connection.json");
        Path walletPath = Paths.get("/home/ubuntu/group3BlockChain/wallet", role);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = null;
            result = contract.submitTransaction(functionName, args);

            return new String(result);
        }
    }
}
