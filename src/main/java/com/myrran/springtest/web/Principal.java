package com.myrran.springtest.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/principal")
public class Principal
{
    @GetMapping
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }
}
