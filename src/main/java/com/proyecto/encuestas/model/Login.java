
package com.proyecto.encuestas.model;

import javax.validation.constraints.NotBlank;


public class Login {
     @NotBlank(message = "El password es requerido")
    private String password;
    @NotBlank(message = "El email es requerido")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
