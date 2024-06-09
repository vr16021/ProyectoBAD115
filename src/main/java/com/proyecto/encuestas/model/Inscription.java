
package com.proyecto.encuestas.model;

import java.util.Date;


public class Inscription {

    private Long id;
    private String poll;
    private Integer responses;
    private String response;
    private Date date;
    private Long id_in;

    public Inscription(Long id, String poll, Integer responses, String response, Date date,Long id_in) {
        this.id = id;
        this.poll = poll;
        this.responses = responses;
        this.response = response;
        this.date = date;
        this.id_in = id_in;
    }

    public Long getId() {
        return id;
    }

    public String getPoll() {
        return poll;
    }

    public Integer getResponses() {
        return responses;
    }

    public String getResponse() {
        return response;
    }

    public Date getDate() {
        return date;
    }

    public Long getId_in() {
        return id_in;
    }

}
