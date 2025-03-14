package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateCrear {

   public static void main(String[] args) {

      EntityManager em = JpaUtil.getEntityManager();

      try {

         String nombre = JOptionPane.showInputDialog("Ingrese el nombre");
         String apellido = JOptionPane.showInputDialog("Ingrese el apellido");
         String formaPago = JOptionPane.showInputDialog("Ingrese la forma de pago");
         em.getTransaction().begin();

         Cliente c = new Cliente();
         c.setNombre(nombre);
         c.setApellido(apellido);
         c.setFormaPago(formaPago);
         //crea el usuario
         em.persist(c);

         em.getTransaction().commit();

         //persist carga el id en la instancia c
         c = em.find(Cliente.class, c.getId());
         System.out.println("Cliente agregado: " + c);

      } catch (Exception e) {
         em.getTransaction().rollback();
         e.printStackTrace();
      }
      finally {
         em.close();
      }
   }
}
