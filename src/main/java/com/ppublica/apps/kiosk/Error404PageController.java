package com.ppublica.apps.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.Error404PageService;
import com.ppublica.apps.kiosk.service.views.error.Error404PageView;

@Controller
public class Error404PageController {

    @Autowired
    private Error404PageService service;
    
    @QueryMapping
    public Error404PageView error404Page(@Argument String locale) {
        // todo: decide error for page not found

        Error404PageView error404PageView = service.getError404Page(locale).get();

        return error404PageView;
    }
    
}
