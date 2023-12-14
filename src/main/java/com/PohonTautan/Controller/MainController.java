package com.PohonTautan.Controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.text.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PohonTautan.Entity.Log;
import com.PohonTautan.Entity.Sessionid;
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
    public String adm(Model model) throws SQLException {

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();
        Styles st = stylesRepository.getstStyles2(usnn);
        Date date = new Date();
        // List<Styles> stylesList = new ArrayList<>();
        List<Map<String, Object>> stylesList = new ArrayList<>();
        Map<String, Object> styleMap = new HashMap<>();
        Long countday = sessionoidRepositori.countsession(date, usnn);
        model.addAttribute("countday", countday);

        String[] btn = null;
        String[] btnstyle = null;
        String[] link = null;
        String[] btnanim = null;
        String[] btntc = null;

        if (st.getButton_name() != null || st.getButton_style() != null || st.getLink() != null
                || st.getButton_animation() != null || st.getButton_text_color() != null) {

            if (st.getButton_name() != null) {
                btn = st.getButton_name().split(",");
            }
            if (st.getButton_style() != null) {
                btnstyle = st.getButton_style().split(",");
            }
            if (st.getLink() != null) {
                link = st.getLink().split(",");
            }
            if (st.getButton_animation() != null) {
                btnanim = st.getButton_animation().split(",");
            }
            if (st.getButton_text_color() != null) {
                btntc = st.getButton_text_color().split(",");
            }

            Blob blob = st.getBg();
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            String base64String = Base64.getEncoder().encodeToString(bytes);
            String gambars = base64String.replace("dataimage/pngbase64",
                    "data:image/png;base64,");
            String bg = gambars.replace("=", "");

            Blob blobs = st.getImage();
            byte[] bytess = blobs.getBytes(1, (int) blobs.length());
            String base64Strings = Base64.getEncoder().encodeToString(bytess);
            String gambarss = base64Strings.replace("dataimage/pngbase64",
                    "data:image/png;base64,");
            String image = gambarss.replace("=", "");

            for (Integer i = 0; i < link.length; i++) {
                styleMap.put("tempBg", bg);
                styleMap.put("tempImg", image);
                styleMap.put("button_name", btn[0]);
                styleMap.put("button_style", "#" + btnstyle[0]);
                styleMap.put("custom_url", st.getCustom_url());
                styleMap.put("id_user", st.getId_user());
                styleMap.put("created_at", st.getCreatedAt());
                styleMap.put("updated_at", st.getUpdatedAt());
                styleMap.put("id_style", st.getId_style());
                styleMap.put("headline", st.getHeadline());
                styleMap.put("bio", st.getBio());
                styleMap.put("bg_default", st.getBg_default());
                styleMap.put("link", link[0]);
                styleMap.put("button_animation", btnanim[0]);
                styleMap.put("button_text_color", btntc[0]);
                stylesList.add(styleMap);
            }

        } else {
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
            styleMap.put("bg_default", null);
            styleMap.put("link", null);
            styleMap.put("button_animation", null);
            styleMap.put("button_text_color", null);
            stylesList.add(styleMap);
        }

        model.addAttribute("btnstyle", stylesList);

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
            @RequestParam String[] buttonname, @RequestParam String bg, @RequestParam String image,
            @RequestParam String curl)
            throws SerialException, SQLException {
        Map data = new HashMap<>();

        String usn = httpSession.getAttribute("username").toString();
        Integer usnn = usersRepository.getidwithusername(usn).getUid();

        if (!stylesRepository.booleanstyle(usnn)) {
            String gambarimage = image.replace(" ", "+");
            byte[] binarydataimage = Base64.getMimeDecoder().decode(gambarimage);
            Blob blobimage = new SerialBlob(binarydataimage);

            String gambarbg = image.replace(" ", "+");
            byte[] binarydatabg = Base64.getMimeDecoder().decode(gambarbg);
            Blob blobbg = new SerialBlob(binarydatabg);

            String A = null;
            String B = null;
            String C = null;

            if (link != null) {
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
    public ResponseEntity<Map> inputbutton(@RequestParam(required = false) String[] tombol,
            @RequestParam(required = false) String[] tautan,
            @RequestParam(required = false) String[] buttonname,
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

        String A = Arrays.toString(tautan).replace("[", "").replace("]", "");
        String B = Arrays.toString(tombol).replace("[", "").replace("]", "");
        String C = Arrays.toString(buttonname).replace("[", "").replace("]", "");
        String D = Arrays.toString(buttonanim).replace("[", "").replace("]", "");
        String E = Arrays.toString(buttoncolortext).replace("[", "").replace("]", "");

        String cek = st.getLink();

        if (cek != null) {
            System.out.println("masuk if");
            st.setLink(st.getLink() + "," + A);
            st.setButton_style(st.getButton_style() + "," + B);
            st.setButton_name(st.getButton_name() + "," + C);
            st.setButton_animation(st.getButton_animation() + "," + D);
            st.setButton_text_color(st.getButton_text_color() + "," + E);
            stylesRepository.save(st);
        } else {
            System.out.println("masuk else");
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

        Integer index = Arrays.asList(link).indexOf(tautan);

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

        st.setButton_animation(D);
        st.setButton_name(A);
        st.setButton_style(B);
        st.setButton_text_color(E);
        st.setLink(C);
        stylesRepository.save(st);

        data.put("icon", "success");
        data.put("message", "Sukses Delete Asset");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
