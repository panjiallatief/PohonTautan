package com.PohonTautan.Controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PohonTautan.Entity.Log;
import com.PohonTautan.Entity.Styles;
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
    private UsersRepository usersRepository;

    @Autowired
    private StylesRepository stylesRepository;

    @Autowired
    private SessionoidRepositori sessionoidRepositori;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private HttpSession httpSession;

    @RequestMapping(value = "/dasboard", method = RequestMethod.GET)
    public String adm() {

        return "dashboard";
    }

    @RequestMapping(value = "/countday", method = RequestMethod.GET)
    public ResponseEntity<Map> countday() {
        Map data = new HashMap<>();
        Date date = new Date();

        Long count = sessionoidRepositori.countsession(date, 1);

        data.put("data", count);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/countbuttonday", method = RequestMethod.GET)
    public ResponseEntity<Map> countbuttonday() {
        Map data = new HashMap<>();
        Date date = new Date();

        Long count = logRepository.countbutton(date, 1);

        data.put("data", count);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/sessionbutton", method = RequestMethod.POST)
    public ResponseEntity<Map> sessionbutton(HttpServletRequest request, @RequestParam String url,
            @RequestParam Integer button) {
        Map data = new HashMap<>();

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String currentUrl = request.getRequestURL().toString();

        Integer id = stylesRepository.getstStyles(currentUrl).getId_user();

        Log ss = new Log();
        ss.setButton(button);
        ss.setId_user(id);
        ss.setSession_visitor(sessionId);
        logRepository.save(ss);

        data.put("icon", "success");
        data.put("message", "Sukses Insert Person");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/inputstyle", method = RequestMethod.POST)
    public ResponseEntity<Map> inputstyle(@RequestParam String[] link, @RequestParam String[] button,
            @RequestParam String[] buttonname, @RequestParam String bg, @RequestParam String image, @RequestParam String curl)
            throws SerialException, SQLException {
        Map data = new HashMap<>();

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        if(!stylesRepository.booleanstyle(usnn)){
            String gambarimage = image.replace(" ", "+");
            byte[] binarydataimage = Base64.getMimeDecoder().decode(gambarimage);
            Blob blobimage = new SerialBlob(binarydataimage);

            String gambarbg = image.replace(" ", "+");
            byte[] binarydatabg = Base64.getMimeDecoder().decode(gambarbg);
            Blob blobbg = new SerialBlob(binarydatabg);

            // byte[] bytes = blob.getBytes(1, (int) blob.length());
            // String base64String = Base64.getEncoder().encodeToString(bytes);
            // String gambars = base64String.replace("dataimage/pngbase64",
            // "data:image/png;base64,");
            // String images = gambars.replace("=", "");

            String A =  null;
            String B =  null;
            String C =  null;

            if(link != null){
                A = Arrays.toString(link).replace("[", "").replace("]", "");
                B = Arrays.toString(button).replace("[", "").replace("]", "");
                C = Arrays.toString(buttonname).replace("[", "").replace("]", "");
            }

            Styles st = new Styles();
            st.setId_user(usnn);
            st.setLink(A);
            st.setButton_style(B);
            st.setButton_name(C);
            st.setBg(blobbg);
            st.setImage(blobimage);
            st.setCustom_url(curl);
            stylesRepository.save(st);

            data.put("icon", "success");
            data.put("message", "Sukses Insert Style");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

        data.put("icon", "error");
        data.put("message", "Style Sudah Ada");
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/inputbutton", method = RequestMethod.PUT)
    public ResponseEntity<Map> inputbutton(@RequestParam String[] tombol,
            @RequestParam String[] tautan, @RequestParam String[] buttonname) {
        Map data = new HashMap<>();

        System.out.println(Arrays.toString(tautan));

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        if (!stylesRepository.booleanstyle(usnn)) {
            data.put("icon", "error");
            data.put("message", "Data Tidak ada");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Styles st = stylesRepository.getstStyles2(usnn);

        String A = Arrays.toString(tautan).replace("[", "").replace("]", "");
        String B = Arrays.toString(tombol).replace("[", "").replace("]", "");
        String C = Arrays.toString(buttonname).replace("[", "").replace("]", "");

        String cek = st.getLink();

        if(cek.length() != 0){
            System.out.println("masuk if");
            st.setLink(st.getLink() + "," + A);
            st.setButton_style(st.getButton_style() + "," + B);
            st.setButton_name(st.getButton_name() + "," + C);
            stylesRepository.save(st);
        } else {
            System.out.println("masuk else");
            st.setLink(A);
            st.setButton_style(B);
            st.setButton_name(C);
            stylesRepository.save(st);
        }

        data.put("icon", "success");
        data.put("message", "Sukses Insert Style");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // @RequestMapping(value = "/editstyle", method = RequestMethod.PUT)
    // public ResponseEntity<Map> editstyle(@RequestParam String username,
    // @RequestParam String password) {
    // Map data = new HashMap<>();

    // // if (!stylesRepository.existsById(id)) {
    // // data.put("icon", "error");
    // // data.put("message", "Data Tidak ada");
    // // return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    // // }

    // // Styles st = stylesRepository.getById(id);
    // // st.setId_user();
    // // st.setLink();
    // // st.setButton_style();
    // // st.setBg();
    // // st.setImage();
    // // stylesRepository.save(st);

    // data.put("icon", "success");
    // data.put("message", "Sukses Insert Style");
    // return new ResponseEntity<>(data, HttpStatus.OK);
    // }

}
