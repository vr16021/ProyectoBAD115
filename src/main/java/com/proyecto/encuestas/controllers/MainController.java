
package com.proyecto.encuestas.controllers;

import com.proyecto.encuestas.Library.Paginator;
import com.proyecto.encuestas.model.Polls;
import com.proyecto.encuestas.repository.PollRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    public PollRepository _pollRepository;

    @GetMapping(value = {"/main"})
    public String main(HttpSession misession, Model model, int page) {
        Object[] listPolls = new Object[3];
        Object[] user = (Object[]) misession.getAttribute("usersession");
        if (user != null) {
            long id_user = (long) user[5];
            List<Polls> polls = _pollRepository.findAll().stream()
                    .filter(c -> c.getUser_id() == id_user)
                    .collect(Collectors.toList());
            if (!polls.isEmpty()) {
                listPolls = new Paginator().paginator(polls, page, 3, "main", "http://localhost:8080/");
                model.addAttribute("listPolls", listPolls);
            } else {
                listPolls[0] = "No data";
                listPolls[1] = "No data";
                listPolls[2] = new ArrayList<Polls>();
                model.addAttribute("listPolls", listPolls);
            }
            return "main";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = {"/main/*"})
    public String error() {
        return "error";
    }
}
