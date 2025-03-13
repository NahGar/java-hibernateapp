package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateListarWhere {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("Select c from Cliente c where c.formaPago=?1", Cliente.class);
        System.out.println("Ingrese forma de pago");
        String pago = s.next();
        query.setParameter(1, pago);
        //Cuidado que tiene que devolver un único registro
        //Se puede usar esta línea para forzar que devuelva un único resultado y no tire error
        query.setMaxResults(1);
        Cliente c = (Cliente) query.getSingleResult();
        System.out.println(c);
        em.close();
    }
}
