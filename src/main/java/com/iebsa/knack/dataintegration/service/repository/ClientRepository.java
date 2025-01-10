package com.iebsa.knack.dataintegration.service.repository;

import com.iebsa.knack.dataintegration.service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
