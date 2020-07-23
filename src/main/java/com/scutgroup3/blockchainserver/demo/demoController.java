package com.scutgroup3.blockchainserver.demo;

import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class demoController {

    @Autowired
    private demoService demoService;

    private static final String ORGNAME_ORG2 = "Org1";
    private static final String USERNAME_ORG2 = "user01";

    @RequestMapping("/query")
    @ResponseBody
    public String query() throws IOException, ContractException {
        return demoService.doQuery(ORGNAME_ORG2, USERNAME_ORG2, "Query", "a");
    }
}
