package com.PohonTautan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PohonTautan.Entity.Sessionid;
import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.SessionoidRepositori;
import com.PohonTautan.Repository.StylesRepository;
import com.PohonTautan.Repository.UsersRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    @Autowired
    private JavaMailSender javaMailSender;
    
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
    public ResponseEntity<Map> inputuser(HttpServletRequest request, @RequestParam String username, @RequestParam String passwordr, @RequestParam String email)  {
        Map data = new HashMap<>();
        if (usersRepository.findByUsername(username).size() > 0) {
            data.put("message", "username sudah ada");
            data.put("icon", "warning");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        StringBuffer a = request.getRequestURL();
        String tautan = a.substring(0, a.lastIndexOf("/"));

        Users personTemp = new Users();
        personTemp.setUsername(username);
        personTemp.setPassword(encoder.encode(passwordr));
        personTemp.setEmail(email);
        personTemp.setStatus(false);
        usersRepository.save(personTemp);
    
        String myvar = "<!doctype html><html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Pohon Tautan Activation</title><style media=\"all\" type=\"text/css\">body{font-family:Helvetica,sans-serif;-webkit-font-smoothing:antialiased;font-size:16px;line-height:1.3;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}table{border-collapse:separate;mso-table-lspace:0;mso-table-rspace:0;width:100%}table td{font-family:Helvetica,sans-serif;font-size:16px;vertical-align:top}body{background-color:#f4f5f6;margin:0;padding:0}.body{background-color:#f4f5f6;width:100%}.container{margin:0 auto!important;max-width:600px;padding:0;padding-top:24px;width:600px}.content{box-sizing:border-box;display:block;margin:0 auto;max-width:600px;padding:0}.main{background:#fff;border:1px solid #eaebed;border-radius:16px;width:100%}.wrapper{box-sizing:border-box;padding:24px}.footer{clear:both;padding-top:24px;text-align:center;width:100%}.footer a,.footer p,.footer span,.footer td{color:#9a9ea6;font-size:16px;text-align:center}p{font-family:Helvetica,sans-serif;font-size:16px;font-weight:400;margin:0;margin-bottom:16px}a{color:#0867ec;text-decoration:underline}.btn{box-sizing:border-box;min-width:100%!important;width:100%}.btn>tbody>tr>td{padding-bottom:16px}.btn table{width:auto}.btn table td{background-color:#fff;border-radius:4px;text-align:center}.btn a{background-color:#fff;border:solid 2px #0867ec;border-radius:4px;box-sizing:border-box;color:#0867ec;cursor:pointer;display:inline-block;font-size:16px;font-weight:700;margin:0;padding:12px 24px;text-decoration:none;text-transform:capitalize}.btn-primary table td{background-color:#0867ec}.btn-primary a{background-color:#0867ec;border-color:#0867ec;color:#fff}@media all{.btn-primary table td:hover{background-color:#ec0867!important}.btn-primary a:hover{background-color:#ec0867!important;border-color:#ec0867!important}}.last{margin-bottom:0}.first{margin-top:0}.align-center{text-align:center}.align-right{text-align:right}.align-left{text-align:left}.text-link{color:#0867ec!important;text-decoration:underline!important}.clear{clear:both}.mt0{margin-top:0}.mb0{margin-bottom:0}.preheader{color:transparent;display:none;height:0;max-height:0;max-width:0;opacity:0;overflow:hidden;mso-hide:all;visibility:hidden;width:0}.powered-by a{text-decoration:none}@media only screen and (max-width:640px){.main p,.main span,.main td{font-size:16px!important}.wrapper{padding:8px!important}.content{padding:0!important}.container{padding:0!important;padding-top:8px!important;width:100%!important}.main{border-left-width:0!important;border-radius:0!important;border-right-width:0!important}.btn table{max-width:100%!important;width:100%!important}.btn a{font-size:16px!important;max-width:100%!important;width:100%!important}}@media all{.ExternalClass{width:100%}.ExternalClass,.ExternalClass div,.ExternalClass font,.ExternalClass p,.ExternalClass span,.ExternalClass td{line-height:100%}.apple-link a{color:inherit!important;font-family:inherit!important;font-size:inherit!important;font-weight:inherit!important;line-height:inherit!important;text-decoration:none!important}#MessageViewBody a{color:inherit;text-decoration:none;font-size:inherit;font-family:inherit;font-weight:inherit;line-height:inherit}}</style></head><body><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\"><tr><td> </td><td class=\"container\"><div class=\"content\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"main\"><tr><td class=\"wrapper\"><p>Hi there</p><p>One more step to activate your account, just click the button bellow</p><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\"><tbody><tr><td align=\"left\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td><a target=\"_blank\" href=\""+ tautan +"/auth/authuser?username="+username+"\">Activate your account</a></td></tr></tbody></table></td></tr></tbody></table><p>Good luck! Hope it works.</p></td></tr></table></div></td><td> </td></tr></table></body></html>";
	

	
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Account Activation");
            helper.setText(myvar, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        data.put("icon", "success");
        data.put("message", "Sukses Insert Person");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping(value = "/resetpass")
    public ResponseEntity<Map> resetpass(@RequestParam String email, @RequestParam String pass)  {
        Map data = new HashMap<>();
        if (usersRepository.findByEmail(email).size() > 0) {

            Users personTemp = usersRepository.getwithemail(email);
            personTemp.setPassword(encoder.encode(pass));
            usersRepository.save(personTemp);

            data.put("icon", "success");
            data.put("message", "Sukses Insert Person");
            return new ResponseEntity<>(data, HttpStatus.OK);

        }

        data.put("icon", "error");
        data.put("message", "Gagal Reset Password");
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);

    }
    

}
