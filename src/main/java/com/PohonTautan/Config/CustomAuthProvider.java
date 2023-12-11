// package com.PohonTautan.Config;

// // import java.util.NoSuchElementException;

// // import javax.servlet.http.HttpSession;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.data.domain.Example;
// import org.springframework.security.authentication.AuthenticationProvider;
// // import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// // import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;


// @Component
// public class CustomAuthProvider implements AuthenticationProvider {


//     // @Autowired
//     // private HttpSession httpSession;

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         return authentication;
//         // String idcard = authentication.getName();
//         // String password = " ";
//         // Cameraman cameramanTemp = new Cameraman();
//         // cameramanTemp.setNo_IdCard(idcard);
//         // Cameraman cameraman = new Cameraman();
//         // try{
//         //     cameraman = cameramanRepository.findOne(Example.of(cameramanTemp)).get();
//         // }catch(NoSuchElementException e){
//         //     throw new BadCredentialsException("User Tidak ditemukan");
//         // }
           
//         //     httpSession.setAttribute("username", cameraman.getNama_cameraman());
//         //     httpSession.setAttribute("no_idcard", cameraman.getNo_IdCard());
//         //     httpSession.setAttribute("role", "Reporter");
        

//         // return new UsernamePasswordAuthenticationToken(idcard, password);
//     }

//     @Override
//     public boolean supports(Class<?> authentication) {
//         return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//     }

// }
