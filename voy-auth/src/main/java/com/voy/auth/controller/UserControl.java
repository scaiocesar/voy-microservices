package com.voy.auth.controller;

import com.voy.auth.model.UserActive;
import com.voy.auth.model.User;
import com.voy.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bozo on 19/10/2015.
 */

@RestController()
public class UserControl {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    PooledPBEStringEncryptor encryptor;

    @RequestMapping(value = "/isActive/{idUser}",method = RequestMethod.GET)
    public UserActive isActive(@PathVariable("idUser") Long idUser) throws ServletException {
        User u = userRepo.findByIdPerson(idUser);
        return new UserActive(!u.getInactive());
    }

    /**
     * Realiza o login do usuario no sitema
     * @param login
     * @return
     * @throws ServletException
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final UserLogin login) throws ServletException {
        User u = userRepo.findByLogin(login.name);

        String passdec = encryptor.decrypt(u.getPasswordWeb());
        if (u == null) {
            throw new ServletException("Login Invalido");
        }else if (!passdec.equals(login.password)){
            throw new ServletException("Problemas com sua senha");
        }else if(u.getInactive()){
            throw new ServletException("Usuario inativo");
        }

        /**
         * Expirar senha
         */
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR,168);

        u.setPasswordWeb("");

        return new LoginResponse(
                    Jwts.builder()
                            .setSubject(login.name)
                            .setId(u.getIdPerson().toString())
                            .setExpiration(c.getTime())
                            .claim("userInfo", u)
                            .setIssuedAt(new Date())
                            .signWith(SignatureAlgorithm.HS256, "x1x1d3B@l3iA").compact());

    }



    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String info() {
        return "OK";
    }

    @SuppressWarnings("unused")
    private static class UserLogin {
        public String name;
        public String password;
    }

    @SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }



}


