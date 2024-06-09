
package com.proyecto.encuestas.controllers;

import com.proyecto.encuestas.Library.Paginator;
import com.proyecto.encuestas.model.Polls;
import com.proyecto.encuestas.repository.PollRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
 @Autowired
    public PollRepository _pollRepository;
 
    @GetMapping("/")
    public String index(Model model) {
        poll(model, 1, "");
        return "index";
    }
    @GetMapping("/index")
    public String index(Model model, int page, String filtrar) {
        String data = filtrar == null ? "" : filtrar;
        poll(model, page, data);
        return "index";
    }
    private void poll(Model model, int page, String filtrar){
        Object[] listPolls = new Object[3];
        List<Polls> polls = _pollRepository.findAll().stream()
                    .filter(c -> c.getPoll().startsWith(filtrar))
                    .collect(Collectors.toList());
        if (!polls.isEmpty()){
            listPolls = new Paginator().paginator(polls, page, 3,"index", "http://localhost:8080/");
             model.addAttribute("listPolls", listPolls);
        }else{
            listPolls[0] = "No data";
            listPolls[1] = "No data";
            listPolls[2] = new ArrayList<Polls>();
            model.addAttribute("listPolls", listPolls);
        }
    }
}
