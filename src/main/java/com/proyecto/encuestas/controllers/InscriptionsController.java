
package com.proyecto.encuestas.controllers;

import com.proyecto.encuestas.Library.Paginator;
import com.proyecto.encuestas.model.Inscription;
import com.proyecto.encuestas.model.Inscriptions;
import com.proyecto.encuestas.model.Polls;
import com.proyecto.encuestas.repository.InscriptionsRepository;
import com.proyecto.encuestas.repository.PollRepository;
import com.proyecto.encuestas.repository.ResponsesRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class InscriptionsController {

    @Autowired
    public PollRepository _pollRepository;
    @Autowired
    public InscriptionsRepository _inscriptionsRepository;
    @Autowired
    public ResponsesRepository _responsesRepository;

    @GetMapping(value = {"/inscriptions"})
    public String inscriptions(Model model, HttpSession session, int page, String filtrar) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            Object[] listPolls = new Object[3];
            List<Inscription> list = new ArrayList<>();
            List<Inscription> listData = new ArrayList<>();
            long id_user = (long) user[5];
            List<Inscriptions> inscription = _inscriptionsRepository.findAll()
                    .stream().filter(c -> c.getUser_id() == id_user)
                    .collect(Collectors.toList());
            inscription.forEach(item -> {
                Polls poll = _pollRepository.findById(item.getPoll_id()).get();
                list.add(new Inscription(
                        poll.getId(),
                        poll.getPoll(),
                        poll.getResponses(),
                        item.getResponse(),
                        item.getDate(),
                        item.getId_in()
                ));
            });
            if (filtrar == null) {
                listData = list;
            } else {
                listData = list.stream().filter(e -> e.getPoll().startsWith(filtrar))
                        .collect(Collectors.toList());
            }
            if (!listData.isEmpty()) {
                listPolls = new Paginator<Inscription>().paginator(listData, page, 1, "inscriptions", "http://localhost:8080/");
                model.addAttribute("listPolls", listPolls);
            } else {
                listPolls[0] = "No data";
                listPolls[1] = "No data";
                listPolls[2] = new ArrayList<Inscription>();
                model.addAttribute("listPolls", listPolls);
            }
            return "inscriptions/inscriptions";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = {"/deleteVote"})
    @Transactional
    public String deleteVote(Model model, HttpSession session, long id) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            var inscription = _inscriptionsRepository.findById(id).get();
            var response = _responsesRepository.findById(inscription.getResponse_id()).get();
            var votes = response.getVotes();
            votes--;
            response.setVotes(votes);
            _responsesRepository.save(response);
            _inscriptionsRepository.delete(inscription);
        }
        return "redirect:/inscriptions?page=0&filtrar=";
    }
}
