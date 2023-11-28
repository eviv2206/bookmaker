package by.bsuir.bookmaker.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute("language");
        if (language == null) {
            session.setAttribute("language", "en");
            req.setAttribute("language", "en");
        } else {
            req.setAttribute("language", language);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
