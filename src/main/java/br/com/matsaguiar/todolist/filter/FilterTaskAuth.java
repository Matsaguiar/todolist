package br.com.matsaguiar.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.matsaguiar.todolist.user.UserModel;
import br.com.matsaguiar.todolist.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if(request.getServletPath().startsWith("/rest/tasks/")){
            var authorization = request.getHeader("Authorization");
            byte[] authDecode = Base64.getDecoder().decode(authorization.substring("Basic".length()).trim());
            String[] authString = new String(authDecode).split(":");
            
            UserModel user = userService.findByUserName(authString[0]);
            byte[] encrypted = userService.sha512(authString[1]); 
            String password = Base64.getEncoder().encodeToString(encrypted);
            String passwordBase = Base64.getEncoder().encodeToString(user.getPasswordEncoded());
            if(user == null || !password.equals(passwordBase))
                throw new UnsupportedOperationException("Usuário ou senha inválidos!");
            else
            	request.setAttribute("idUser", user.getId());
        }
        
        filterChain.doFilter(request, response);
    }
    
}
