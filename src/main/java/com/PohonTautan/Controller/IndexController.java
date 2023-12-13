package com.PohonTautan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PohonTautan.Entity.Sessionid;
import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.SessionoidRepositori;
import com.PohonTautan.Repository.StylesRepository;
import com.PohonTautan.Repository.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private SessionoidRepositori sessionoidRepositori;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private StylesRepository stylesRepository;
    
    @GetMapping(value = "/{curl}")
    public String index(HttpServletRequest request, @PathVariable(required = true) String curl){

        Date date = new Date();

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String userIp = request.getRemoteAddr();

        Integer id = stylesRepository.getstStyles(curl).getId_user();

        if(!sessionoidRepositori.findBycreatedAt(date, sessionId)){
            Sessionid st = new Sessionid();
            st.setSession_visitor(sessionId);
            st.setIp_visitor(userIp);
            st.setId_user(id);
            sessionoidRepositori.save(st);
        }
 
        return "index";
    }
    
    @PostMapping(value = "/inputuser")
    public ResponseEntity<Map> inputuser(@RequestParam String username, @RequestParam String passwordr, @RequestParam String email)  {
        Map data = new HashMap<>();
        if (usersRepository.findByUsername(username).size() > 0) {
            data.put("message", "username sudah ada");
            data.put("icon", "warning");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        Users personTemp = new Users();
        personTemp.setUsername(username);
        personTemp.setPassword(encoder.encode(passwordr));
        personTemp.setEmail(email);
        personTemp.setStatus(false);
        usersRepository.save(personTemp);

        data.put("icon", "success");
        data.put("message", "Sukses Insert Person");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
