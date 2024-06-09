
package com.proyecto.encuestas.controllers;

import com.proyecto.encuestas.Library.Encrypt;
import com.proyecto.encuestas.model.Login;
import com.proyecto.encuestas.model.User;
import com.proyecto.encuestas.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    @Autowired
    public UserRepository userRepository;

    @GetMapping(value = {"/signup"})
    public String signup(Model model, HttpSession session) {
        Object[] user = (Object[]) session.getAttribute("usersession");
       if (user != null) {
             return "redirect:/main?page=0";
        } else {
            model.addAttribute("user", new User());
            return "signup";
        }
    }

    @PostMapping(value = {"/signup"})
    @Transactional
    public String signup(@Valid User user, BindingResult result, Model model, HttpSession session) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "signup";
        } else {
            List<User> userData = userRepository.findAll().stream()
                    .filter(u -> u.getEmail().equals(user.getEmail()))
                    .collect(Collectors.toList());
            if (userData.isEmpty()) {
                Date utilDate = new Date(); //fecha actual
                user.setIsActive(Boolean.TRUE);
                user.setIsSuperuser(Boolean.FALSE);
                user.setPassword(Encrypt.encrypt(user.getPassword()));
                user.setDateJoined(utilDate);
                user.setLastLogin(utilDate);
                userRepository.save(user);
                Object[] data = {
                    user.getEmail(),
                    user.getFirstName(),
                    user.getIsSuperuser(),
                    user.getLastName(),
                    user.getUserName(),
                    user.getId()
                };
                session.setAttribute("usersession", data);
                 return "redirect:/main?page=0";
            } else {
                result.rejectValue("email", "error.user", "An account already exists for this email.");
                model.addAttribute("user", user);
                return "signup";
            }
        }
    }

    @GetMapping(value = {"/login"})
    public String login(Model model, HttpSession session) {
         Object[] user = (Object[]) session.getAttribute("usersession");
         if (user != null) {
            return "main";
        } else {
            model.addAttribute("login", new Login());
            return "login";
        }
    }

    @PostMapping(value = {"/login"})
    @Transactional
    public String login(@Valid Login login, BindingResult result, Model model, HttpSession session) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("login", login);
            return "login";
        } else {
            List<User> userData = userRepository.findAll().stream()
                    .filter(u -> u.getEmail().equals(login.getEmail()))
                    .collect(Collectors.toList());
            if (userData.isEmpty()) {
                result.rejectValue("email", "error.user", "El email no esta registrado.");
                model.addAttribute("login", login);
                return "login";
            } else {
                String pass = Encrypt.decrypt(userData.get(0).getPassword());
                if (pass.equals(login.getPassword())) {
                    Object[] data = {
                        userData.get(0).getEmail(),
                        userData.get(0).getFirstName(),
                        userData.get(0).getIsSuperuser(),
                        userData.get(0).getLastName(),
                        userData.get(0).getUserName(),
                        userData.get(0).getId()
                    };
                    session.setAttribute("usersession", data);
                     return "redirect:/main?page=0";
                } else {
                    result.rejectValue("password", "error.user", "Contraceña incorrecta.");
                    model.addAttribute("login", login);
                    return "login";
                }
            }
        }
    }
    @GetMapping(value = {"/logout"})
    public String logout(HttpSession session) {
        session.setAttribute("usersession", null);
        return "redirect:/";
    }
    @GetMapping(value = {"/signup/*"})
    public String error(Model model) {
        model.addAttribute("error", "Error al ejecutar la url en signup");
        return "error";
    }
    @GetMapping(value = {"/login/*"})
    public String errors() {
        return "error";
    }
}
