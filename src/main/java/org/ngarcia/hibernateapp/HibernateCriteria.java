package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import java.util.ArrayList;
import java.util.Arrays;
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

      System.out.println("---- usando where in ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      //query.select(from).where(from.get("nombre").in("Gael","Micaela"));
      //clientes = em.createQuery(query).getResultList();
      ParameterExpression<List> listParam = criteria.parameter(List.class,"nombres");
      query.select(from).where(from.get("nombre").in(listParam));
      clientes = em.createQuery(query).setParameter("nombres", Arrays.asList("Gael","Micaela")).getResultList();
      clientes.forEach(System.out::println);

      System.out.println("---- usando where > >= ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      //query.select(from).where(criteria.greaterThan(from.get("id"), 3L));
      query.select(from).where(criteria.greaterThanOrEqualTo(from.get("id"), 3L));
      clientes = em.createQuery(query).getResultList();
      clientes.forEach(System.out::println);

      System.out.println("---- usando where < <= ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      //query.select(from).where(criteria.lessThan(from.get("id"), 3L));
      query.select(from).where(criteria.lessThanOrEqualTo(from.get("id"), 3L));
      clientes = em.createQuery(query).getResultList();
      clientes.forEach(System.out::println);

      System.out.println("---- usando and y or ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      Predicate porNombre = criteria.equal(from.get("nombre"),"Gael");
      Predicate porFormaPago = criteria.equal(from.get("formaPago"),"debito");
      Predicate porId = criteria.greaterThanOrEqualTo(from.get("id"),4L);
      //query.select(from).where(criteria.and(porNombre, porFormaPago)); //AND
      //query.select(from).where(criteria.or(porNombre, porFormaPago)); //OR
      query.select(from).where(criteria.and(porId,criteria.or(porNombre, porFormaPago))); //AMBOS
      clientes = em.createQuery(query).getResultList();
      clientes.forEach(System.out::println);

      System.out.println("---- consulta con order by ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      query.select(from).orderBy(criteria.desc(from.get("formaPago")),criteria.asc(from.get("nombre")));
      clientes = em.createQuery(query).getResultList();
      clientes.forEach(System.out::println);

      System.out.println("---- consulta por id ----");
      query = criteria.createQuery(Cliente.class);
      from = query.from(Cliente.class);
      ParameterExpression<Long> idParam = criteria.parameter(Long.class,"id");
      query.select(from).where(criteria.equal(from.get("id"), idParam));
      Cliente cliente = em.createQuery(query).setParameter("id",1L).getSingleResult();
      System.out.println(cliente);

      System.out.println("--- consulta el nombre de los clientes ---");
      CriteriaQuery<String> queryString = criteria.createQuery(String.class);
      from = queryString.from(Cliente.class);
      queryString.select(from.get("nombre"));
      List<String> nombres = em.createQuery(queryString).getResultList();
      nombres.forEach(System.out::println);

      System.out.println("--- consulta de formas de pago con distinct ---");
      queryString = criteria.createQuery(String.class);
      from = queryString.from(Cliente.class);
      queryString.select(criteria.lower(from.get("formaPago"))).distinct(true);
      nombres = em.createQuery(queryString).getResultList();
      nombres.forEach(System.out::println);

      System.out.println("--- consulta de nombre y apellido concatenados ---");
      queryString = criteria.createQuery(String.class);
      from = queryString.from(Cliente.class);
      queryString.select(criteria.concat( criteria.concat(from.get("nombre")," "), from.get("apellido")));
      nombres = em.createQuery(queryString).getResultList();
      nombres.forEach(System.out::println);

      System.out.println("--- consulta de lista de campos ---");
      CriteriaQuery<Object[]> queryObject = criteria.createQuery(Object[].class);
      from = queryObject.from(Cliente.class);
      queryObject.multiselect(from.get("id"), from.get("nombre"));
      List<Object[]> registros = em.createQuery(queryObject).getResultList();
      registros.forEach( req -> {
         System.out.println(req[0]+" "+req[1]);
      });


      em.close();
   }
}
