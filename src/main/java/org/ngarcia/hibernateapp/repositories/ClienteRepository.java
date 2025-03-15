package org.ngarcia.hibernateapp.repositories;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.entity.Cliente;

import java.util.List;

public class ClienteRepository implements CrudRepository<Cliente>{

   private EntityManager em;

   public ClienteRepository(EntityManager em) {
      this.em = em;
   }

   @Override
   public List<Cliente> listar() {
      return em.createQuery("Select c from Cliente c", Cliente.class).getResultList();
   }

   @Override
   public Cliente porId(Long id) {
      return em.find(Cliente.class, id);
   }

   @Override
   public void guardar(Cliente cliente) {
      if(cliente.getId() == null || cliente.getId() == 0) {
         em.persist(cliente);
      }
      else {
         em.merge(cliente);
      }
   }

   @Override
   public void eliminar(Long id) {
      Cliente c = porId(id);
      em.remove(c);
   }
}
