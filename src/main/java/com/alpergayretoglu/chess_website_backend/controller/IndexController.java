package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.config.SwaggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class IndexController {

    private final SwaggerConfig swaggerConfig;

    @Autowired
    public IndexController(SwaggerConfig swaggerConfig) {
        this.swaggerConfig = swaggerConfig;
    }

    @GetMapping
    public RedirectView redirectToSwagger(HttpServletRequest request) {
        return new RedirectView(String.format("http://%s%s/swagger-ui.html", request.getServerName() + ":" + request.getServerPort(), swaggerConfig.getPath()));
    }

    @RequestMapping(value = "/csrf", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<CsrfToken> getToken(final HttpServletRequest request) {
        return ResponseEntity.ok().body(new HttpSessionCsrfTokenRepository().generateToken(request));
    }

}
