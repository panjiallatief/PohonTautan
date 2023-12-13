package com.PohonTautan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.UsersRepository;

@Controller
public class EmailController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/auth/authuser")
    public String authuser(@RequestParam String username)  {
        if (!usersRepository.booleanusercname(username)) {
            return "error";
        }
        Users personTemp = usersRepository.getidwithusername(username);
        personTemp.setStatus(true);
        usersRepository.save(personTemp);

        return "thankyou";

    }
    
}
