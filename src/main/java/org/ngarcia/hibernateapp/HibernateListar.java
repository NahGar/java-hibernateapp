package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateListar {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();
        List<Cliente> clientes = em.createQuery("Select c from Cliente c", Cliente.class).getResultList();
        clientes.forEach(c-> System.out.println(c));
        em.close();
    }
}
