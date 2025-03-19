package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateCriteria {
   public static void main(String[] args) {

      EntityManager em = JpaUtil.getEntityManager();

      CriteriaBuilder criteria = em.getCriteriaBuilder();

      System.out.println("---- listar todos los clientes ----");
      CriteriaQuery<Cliente> query = criteria.createQuery(Cliente.class);
      Root<Cliente> from = query.from(Cliente.class);
      query.select(from); //devuelve todos los clientes
      List<Cliente> clientes = em.createQuery(query).getResultList();
      clientes.forEach(System.out::println);


      System.out.println("---- listar con equals ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);

      //Forma 1:
      //query.select(from).where(criteria.equal(from.get("nombre"),"gael"));
      //clientes = em.createQuery(query).getResultList();

      //Forma 2:
      ParameterExpression<String> nombreParam = criteria.parameter(String.class,"nombre");
      query.select(from).where(criteria.equal(from.get("nombre"),nombreParam));
      clientes = em.createQuery(query).setParameter("nombre", "gael").getResultList();

      clientes.forEach(System.out::println);


      System.out.println("---- usando where like ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      //query.select(from).where(criteria.like(from.get("nombre"),"%van%"));
      ParameterExpression<String> nombreLike = criteria.parameter(String.class,"nombreLike");
      query.select(from).where(criteria.like(criteria.upper(from.get("nombre")),criteria.upper(nombreLike)));
      clientes = em.createQuery(query).setParameter("nombreLike","%van%").getResultList();
      clientes.forEach(System.out::println);

      System.out.println("---- usando between ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      query.select(from).where(criteria.between(from.get("id"),2L,4L));
      clientes = em.createQuery(query).getResultList();
      clientes.forEach(System.out::println);

      em.close();
   }
}
