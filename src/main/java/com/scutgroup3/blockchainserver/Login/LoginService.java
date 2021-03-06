package com.scutgroup3.blockchainserver.Login;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String rootPath = "/home/ubuntu/group3BlockChain/wallet/";
        String walletPath = rootPath+getRole(s)+"/"+s+".id";
        person person = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(walletPath));
            String id;
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            while ((id = in.readLine()) != null) {
                person = new person(s,bCryptPasswordEncoder.encode(id));
            }
        } catch (IOException e) {
        }
        UserDetails userDetails=null;
        if(person!=null){
            //创建一个集合来存放权限
            Collection<GrantedAuthority> authorities = null;
            try {
                authorities = getAuthorities(person,rootPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //实例化UserDetails对象
            userDetails=new User(s,person.getId(),true,true,true,true, authorities);
        }
        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(person user, String rootPath) throws IOException {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get(rootPath,getRole(user.getUsername())));
        authList.add(new SimpleGrantedAuthority("ROLE_"+wallet.get(user.getUsername()).getMspId())); //将所有权限设为用户
        return authList;
    }

    private String getRole(String userName){
        String rootPath = "/home/ubuntu/group3BlockChain/wallet/";
        File buyer = new File(rootPath+"buyer/" + userName + ".id");
        File seller = new File(rootPath+"seller/" + userName + ".id");
        File platform = new File(rootPath+"platform/" + userName + ".id");
        if (buyer.exists())
            return "buyer";
        else if (seller.exists())
            return "seller";
        else if (platform.exists())
            return "platform";
        else
            return null;
    }
}
