package org.ngarcia.hibernateapp.services;

import org.ngarcia.hibernateapp.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
   List<Cliente> listar();
   Optional<Cliente> porId(Long id);
   void guardar(Cliente c);
   void eliminar(Long id);
}
