package com.scutgroup3.blockchainserver.BlockChainAPI;

import com.scutgroup3.blockchainserver.Redis.redisComponent;
import org.hyperledger.fabric.gateway.ContractException;
import org.omg.CORBA.OBJ_ADAPTER;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Controller
@CrossOrigin
public class BlockChainController {
    @Autowired
    private BlockChainService blockChainService;

    @Autowired
    private redisComponent redisComponent;

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("name",blockChainService.getUserName());
        map.put("role", blockChainService.getRole());
        return map;
    }

    /**
     *首页
     */
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("username",blockChainService.getUserName());
        model.addAttribute("role",blockChainService.getRole());
        return "index";
    }

    @RequestMapping("/{api}")
    public String api(@PathVariable String api){
        return api;
    }

    @RequestMapping("/APIDoc")
    public String APIDoc(){
        return "APIDoc";
    }


    /**
     * buyer
     */
    @RequestMapping(value = "/InitGroup", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> InitGroup(@RequestParam("discountRuleID") String discountRuleID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String,Object> map = new HashMap<>();
        String userID = blockChainService.getUserName();
        String groupBuyingID = UUID.randomUUID().toString().replace("-","");
        String functionName = "InitGroup";
        String type = "submit";
        String result = blockChainService.sdk(type,functionName,userID,groupBuyingID,discountRuleID);
        redisComponent.setList(userID+"-Init",groupBuyingID);
        map.put("groupBuyingID",groupBuyingID);
        map.put("discountRuleID",discountRuleID);
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/getInitGroupBuyingID", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getGroupBuyingID(){
        Map<String, Object> map = new HashMap<>();
        String userID = blockChainService.getUserName();
        map.put("result",redisComponent.getList(userID+"-Init"));
        return map;
    }

    @RequestMapping(value = "/getParticipate", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getParticipate(){
        Map<String, Object> map = new HashMap<>();
        String userID = blockChainService.getUserName();
        map.put("result",redisComponent.getList(userID+"-Par"));
        return map;
    }

    @RequestMapping(value = "/Participate",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> participate(@RequestParam("groupBuyingID") String groupBuyingID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "Participate";
        String userID = blockChainService.getUserName();
        String type = "submit";
        String result = blockChainService.sdk(type,functionName,userID,groupBuyingID);
        redisComponent.setList(userID+"-Par",groupBuyingID);
        map.put("result", result);
        map.put("groupBuyingID",groupBuyingID);
        return map;
    }

    @RequestMapping(value = "/QueryGroupBuying",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> QueryGroupBuying(@RequestParam("groupBuyingID") String groupBuyingID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "QueryGroupBuying";
        String type = "evaluate";
        String result = blockChainService.sdk(type,functionName,groupBuyingID);
        map.put("groupBuyingID",groupBuyingID);
        map.put("result",result);
        return map;
    }


    /**
     *  seller
     */
    @RequestMapping(value = "/InitRule", method = RequestMethod.POST)
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

    @RequestMapping(value = "/getDiscountRuleID", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDiscountRuleID(){
        Map<String, Object> map = new HashMap<>();
        String sellerID = blockChainService.getUserName();
        map.put("result",redisComponent.getList(sellerID));
        return map;
    }

    @RequestMapping(value = "/QueryParticipation", method = RequestMethod.GET)
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

    @RequestMapping(value = "/QueryState", method = RequestMethod.GET)
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

    @RequestMapping(value = "/Open",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> Open(@RequestParam("discountRuleID") String discountRuleID, @RequestParam("duration") String duration) throws InterruptedException, ContractException, TimeoutException, IOException, SchedulerException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "Open";
        String type = "submit";
        blockChainService.Close(discountRuleID, duration).start();
        Thread.sleep(1000*5);
        String result = blockChainService.sdk(type,functionName,discountRuleID,duration);
        map.put("discountRuleID",discountRuleID);
        map.put("duration", duration);
        map.put("result",result);
        return map;
    }

    /**
     * platform
     */
    @RequestMapping(value = "/InitCredit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> InitCredit(@RequestParam("userID") String userId) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "InitCredit";
        String type = "submit";
        String result = blockChainService.sdk(type,functionName,userId);
        map.put("userID",userId);
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/ChangeCredit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> ChangeCredit(@RequestParam("userID") String userID, @RequestParam("changeValue") String changeValue) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "ChangeCredit";
        String type = "submit";
        String result = blockChainService.sdk(type,functionName,userID,changeValue);
        map.put("userID",userID);
        map.put("changeValue",changeValue);
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/QueryCredit",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> QueryCredit(@RequestParam("userID") String userID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "QueryCredit";
        String type = "evaluate";
        String result = blockChainService.sdk(type,functionName,userID);
        map.put("result",result);
        map.put("userID",userID);
        return map;
    }

    @RequestMapping(value = "/InitTrans",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> InitTrans(@RequestParam("discountRuleID") String discountRuleID, @RequestParam("groupBuyingID") String groupBuyingID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "InitTrans";
        String type = "submit";
        String result = blockChainService.sdk(type, functionName,discountRuleID,groupBuyingID);
        redisComponent.setList("transID",groupBuyingID+"-"+discountRuleID);
        map.put("discountRuleID",discountRuleID);
        map.put("groupBuyingID",groupBuyingID);
        map.put("TransID",groupBuyingID+"-"+discountRuleID);
        map.put("result",result);

        return map;
    }

    @RequestMapping(value = "/getTransID",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getTransID(){
        Map<String, Object> map = new HashMap<>();
        map.put("transID",redisComponent.getList("transID"));
        return map;
    }

    @RequestMapping(value = "/ChangeTrans",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> ChangeTrans(@RequestParam("transID") String transID, @RequestParam("transState") String transState) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String,Object> map = new HashMap<>();
        String functionName = "ChangeTrans";
        String type = "submit";
        String result = blockChainService.sdk(type,functionName,transID,transState);
        map.put("result",result);
        map.put("transID",transID);
        map.put("transState",transState);
        return map;
    }

    @RequestMapping(value = "/QueryTrans",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> QueryTrans(@RequestParam("transID") String transID) throws InterruptedException, ContractException, TimeoutException, IOException {
        Map<String, Object> map = new HashMap<>();
        String functionName = "QueryTrans";
        String type = "evaluate";
        String result = blockChainService.sdk(type,functionName,transID);
        map.put("result",result);
        return map;

    }

}
