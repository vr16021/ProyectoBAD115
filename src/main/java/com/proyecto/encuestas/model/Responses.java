
package com.proyecto.encuestas.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "responses")
@EntityListeners(AuditingEntityListener.class)
public class Responses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String response;
    private Integer votes;
    private Long polls_id;

    public Responses() {
    }

    public Responses(Long id, String response, Integer votes, Long polls_id) {
        this.id = id;
        this.response = response;
        this.votes = votes;
        this.polls_id = polls_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Long getPolls_id() {
        return polls_id;
    }

    public void setPolls_id(Long polls_id) {
        this.polls_id = polls_id;
    }
    
    
}
