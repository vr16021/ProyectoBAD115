
package com.proyecto.encuestas.controllers;

import com.proyecto.encuestas.model.Inscriptions;
import com.proyecto.encuestas.model.Polls;
import com.proyecto.encuestas.model.Responses;
import com.proyecto.encuestas.repository.InscriptionsRepository;
import com.proyecto.encuestas.repository.PollRepository;
import com.proyecto.encuestas.repository.ResponsesRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PollsController {

    @Autowired
    public PollRepository _pollRepository;
    @Autowired
    public ResponsesRepository _responsesRepository;
    @Autowired
    public InscriptionsRepository _inscriptionsRepository;

    @GetMapping(value = {"/createpoll"})
    public String createpoll(Model model, HttpSession session) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            return "polls/createpoll";
        } else {
            return "signup";
        }
    }

    @PostMapping(value = {"/registerpoll"})
    public String registerpoll(String[] response, String poll, HttpSession session) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            Date utilDate = new Date(); //fecha actual
            Polls polls = new Polls();
            polls.setPoll(poll);
            polls.setResponses(response.length);
            polls.setDate(utilDate);
            polls.setUser_id((Long) user[5]);
            long id = _pollRepository.save(polls).getId();
            for (String item : response) {
                Responses responses = new Responses();
                responses.setResponse(item);
                responses.setVotes(0);
                responses.setPolls_id(id);
                _responsesRepository.save(responses);
            }
            return "redirect:/main?page=0";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = {"/details"})
    public String details(HttpSession misession, Model model, long id) {
        Polls poll = _pollRepository.findById(id).get();
        List<Responses> responses = _responsesRepository.findAll()
                .stream().filter(c -> c.getPolls_id() == id)
                .collect(Collectors.toList());
        int votes = 0;
        int cout = responses.size();
        List<Object> responses2 = new ArrayList<>();
        for (int i = 0; i < cout; i++) {
            responses2.add(responses.get(i).getResponse());
            responses2.add(responses.get(i).getVotes());
            votes += responses.get(i).getVotes();
        }
        model.addAttribute("poll", poll);
        model.addAttribute("responses", responses2);
        model.addAttribute("votes", votes);
        return "polls/details";
    }
    private static long _id;
    private static boolean value = false;

    @GetMapping(value = {"/votes"})
    public String votes(Model model, HttpSession session, long id) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            _id = id;
            long id_user = (long) user[5];
            boolean action = true;
            Polls poll = _pollRepository.findById(id).get();
            List<Responses> responses = _responsesRepository.findAll()
                    .stream().filter(c -> c.getPolls_id() == id)
                    .collect(Collectors.toList());
            List<Inscriptions> inscription = _inscriptionsRepository.findAll()
                    .stream().filter(c -> c.getPoll_id() == id
                    && c.getUser_id() == id_user)
                    .collect(Collectors.toList());
            if (!inscription.isEmpty()) {
                action = false;
            }
            model.addAttribute("action", action);
            model.addAttribute("poll", poll);
            model.addAttribute("responses", responses);
            if (value) {
                model.addAttribute("message", "Seleccione una respuesta");
            }
            value = false;
            return "polls/votes";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = {"/votes"})
    public String insertVotes(Model model, HttpSession session, String response) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            if (response != null) {
                long id = Long.valueOf(response);
                Responses res = _responsesRepository.findById(id).get();
                Integer vote = res.getVotes();
                vote++;
                res.setVotes(vote);
                _responsesRepository.save(res);
                long id_user = (long) user[5];
                Date utilDate = new Date();
                Inscriptions inscription = new Inscriptions();
                inscription.setId_in(0l);
                inscription.setPoll_id(_id);
                inscription.setUser_id(id_user);
                inscription.setResponse(res.getResponse());
                inscription.setResponse_id(id);
                inscription.setDate(utilDate);
                _inscriptionsRepository.save(inscription);
                return "redirect:/details?id=" + _id;
            } else {
                value = true;
            }
            return "redirect:/votes?id=" + _id;
        } else {
            return "signup";
        }
    }

    @GetMapping(value = {"/delete"})
    @Transactional
    public String delete(Model model, HttpSession session, long id) {
        Object[] user = (Object[]) session.getAttribute("usersession");
        if (user != null) {
            List<Responses> responses = _responsesRepository.findAll()
                    .stream().filter(c -> c.getPolls_id() == id)
                    .collect(Collectors.toList());
            responses.forEach(item -> {
                _responsesRepository.delete(item);
            });
            Polls poll = _pollRepository.findById(id).get();
            _pollRepository.delete(poll);
        }
        return "redirect:/main?page=0&filtrar=";
    }
}
