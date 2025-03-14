package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateEliminar {

   public static void main(String[] args) {

      EntityManager em = JpaUtil.getEntityManager();

      try {

         Long id = Long.parseLong(JOptionPane.showInputDialog("Id a eliminar"));

         em.getTransaction().begin();

         Cliente c = em.find(Cliente.class, id);
         System.out.println("A eliminar:"+c);
         //Nota: c debe obtenerse con entity manager,
         // no funciona crear una instancia con new()
         em.remove(c);

         em.getTransaction().commit();
      } catch (Exception e) {
         em.getTransaction().rollback();
         e.printStackTrace();
      }
      finally {
         em.close();
      }
   }
}
