package com.PohonTautan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.PohonTautan.Entity.Sessionid;
import com.PohonTautan.Repository.SessionoidRepositori;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Date;


@Controller
public class IndexController {

    @Autowired
    private SessionoidRepositori sessionoidRepositori;
    
    @GetMapping(value = "/")
    public String index(HttpServletRequest request){

        Date date = new Date();

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String userIp = request.getRemoteAddr();

        if(!sessionoidRepositori.findBySessionId(sessionId)){
            if(!sessionoidRepositori.findBycreatedAt(date)){
                Sessionid st = new Sessionid();
                st.setSession_visitor(sessionId);
                st.setIp_visitor(userIp);
                st.setButton(null);
                sessionoidRepositori.save(st);
            }
            
        }
 
        return "index";
    }

}
