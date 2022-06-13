package net.anumbrella.sso.controller;

import net.anumbrella.sso.entity.User;
import org.apache.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anumbrella
 */
@RestController
public class RestAttributeController {

    @PostMapping("/attributes")
    public Object getAttributes(@RequestHeader HttpHeaders httpHeaders) {
        User user = new User();
        user.setEmail("rest@gmail.com");
        user.setUsername("email");
        user.setPassword("123");
        List<String> role = new ArrayList<>();
        role.add("admin");
        role.add("dev");
        user.setRole(role);
        //成功返回json
        return user;
    }
}
