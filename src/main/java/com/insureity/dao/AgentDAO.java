package com.insureity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insureity.model.Agent;

/**
 * 
 */


/**JPA Repository which interacts with database*/
@Repository
public interface AgentDAO extends JpaRepository<Agent, String> {

}
