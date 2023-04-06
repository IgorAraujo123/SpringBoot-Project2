package com.igor.Vendas.rest.controller;

import com.igor.Vendas.domain.entity.Cliente;
import com.igor.Vendas.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }
    @GetMapping( "/api/clientes/{id}" )
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id ){
        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping ("/api/clientes")
    @ResponseBody
    public ResponseEntity save( @RequestBody Cliente cliente ){
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping( "/api/clientes/delete/{id}" )
    @ResponseBody
    public ResponseEntity delete( @PathVariable Integer id ){
        Optional<Cliente> clienteDelete = clientes.findById(id);

        if (clienteDelete.isPresent()){
            clientes.delete( clienteDelete.get() );
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping( "/api/clientes/update/{id}" )
    @ResponseBody
    public ResponseEntity update( @PathVariable Integer id, @RequestBody Cliente cliente ){
        return clientes
                .findById(id)
                .map( ClientesExixtentes ->{
                    cliente.setId( ClientesExixtentes.getId() );
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/api/clientes/buscar")
    @ResponseBody
    public ResponseEntity buscarCliente( Cliente filtro ){
        ExampleMatcher exampleMatcher = ExampleMatcher
                                            .matching()
                                            .withIgnoreCase()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,exampleMatcher);
        List<Cliente> clienteList = clientes.findAll(example);
        return ResponseEntity.ok(clienteList);
    }

}
