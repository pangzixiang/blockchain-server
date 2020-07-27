package com.scutgroup3.blockchainserver.BlockChainAPI;

import org.hyperledger.fabric.gateway.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class BlockChainService {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }
    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = "mychaincode";

    public String sdk(String type, String functionName, String...args)
            throws IOException, ContractException, TimeoutException, InterruptedException {
        String role = getRole();
        Path networkConfigPath = Paths.get( "/home/ubuntu/group3BlockChain/profiles", role, "connection.json");
        Path walletPath = Paths.get("/home/ubuntu/group3BlockChain/wallet", role);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, getUserName()).networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);
            byte[] result = null;
            if (type.equals("submit")){
                result = contract.submitTransaction(functionName, args);
            }
            else if (type.equals("evaluate")){
                result = contract.evaluateTransaction(functionName, args);
            }

            return new String(result);
        }
    }

    public String getUserName(){
        //获取当前登录的username
        String currentUser = "";
        Object principl = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principl instanceof UserDetails) {
            currentUser = ((UserDetails)principl).getUsername();
        }else {
            currentUser = principl.toString();
        }
        System.out.println("currentUser" + currentUser);
        return currentUser;
    }

    public String getRole(){
        //获取当前登录的username
        String role = "";
        Object principl = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principl instanceof UserDetails) {
            role = ((UserDetails)principl).getAuthorities().toString().split("_")[1].replace("MSP]","");;
        }else {
            role = principl.toString();
        }
        System.out.println("currentUser" + role);
        return role;
    }

    public Scheduler Close(String discountRuleID, String duration) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobDetail jobDetail = newJob(CloseJob.class).withIdentity("Close")
                .usingJobData("discountRuleID",discountRuleID)
                .usingJobData("contractName",CONTRACT_NAME)
                .usingJobData("userName",getUserName())
                .usingJobData("role",getRole())
                .build();
        Trigger trigger = newTrigger().withIdentity("trigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(Integer.parseInt(duration)).withRepeatCount(1))
                .build();
        scheduler.scheduleJob(jobDetail,trigger);
        return scheduler;
    }
}
