
package com.proyecto.encuestas.repository;

import com.proyecto.encuestas.model.Polls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PollRepository extends JpaRepository<Polls, Long>{
    
}
