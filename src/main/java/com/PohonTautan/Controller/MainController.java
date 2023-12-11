package com.PohonTautan.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.UsersRepository;


@Controller
public class MainController {

    @Autowired
    private UsersRepository personRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @PostMapping(value = "/post")
    public ResponseEntity<Map> post(@RequestParam String username, @RequestParam String password) {
        Map data = new HashMap<>();
        if (personRepository.findByUsername(username).size() > 0) {
            data.put("message", "username sudah ada");
            data.put("icon", "warning");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        Users personTemp = new Users();
        personTemp.setUsername(username);
        personTemp.setPassword(encoder.encode(password));
        personRepository.save(personTemp);
        data.put("icon", "success");
        data.put("message", "Sukses Insert Person");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
