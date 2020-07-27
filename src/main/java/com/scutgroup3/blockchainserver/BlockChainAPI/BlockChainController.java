package com.scutgroup3.blockchainserver.BlockChainAPI;

import com.scutgroup3.blockchainserver.Redis.redisComponent;
import org.hyperledger.fabric.gateway.ContractException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Controller
public class BlockChainController {
    @Autowired
    private BlockChainService blockChainService;

    @Autowired
    private redisComponent redisComponent;

    /**
     *首页
     */
    @RequestMapping("/")
    public String tempIndex(){
        return "tempIndex";
    }


    /**
     * buyer
     */
    @RequestMapping("/InitGroup")
    @ResponseBody
    public Map<String,Object> InitGroup(){
        Map<String,Object> map = new HashMap<>();




        return map;
    }


    /**
     *  seller
     */
    @RequestMapping("/InitRule")
    @ResponseBody
    public Map<String, Object> InitRule(@RequestParam("goodID") String goodID,
                                          @RequestParam("groupNum") String groupNum,
                                          @RequestParam("firstBuyerPrice") String firstBuyerPrice,
                                          @RequestParam("otherBuyerPrice") String otherBuyerPrice) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String,Object> map = new HashMap<>();
        String functionName = "InitRule";
        String sellerID = blockChainService.getUserName();
        String discountRuleID = UUID.randomUUID().toString().replace("-","");
        String type = "submit";
        String result = blockChainService.sdk(type,functionName,sellerID,discountRuleID,goodID,groupNum,firstBuyerPrice,otherBuyerPrice);
        redisComponent.setList(sellerID,discountRuleID);
        map.put("discountRuleID", discountRuleID);
        map.put("goodID",goodID);
        map.put("groupNum",groupNum);
        map.put("firstBuyerPrice", firstBuyerPrice);
        map.put("otherBuyerPrice",otherBuyerPrice);
        map.put("result",result);
        return map;
    }

    @RequestMapping("/getDiscountRuleID")
    @ResponseBody
    public Map<String, Object> getDiscountRuleID(){
        Map<String, Object> map = new HashMap<>();
        String sellerID = blockChainService.getUserName();
        map.put("result",redisComponent.getList(sellerID));
        return map;
    }

    @RequestMapping("/QueryParticipation")
    @ResponseBody
    public Map<String, Object> QueryParticipation(@RequestParam("discountRuleID") String discountRuleID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "QueryParticipation";
        String type = "evaluate";
        String result = blockChainService.sdk(type,functionName,discountRuleID);
        map.put("discountRuleID", discountRuleID);
        map.put("result", result);
        return map;
    }

    @RequestMapping("/QueryState")
    @ResponseBody
    public Map<String, Object> QueryState(@RequestParam("discountRuleID") String discountRuleID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "QueryState";
        String type = "evaluate";
        String result = blockChainService.sdk(type,functionName,discountRuleID);
        map.put("discountRuleID", discountRuleID);
        map.put("result",result);
        return map;
    }

    @RequestMapping("/Open")
    @ResponseBody
    public Map<String, Object> Open(@RequestParam("discountRuleID") String discountRuleID, @RequestParam("duration") String duration) throws InterruptedException, ContractException, TimeoutException, IOException, SchedulerException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "Open";
        String type = "submit";
        blockChainService.Close(discountRuleID, duration).start();
        String result = blockChainService.sdk(type,functionName,discountRuleID,duration);
        map.put("discountRuleID",discountRuleID);
        map.put("duration", duration);
        map.put("result",result);
        return map;
    }

    /**
     * platform
     */


}
