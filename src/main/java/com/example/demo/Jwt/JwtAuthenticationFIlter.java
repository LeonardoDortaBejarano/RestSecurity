package com.example.demo.Jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFIlter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;
    @Autowired
    private  UserDetailsService userDetailsService;


    //Usado para ejecutar el filtro solo una vez por petición
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getToken(request);
        if (token==null)
        {
            filterChain.doFilter(request, response);
            return;
        }

        //si hay token, verificamos el username
        String username =  jwtService.getUsernameFromToken(token);
        // si no tenemos username y no esta esta cargado en el contexot
        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            //cargamos el user desde la bbdd
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            // si el token es valido 
            if(jwtService.isTokenValid(token,userDetails)){
                // hacemos un minitoken de auth para obtener setterlo en el contex
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // lo seteamos
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        filterChain.doFilter(request, response);
    }

    

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION); //obtenemos la cabecera correspondiente a la auth
        if (StringUtils.hasText(header) && header.startsWith("Bearer ") ) { //si tiene texto y empieza por bearer (que es la opcion de cabecera de auth)
            return header.substring(7); //dame el token
        }
        return null;
    }

}
