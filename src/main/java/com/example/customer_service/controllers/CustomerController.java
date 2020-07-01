package com.example.customer_service.controllers;

import com.example.customer_service.models.Customer;
import com.example.customer_service.models.CustomerDTO;
import com.example.customer_service.repositories.CustomerRepository;
import com.example.customer_service.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController()
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("**/isVerified/{id}")
    public ResponseEntity isVerified(@PathVariable int id){
        Customer customer;

        try {
            customer = service.get(id);
        }
        catch (EntityNotFoundException exception){
            return new ResponseEntity("Not found customer with ID: " + id,HttpStatus.NOT_FOUND);
        }
        if (customer.isAuthorization())
            return new ResponseEntity("Customer is authorized",HttpStatus.OK);
        else
            return new ResponseEntity("Customer is unauthorized",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "**/customers")
    public ResponseEntity getAll(){
        List<Customer> customers;

        try {
            customers = service.getAll();
        }
        catch (EntityNotFoundException exception){
            return new ResponseEntity("Database is Empty",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customers,HttpStatus.FOUND);
    }

    @GetMapping(value = "**/{id}")
    public ResponseEntity get(@PathVariable int id){
        Customer customer;

        try {
            customer = service.get(id);
        }
        catch (EntityNotFoundException exception){
            return new ResponseEntity("Not found customer with ID: " + id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customer,HttpStatus.FOUND);
    }

    @DeleteMapping(value = "**/{id}")
    public ResponseEntity delete(@PathVariable int id){
        try {
            service.delete(id);
        }
        catch (EntityNotFoundException exception){
            return new ResponseEntity("Not found customer with ID: " + id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Deleted customer with ID: " + id,HttpStatus.OK);
    }

    @PostMapping(value = "**/save")
    public ResponseEntity save(@RequestBody CustomerDTO customerDTO){
        Customer createdCustomer;
        Customer customer = new Customer();
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setAuthorization(customerDTO.isAuthorization());

        try {
            System.out.println(customerDTO.toString());
            createdCustomer = service.save(customer);
            System.out.println(createdCustomer.toString());
        }
        catch (Exception e){
            return new ResponseEntity("Create failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(createdCustomer,HttpStatus.CREATED);
    }

    @PostMapping(value = "**/update")
    public ResponseEntity update(@RequestBody Customer customer){
        Customer updatedCustomer;
        try {
            updatedCustomer = service.update(customer);
        }
        catch (EntityNotFoundException exception){
            return new ResponseEntity("Not found customer",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedCustomer,HttpStatus.OK);
    }
}
