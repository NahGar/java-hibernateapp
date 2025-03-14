package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateEditar {

   public static void main(String[] args) {
      EntityManager em = JpaUtil.getEntityManager();

      try {
         String id = JOptionPane.showInputDialog("Ingrese el id");
         Cliente c = em.find(Cliente.class, Long.valueOf(id));

         String nombre = JOptionPane.showInputDialog("Ingrese el nombre",c.getNombre());
         String apellido = JOptionPane.showInputDialog("Ingrese el apellido",c.getApellido());
         String formaPago = JOptionPane.showInputDialog("Ingrese la forma de pago",c.getFormaPago());

         em.getTransaction().begin();

         //c.setId(Long.valueOf(id));
         c.setNombre(nombre);
         c.setApellido(apellido);
         c.setFormaPago(formaPago);
         //modifica el usuario
         em.merge(c);

         em.getTransaction().commit();

         c = em.find(Cliente.class, c.getId());
         System.out.println("Cliente modificado: " + c);

      } catch (Exception e) {
         em.getTransaction().rollback();
         e.printStackTrace();
      }
      finally {
         em.close();
      }
   }
}
