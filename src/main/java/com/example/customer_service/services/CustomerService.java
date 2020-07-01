package com.example.customer_service.services;

import com.example.customer_service.models.Customer;
import com.example.customer_service.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<Customer> getAll(){
        if (repository.count() == 0)
            throw new EntityNotFoundException();
        return repository.findAll();
    }

    public Customer save(Customer customer){
        return repository.save(customer);
    }

    public Customer update(Customer customer){
        if (!repository.equals(customer))
            throw new EntityNotFoundException();
        return repository.save(customer);
    }

    public Customer get(int id){
        if (!repository.existsById(id))
            throw new EntityNotFoundException();
        return repository.getOne(id);
    }
    public void delete(int id){
        if (!repository.existsById(id))
            throw new EntityNotFoundException();
        repository.deleteById(id);
    }
}
