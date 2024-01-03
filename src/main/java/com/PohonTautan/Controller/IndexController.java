package com.PohonTautan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.PohonTautan.Entity.Log;
import com.PohonTautan.Entity.Sessionid;
import com.PohonTautan.Entity.Styles;
import com.PohonTautan.Entity.Token;
import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.LogRepository;
import com.PohonTautan.Repository.SessionoidRepositori;
import com.PohonTautan.Repository.StylesRepository;
import com.PohonTautan.Repository.TokenRepository;
import com.PohonTautan.Repository.UsersRepository;

import jakarta.mail.MessagingException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.*;

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

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private LogRepository logRepository;

    @GetMapping(value = "/")
    public String landing(){
        return "landing";
    }
    
    @GetMapping(value = "/{curl}")
    public String index(@PathVariable(required = true) String curl, HttpServletRequest request, Model model) throws SQLException {
        Date date = new Date();
        Integer usnn ;
        if(stylesRepository.getstStyles(curl) != null){
            usnn = stylesRepository.getstStyles(curl).getId_user();
        } else {
            usnn = null;
        }
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String userIp = request.getRemoteAddr();
        String currentUrl = request.getRequestURL().toString();
        Integer ins = currentUrl.lastIndexOf("/");
        String customurl = currentUrl.substring(ins + 1);
        httpSession.setAttribute("curl", customurl);

        if(!sessionoidRepositori.findBycreatedAt(date, sessionId)){
            Sessionid st = new Sessionid();
            st.setSession_visitor(sessionId);
            st.setIp_visitor(userIp);
            st.setId_user(usnn);
            sessionoidRepositori.save(st); 
        }

        if(stylesRepository.getstStyles2(usnn) != null){
             Styles st = stylesRepository.getstStyles2(usnn);
            List<Map<String, Object>> stylesList = new ArrayList<>();
            Long countday = sessionoidRepositori.countsession(date, usnn);
            model.addAttribute("countday", countday);
            Long countbuttonday = logRepository.countbutton(date, usnn);
            model.addAttribute("countbuttonday", countbuttonday);

            String[] btn = null;
            String[] btnstyle = null;
            String[] link = null;
            String[] btnanim = null;
            String[] btntc = null;
            String bg = null;
            String image = null;
            String curls = null;
            String headline = null;
            String bio = null;
            
            if (st.getButton_name() != null) {
                btn = st.getButton_name().split(",");
            } else {
                btn = null;
            }

            if (st.getButton_style() != null) {
                btnstyle = st.getButton_style().split(",");
            } else {
                btnstyle = null;
            }

            if (st.getLink() != null) {
                link = st.getLink().split(",");
            } else {
                link = null;
            }

            if (st.getButton_animation() != null) {
                btnanim = st.getButton_animation().split(",");
            } else {
                btnanim = null;
            }

            if (st.getButton_text_color() != null) {
                btntc = st.getButton_text_color().split(",");
            } else {
                btntc = null;
            }

            if (st.getBg() != null) {
                byte[] bgBytes = st.getBg().getBytes(1, (int) st.getBg().length());
                String base64String = Base64.getEncoder().encodeToString(bgBytes);
                String gambars = base64String.replace("dataimage/jpegbase64",
                        "data:image/png;base64,");
                bg = gambars.replace("=", "");
            } else {
                bg = null;
            }

            if (st.getImage() != null) {
                byte[] imageBytes = st.getImage().getBytes(1, (int) st.getBg().length());
                String base64Strings = Base64.getEncoder().encodeToString(imageBytes);
                String gambar = base64Strings.replace("dataimage/jpegbase64",
                        "data:image/png;base64,");
                image = gambar.replace("=", "");
            } else {
                image = null;
            }

            if (st.getCustom_url() != null) {
                curls = st.getCustom_url();
            } else {
                curls = null;
            }

            if (st.getHeadline() != null) {
                headline = st.getHeadline();
            } else {
                headline = null;
            }

            if (st.getBio() != null) {
                bio = st.getBio();
            } else {
                bio = null;
            }

            if(link != null){
                for (Integer i = 0; i < link.length; i++) {
                    Map<String, Object> styleMap = new HashMap<>();
                    styleMap.put("tempBg", bg);
                    styleMap.put("tempImg", image);
                    styleMap.put("button_name", btn[i]);
                    styleMap.put("button_style", "#" + btnstyle[i]);
                    styleMap.put("custom_url", curl);
                    styleMap.put("id_user", st.getId_user());
                    styleMap.put("created_at", st.getCreatedAt());
                    styleMap.put("updated_at", st.getUpdatedAt());
                    styleMap.put("id_style", st.getId_style());
                    styleMap.put("headline", headline);
                    styleMap.put("bio", bio);
                    styleMap.put("bg_default", st.getBg_default());
                    styleMap.put("link", link[i]);
                    styleMap.put("button_animation", btnanim[i]);
                    styleMap.put("button_text_color", btntc[i]);
                    stylesList.add(styleMap);
                }
            } else if (bg != null){
                Map<String, Object> styleMap = new HashMap<>();
                styleMap.put("tempBg", bg);
                styleMap.put("tempImg", image);
                styleMap.put("button_name", btn);
                styleMap.put("button_style", btnstyle);
                styleMap.put("custom_url", curl);
                styleMap.put("id_user", st.getId_user());
                styleMap.put("created_at", st.getCreatedAt());
                styleMap.put("updated_at", st.getUpdatedAt());
                styleMap.put("id_style", st.getId_style());
                styleMap.put("headline", headline);
                styleMap.put("bio", bio);
                styleMap.put("bg_default", st.getBg_default());
                styleMap.put("link", link);
                styleMap.put("button_animation", btnanim);
                styleMap.put("button_text_color", btntc);
                stylesList.add(styleMap);
            } else {
                Map<String, Object> styleMap = new HashMap<>();
                styleMap.put("tempBg", null);
                styleMap.put("tempImg", null);
                styleMap.put("button_name", null);
                styleMap.put("button_style", null);
                styleMap.put("custom_url", null);
                styleMap.put("id_user", st.getId_user());
                styleMap.put("created_at", null);
                styleMap.put("updated_at", null);
                styleMap.put("id_style", st.getId_style());
                styleMap.put("headline", null);
                styleMap.put("bio", null);
                styleMap.put("bg_default", st.getBg_default());
                styleMap.put("link", null);
                styleMap.put("button_animation", null);
                styleMap.put("button_text_color", null);
                stylesList.add(styleMap);
            }

            model.addAttribute("btnstyle", stylesList);

            return "index";

        }

        // return "curlnotfound";
        return "error";
        // buat template html curl tidak ditemukan / tidak ada.

    }

    public String generetetoken(Integer lenght){
        
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < lenght) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();

        return saltStr;
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

        String strtoken = generetetoken(100);

        Token token = new Token();
        token.setToken(strtoken);
        token.setId_user(personTemp.getUid());
        tokenRepository.save(token);

        Styles ss = new Styles();
        ss.setId_user(personTemp.getUid());
        ss.setCustom_url(username);
        ss.setBg_default("https://source.unsplash.com/1080x1920?pattren");
        stylesRepository.save(ss);
    
        String myvar = "<!doctype html><html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Pohon Tautan Activation</title><style media=\"all\" type=\"text/css\">body{font-family:Helvetica,sans-serif;-webkit-font-smoothing:antialiased;font-size:16px;line-height:1.3;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}table{border-collapse:separate;mso-table-lspace:0;mso-table-rspace:0;width:100%}table td{font-family:Helvetica,sans-serif;font-size:16px;vertical-align:top}body{background-color:#f4f5f6;margin:0;padding:0}.body{background-color:#f4f5f6;width:100%}.container{margin:0 auto!important;max-width:600px;padding:0;padding-top:24px;width:600px}.content{box-sizing:border-box;display:block;margin:0 auto;max-width:600px;padding:0}.main{background:#fff;border:1px solid #eaebed;border-radius:16px;width:100%}.wrapper{box-sizing:border-box;padding:24px}.footer{clear:both;padding-top:24px;text-align:center;width:100%}.footer a,.footer p,.footer span,.footer td{color:#9a9ea6;font-size:16px;text-align:center}p{font-family:Helvetica,sans-serif;font-size:16px;font-weight:400;margin:0;margin-bottom:16px}a{color:#0867ec;text-decoration:underline}.btn{box-sizing:border-box;min-width:100%!important;width:100%}.btn>tbody>tr>td{padding-bottom:16px}.btn table{width:auto}.btn table td{background-color:#fff;border-radius:4px;text-align:center}.btn a{background-color:#fff;border:solid 2px #0867ec;border-radius:4px;box-sizing:border-box;color:#0867ec;cursor:pointer;display:inline-block;font-size:16px;font-weight:700;margin:0;padding:12px 24px;text-decoration:none;text-transform:capitalize}.btn-primary table td{background-color:#0867ec}.btn-primary a{background-color:#0867ec;border-color:#0867ec;color:#fff}@media all{.btn-primary table td:hover{background-color:#ec0867!important}.btn-primary a:hover{background-color:#ec0867!important;border-color:#ec0867!important}}.last{margin-bottom:0}.first{margin-top:0}.align-center{text-align:center}.align-right{text-align:right}.align-left{text-align:left}.text-link{color:#0867ec!important;text-decoration:underline!important}.clear{clear:both}.mt0{margin-top:0}.mb0{margin-bottom:0}.preheader{color:transparent;display:none;height:0;max-height:0;max-width:0;opacity:0;overflow:hidden;mso-hide:all;visibility:hidden;width:0}.powered-by a{text-decoration:none}@media only screen and (max-width:640px){.main p,.main span,.main td{font-size:16px!important}.wrapper{padding:8px!important}.content{padding:0!important}.container{padding:0!important;padding-top:8px!important;width:100%!important}.main{border-left-width:0!important;border-radius:0!important;border-right-width:0!important}.btn table{max-width:100%!important;width:100%!important}.btn a{font-size:16px!important;max-width:100%!important;width:100%!important}}@media all{.ExternalClass{width:100%}.ExternalClass,.ExternalClass div,.ExternalClass font,.ExternalClass p,.ExternalClass span,.ExternalClass td{line-height:100%}.apple-link a{color:inherit!important;font-family:inherit!important;font-size:inherit!important;font-weight:inherit!important;line-height:inherit!important;text-decoration:none!important}#MessageViewBody a{color:inherit;text-decoration:none;font-size:inherit;font-family:inherit;font-weight:inherit;line-height:inherit}}</style></head><body><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\"><tr><td> </td><td class=\"container\"><div class=\"content\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"main\"><tr><td class=\"wrapper\"><p>Hi there</p><p>One more step to activate your account, just click the button bellow</p><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\"><tbody><tr><td align=\"left\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td><a target=\"_blank\" href=\""+ tautan +"/auth/authuser/"+strtoken+"\">Activate your account</a></td></tr></tbody></table></td></tr></tbody></table><p>Good luck! Hope it works.</p></td></tr></table></div></td><td> </td></tr></table></body></html>";
	

	
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

    @RequestMapping(value = "/sessionbutton", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map> sessionbutton(HttpServletRequest request, @RequestParam String url) {
        Map data = new HashMap<>();

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String currentUrl = httpSession.getAttribute("curl").toString();

        System.out.println(currentUrl);

        Integer id = stylesRepository.getstStyles(currentUrl).getId_user();

        Log ss = new Log();
        ss.setButton(url);
        ss.setId_user(id);
        ss.setSession_visitor(sessionId);
        logRepository.save(ss);

        data.put("icon", "success");
        data.put("message", "Sukses Insert Person");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping(value = "/resetpass")
    public ResponseEntity<Map> resetpass(@RequestParam String token, @RequestParam String password)  {
        Map data = new HashMap<>();

        Integer id = tokenRepository.getiduser(token).getId_user();
        Users personTempor = usersRepository.getById(id);
        String email = personTempor.getEmail();

        if (usersRepository.findByEmail(email).size() > 0) {

            Users personTemp = usersRepository.getwithemail(email);
            personTemp.setPassword(encoder.encode(password));
            usersRepository.save(personTemp);

            Integer tk = tokenRepository.getiduser(token).getIdt();
            tokenRepository.deleteById(tk);

            data.put("icon", "success");
            data.put("message", "Sukses Insert Person");
            return new ResponseEntity<>(data, HttpStatus.OK);

        }

        data.put("icon", "error");
        data.put("message", "Gagal Reset Password");
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);

    }
    

}
