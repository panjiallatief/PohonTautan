package com.PohonTautan.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.UsersRepository;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Users personTemp = new Users();
        personTemp.setUsername(username);
        Users person = new Users();
        boolean stat = usersRepository.getstatus(username);

        try{
            person = usersRepository.getidwithusername(username);
        }catch(NoSuchElementException e){
            throw new BadCredentialsException("Username Tidak ditemukan");
        }
        
        if (encoder.matches(password, person.getPassword()) && stat == true) {
            httpSession.setAttribute("id", person.getUid());
            httpSession.setAttribute("username", person.getUsername());            
            httpSession.setAttribute("role", "user");
        } else if (encoder.matches(password, person.getPassword())) {
            throw new BadCredentialsException("Akun Belum di Aktivasi");
        }else {
            throw new BadCredentialsException("Username/Password Salah");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList();
        grantedAuthorities.add(new SimpleGrantedAuthority((String) httpSession.getAttribute("role")));
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
