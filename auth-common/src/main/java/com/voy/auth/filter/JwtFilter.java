package com.voy.auth.filter;

import com.alo.base.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voy.auth.model.UserActive;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by Bozo on 19/10/2015.
 */
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        final String token = authHeader.substring(7); // The part after "Bearer "
        User user = new User();
        try {
            final Claims claims = Jwts.parser().setSigningKey("x1x1d3B@l3iA").parseClaimsJws(token).getBody();
            LinkedHashMap<String,Object> hs = (LinkedHashMap) claims.get("userInfo");


            user.setIdPerson(new Long((Integer)hs.get("idPerson")));
            user.setName((String)hs.get("name"));
            user.setLogin((String)hs.get("login"));
            user.setType(User.TypeUser.valueOf((String)hs.get("type")));

            RestTemplate restTemplate = new RestTemplate();
            UserActive userActive = restTemplate.getForObject("https://api-local.voy.com.br:8043/auth-service/isActive/"+user.getIdPerson(), UserActive.class);
            if(userActive.isActive){
                request.setAttribute("loggedUser", user);
            }else{
                throw new ServletException("User has been deactivated!");
            }


        }catch (final ResourceAccessException e) {
            /*
                Erro ao acessar o Validador de usuario,
                Caso isso ocorra indica que o serviço esteja OFF-LINE
                Entao ignoramos esta validação ate o servico voltar
             */
            logger.warn("User Active Validation is disabled!");
            request.setAttribute("loggedUser", user);
        }catch (final SignatureException e) {
            throw new ServletException("Invalid token.");
        }

        chain.doFilter(req, res);
    }


}
