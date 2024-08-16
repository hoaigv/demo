package com.example.bookshop.exception;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import jakarta.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(filterName = "exceptionHandlerFilter", urlPatterns = "/*")
public class ExceptionHandlerFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response); // Proceed with the next filter
        } catch (Exception e) {
            log.error("Exception in ExceptionHandlerFilter", e);
            throw new TokenAlreadyInvalidatedException(ErrorCode.USER_NOT_FOUND); // Custom exception handling
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
