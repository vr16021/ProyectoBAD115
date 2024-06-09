
package com.proyecto.encuestas.repository;

import com.proyecto.encuestas.model.Responses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResponsesRepository extends JpaRepository<Responses, Long>{
    
}
