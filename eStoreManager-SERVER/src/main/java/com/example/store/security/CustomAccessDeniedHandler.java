package com.example.store.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.store.payload.common.response.ApiResponse;
import com.google.gson.Gson;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private Gson gson = new Gson();

    @Override
    public void handle(HttpServletRequest httpServletRequest, 
                    HttpServletResponse httpServletResponse, 
                    AccessDeniedException e) throws IOException, ServletException {
        PrintWriter out = httpServletResponse.getWriter();
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        out.print(this.gson.toJson(new ApiResponse(false, "access_denied", "You don't have permission to access this resource")));
        out.flush();
    }

}