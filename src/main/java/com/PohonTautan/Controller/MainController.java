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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PohonTautan.Entity.Log;
import com.PohonTautan.Entity.Sessionid;
import com.PohonTautan.Entity.Styles;
import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.LogRepository;
import com.PohonTautan.Repository.SessionoidRepositori;
import com.PohonTautan.Repository.StylesRepository;
import com.PohonTautan.Repository.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/adm")
public class MainController {

    @Autowired
    private UsersRepository personRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private StylesRepository stylesRepository;

    @Autowired
    private SessionoidRepositori sessionoidRepositori;

    @Autowired
    private LogRepository logRepository;
    
    @RequestMapping(value = "/dasboard", method = RequestMethod.GET)
    public String adm(){
        return "dashboard";
    }

    @RequestMapping(value = "/inputuser", method = RequestMethod.POST)
    public ResponseEntity<Map> inputuser(@RequestParam String username, @RequestParam String password) {
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

    @RequestMapping(value = "/sessionbutton", method = RequestMethod.POST)
    public ResponseEntity<Map> sessionbutton(HttpServletRequest request, @RequestParam String url, @RequestParam Integer button) {
        Map data = new HashMap<>();

        HttpSession session = request.getSession();
        String sessionId = session.getId();

        Integer id = stylesRepository.getstStyles(url).getId_user();

        Log ss = new Log();
        ss.setButton(button);
        ss.setId_user(id);
        ss.setSession_visitor(sessionId);
        logRepository.save(ss);

        data.put("icon", "success");
        data.put("message", "Sukses Insert Person");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // @RequestMapping(value = "/inputstyle", method = RequestMethod.POST)
    // public ResponseEntity<Map> inputstyle(@RequestParam String username, @RequestParam String password) {
    //     Map data = new HashMap<>();
    
        // String gambar = Image.replace(" ", "+");
        // byte[] binarydata = Base64.getMimeDecoder().decode(gambar);
        // Blob blob = new SerialBlob(binarydata);
        // byte[] bytes = blob.getBytes(1, (int) blob.length());
        // String base64String = Base64.getEncoder().encodeToString(bytes);
        // String gambars = base64String.replace("dataimage/pngbase64", "data:image/png;base64,");
        // String images = gambars.replace("=", "");

    //     Styles st = new Styles();
    //     st.setId_user();
    //     st.setLink();
    //     st.setButton_style();
    //     st.setBg();
    //     st.setImage();
    //     stylesRepository.save(st);
    //     data.put("icon", "success");
    //     data.put("message", "Sukses Insert Style");
    //     return new ResponseEntity<>(data, HttpStatus.OK);
    // }

    // @RequestMapping(value = "/editstyle", method = RequestMethod.PUT)
    // public ResponseEntity<Map> editstyle(@RequestParam String username, @RequestParam String password) {
    //     Map data = new HashMap<>();

        // if (!stylesRepository.existsById(id)) {
        //     data.put("icon", "error");
        //     data.put("message", "Data Tidak ada");
        //     return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        // }

    //     Styles st = stylesRepository.getById(id);
    //     st.setId_user();
    //     st.setLink();
    //     st.setButton_style();
    //     st.setBg();
    //     st.setImage();
    //     stylesRepository.save(st);
    //     data.put("icon", "success");
    //     data.put("message", "Sukses Insert Style");
    //     return new ResponseEntity<>(data, HttpStatus.OK);
    // }


}
