
package com.proyecto.encuestas.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "inscriptions")
@EntityListeners(AuditingEntityListener.class)
public class Inscriptions implements Serializable{
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_in;
    private long poll_id;
    private long response_id;
    private String response;
    private Long user_id;
    @CreatedDate
    private Date date;

    public Inscriptions() {
    }

    public Inscriptions(Long id_in, long poll_id, long response_id, String response, Long user_id, Date date) {
        this.id_in = id_in;
        this.poll_id = poll_id;
        this.response_id = response_id;
        this.response = response;
        this.user_id = user_id;
        this.date = date;
    }

    public Long getId_in() {
        return id_in;
    }

    public void setId_in(Long id_in) {
        this.id_in = id_in;
    }

    public long getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(long poll_id) {
        this.poll_id = poll_id;
    }

    public long getResponse_id() {
        return response_id;
    }

    public void setResponse_id(long response_id) {
        this.response_id = response_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
