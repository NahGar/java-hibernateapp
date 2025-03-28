package org.ngarcia.hibernateapp.services;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.repositories.ClienteRepository;
import org.ngarcia.hibernateapp.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {

   private EntityManager em;
   private CrudRepository<Cliente> repository;

   public ClienteServiceImpl(EntityManager em) {
      this.em = em;
      this.repository = new ClienteRepository(em);
   }

   @Override
   public List<Cliente> listar() {
      return repository.listar();
   }

   @Override
   public Optional<Cliente> porId(Long id) {
      return Optional.ofNullable(repository.porId(id));
   }

   @Override
   public void guardar(Cliente c) {
      try {
         em.getTransaction().begin();
         repository.guardar(c);
         em.getTransaction().commit();
      } catch (Exception e) {
         em.getTransaction().rollback();
         e.printStackTrace();
      }
   }

   @Override
   public void eliminar(Long id) {
      try {
         em.getTransaction().begin();
         repository.eliminar(id);
         em.getTransaction().commit();
      } catch (Exception e) {
         em.getTransaction().rollback();
         e.printStackTrace();
      }
   }
}
