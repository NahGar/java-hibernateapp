package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernatePorId {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese el id");
        Long id = s.nextLong();

        //Metodo 1
        Query query = em.createQuery("Select c from Cliente c where c.id=?1", Cliente.class);
        query.setParameter(1, id);
        Cliente c1 = (Cliente) query.getSingleResult();
        System.out.println(c1);

        //Metodo 2
        Cliente c2 = em.find(Cliente.class, id);
        System.out.println(c2);

        em.close();
    }
}
