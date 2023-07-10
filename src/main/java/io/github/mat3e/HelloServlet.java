package io.github.mat3e;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(name = "hello", urlPatterns = {"/api/*"})
public class HelloServlet extends HttpServlet {
    private static final String NameParam = "name";
    private static final String LangParam = "lang";
    private final Logger logger = LoggerFactory.getLogger(HelloServlet.class);

    private HelloService service;
    @SuppressWarnings("unused")
    public HelloServlet(){
        this(new HelloService());
    }
    public HelloServlet(HelloService service){
        this.service = service;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with parameters: " + req.getParameterMap());
        var name = req.getParameter(NameParam);
        var lang = req.getParameter(LangParam);
        resp.getWriter().write(service.prepareGreeting(name, lang));
    }
}
