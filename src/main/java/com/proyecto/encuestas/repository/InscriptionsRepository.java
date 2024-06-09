
package com.proyecto.encuestas.repository;

import com.proyecto.encuestas.model.Inscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InscriptionsRepository extends JpaRepository<Inscriptions, Long>{
    
}
