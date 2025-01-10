package com.iebsa.knack.dataintegration.service.service;

import com.iebsa.knack.dataintegration.service.entity.Client;
import com.iebsa.knack.dataintegration.service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Integer id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Integer id, Client clientDetails) {
        return clientRepository.findById(id).map(client -> {
            client.setFirstName(clientDetails.getFirstName());
            client.setLastName(clientDetails.getLastName());
            client.setIndustry(clientDetails.getIndustry());
            client.setStreetAddress(clientDetails.getStreetAddress());
            client.setStreetAddress2(clientDetails.getStreetAddress2());
            client.setCity(clientDetails.getCity());
            client.setState(clientDetails.getState());
            client.setZipCode(clientDetails.getZipCode());
            client.setPhoneNumber(clientDetails.getPhoneNumber());
            client.setEmail(clientDetails.getEmail());
            client.setStatus(clientDetails.getStatus());
            client.setTotalContractValue(clientDetails.getTotalContractValue());
            return clientRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }

    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
}
