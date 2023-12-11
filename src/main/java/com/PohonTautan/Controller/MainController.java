package com.PohonTautan.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    
    // @Autowired
    // private StyleRepository styleRepository;

    // @Autowired
    // private UserRepository userRepository;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }
}
