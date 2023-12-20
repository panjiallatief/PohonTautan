package com.PohonTautan.Controller;

import java.sql.Blob;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.text.Style;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.thymeleaf.expression.Objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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

    @Autowired
    private static Environment env;

    @RequestMapping(value = "/dasboard", method = RequestMethod.GET)
    public String adm(Model model) throws SQLException {

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();
        Styles st = stylesRepository.getstStyles2(usnn);
        Date date = new Date();
        LocalDate currentDate = LocalDate.now();
        List<Map<String, Object>> stylesList = new ArrayList<>();
        model.addAttribute("btnstyle", stylesList);
        Long countday = sessionoidRepositori.countsession(date, usnn);
        model.addAttribute("countday", countday);
        Long countbuttonday = logRepository.countbutton(date, usnn);
        model.addAttribute("countbuttonday", countbuttonday);
        Integer tahun = currentDate.getYear();
        Integer bulan = currentDate.getMonthValue();
        Integer hari = currentDate.getDayOfMonth();
        List<Object[]> count = sessionoidRepositori.countPerMonth(bulan, tahun, usnn);
        model.addAttribute("countmonth", count);
        List<Object[]> counts = sessionoidRepositori.countweek(usnn);
        model.addAttribute("countweek", counts);

        String[] btn = null;
        String[] btnstyle = null;
        String[] link = null;
        String[] btnanim = null;
        String[] btntc = null;
        String bg = null;
        String image = null;
        String curl = null;
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
            curl = st.getCustom_url();
        } else {
            curl = null;
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

        return "dashboard";
    }

    @RequestMapping(value = "/countday", method = RequestMethod.GET)
    public ResponseEntity<Map> countday() {
        Map data = new HashMap<>();
        Date date = new Date();

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        Long count = sessionoidRepositori.countsession(date, usnn);

        data.put("data", count);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/countmonth", method = RequestMethod.GET)
    public ResponseEntity<Map> countmonth() {
        Map data = new HashMap<>();
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);
        System.out.println("Bulan: " + month);

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        List<Object[]> count = sessionoidRepositori.countPerMonth(12, 2023, usnn);

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

    @RequestMapping(value = "/countbuttonmonth", method = RequestMethod.GET)
    public ResponseEntity<Map> countbuttonmonth() {
        Map data = new HashMap<>();
        Date date = new Date();

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        List<Object[]> count = logRepository.countPerMonth(12, 2023, usnn);

        data.put("data", count);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/inputstyle", method = RequestMethod.POST)
    public ResponseEntity<Map> inputstyle(@RequestParam String bio, @RequestParam String headline,
            @RequestParam (required = false) String bg, @RequestParam (required = false) String image,
            @RequestParam String curl)
            throws SerialException, SQLException, IOException {
        Map data = new HashMap<>();   

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        Blob blobbg = null;
        Blob blobimage = null;

        if(bg.length() > 9){
            String gambarbg = bg.replace(" ", "+");
            byte[] binarydatabg = Base64.getMimeDecoder().decode(gambarbg);
            blobbg = new SerialBlob(binarydatabg);
        }

        if(image.length() > 9){
            String gambarimage = image.replace(" ", "+");
            byte[] binarydataimage = Base64.getMimeDecoder().decode(gambarimage);
            blobimage = new SerialBlob(binarydataimage);
        }

        if (stylesRepository.booleanstyle(usnn)) {

            Styles st = stylesRepository.getstStyles2(usnn);
            st.setId_user(usnn);
            st.setBio(bio);
            st.setHeadline(headline);
            st.setBg(blobbg);
            st.setImage(blobimage);
            st.setCustom_url(curl);
            stylesRepository.save(st);

            data.put("icon", "success");
            data.put("message", "Sukses Insert Style");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

        data.put("icon", "error");
        data.put("message", "Gagal Insert Style");
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/inputbutton", method = RequestMethod.PUT)
    public ResponseEntity<Map> inputbutton(@RequestParam String[] tombol,
            @RequestParam String[] tautan,
            @RequestParam String[] buttonname,
            @RequestParam(required = false) String[] buttonanim,
            @RequestParam(required = false) String[] buttoncolortext) {
        Map data = new HashMap<>();

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        if (!stylesRepository.booleanstyle(usnn)) {
            data.put("icon", "error");
            data.put("message", "Data Tidak ada");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Styles st = stylesRepository.getstStyles2(usnn);
        String D = ""; 
        String E = ""; 

        String A = Arrays.toString(tautan).replace("[", "").replace("]", "");
        String B = Arrays.toString(tombol).replace("[", "").replace("]", "");
        String C = Arrays.toString(buttonname).replace("[", "").replace("]", "");

        if (buttonanim != null) {
             D = Arrays.toString(buttonanim).replace("[", "").replace("]", "");
        } else {
            for(Integer i = 0 ; i < tautan.length ; i++){
                D = "animate-none";
            }
        }

        if (buttoncolortext != null) {
             E = Arrays.toString(buttoncolortext).replace("[", "").replace("]", "");
        } else {
            for(Integer i = 0 ; i < tautan.length ; i++){
                E = "text-white";
            }    
        }


        String cek = st.getLink();

        if (cek != null) {
            st.setLink(st.getLink() + "," + A);
            st.setButton_style(st.getButton_style() + "," + B);
            st.setButton_name(st.getButton_name() + "," + C);
            st.setButton_animation(st.getButton_animation() + "," + D);
            st.setButton_text_color(st.getButton_text_color() + "," + E);
            stylesRepository.save(st);
        } else {
            st.setLink(A);
            st.setButton_style(B);
            st.setButton_name(C);
            st.setButton_animation(D);
            st.setButton_text_color(E);
            stylesRepository.save(st);
        }

        data.put("icon", "success");
        data.put("message", "Sukses Insert Style");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    private static String[] removeElementByIndex(String[] array, int index) {
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }

    @PutMapping(value = "/deletebutton")
    public ResponseEntity<Map> deletebutton(@RequestParam String tautan) {
        Map data = new HashMap<>();

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        if (!stylesRepository.booleanstyle(usnn)) {
            data.put("icon", "error");
            data.put("message", "Data Tidak ada");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Styles st = stylesRepository.getstStyles2(usnn);

        String[] btn = st.getButton_name().split(",");
        String[] btnstyle = st.getButton_style().split(",");
        String[] link = st.getLink().split(",");
        String[] btnanim = st.getButton_animation().split(",");
        String[] btntc = st.getButton_text_color().split(",");
        String A = "";
        String B = "";
        String C = "";
        String D = "";
        String E = "";

        String ta = tautan.replace("_", ".");
        Integer index = -1;

        for (int i = 0; i < link.length; i++) {
            if (link[i].contains(ta)) {
                index = i;
                break; 
            }
        }

        if (index != -1) {
            btn = removeElementByIndex(btn, index);
            btnstyle = removeElementByIndex(btnstyle, index);
            link = removeElementByIndex(link, index);
            btnanim = removeElementByIndex(btnanim, index);
            btntc = removeElementByIndex(btntc, index);

            A = Arrays.toString(btn).replace("[", "").replace("]", "");
            B = Arrays.toString(btnstyle).replace("[", "").replace("]", "");
            C = Arrays.toString(link).replace("[", "").replace("]", "");
            D = Arrays.toString(btnanim).replace("[", "").replace("]", "");
            E = Arrays.toString(btntc).replace("[", "").replace("]", "");
        }

        if(A.length() > 0){
            st.setButton_animation(D);
            st.setButton_name(A);
            st.setButton_style(B);
            st.setButton_text_color(E);
            st.setLink(C);
            stylesRepository.save(st);
        } else {
            st.setButton_animation(null);
            st.setButton_name(null);
            st.setButton_style(null);
            st.setButton_text_color(null);
            st.setLink(null);
            stylesRepository.save(st);
        }

        data.put("icon", "success");
        data.put("message", "Sukses Delete Asset");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
