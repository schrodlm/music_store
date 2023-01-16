package cz.cvut.tjv.music_store_client.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException ex) throws IOException, ServletException {

        // Add a message to the model
        request.setAttribute("message", "You are not authorized to access this page.");
        request.setAttribute("messageTrue", true);

        // Forward to a custom view
        request.getRequestDispatcher("/").forward(request, response);
    }
}