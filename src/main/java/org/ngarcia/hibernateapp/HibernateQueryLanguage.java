package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.dominio.ClienteDto;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.util.JpaUtil;

import java.util.Arrays;
import java.util.List;

public class HibernateQueryLanguage {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        System.out.println("--- consultar todos ----");
        List<Cliente> clientes = em.createQuery("Select c from Cliente c", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("--- consulta por id ----");
        Cliente cliente = em.createQuery("Select c from Cliente c where c.id=:id", Cliente.class)
                .setParameter("id",1L).getSingleResult();
        System.out.println(cliente);

        System.out.println("--- consulta nombre por id ----");
        String nombreCliente = em.createQuery("Select c.nombre from Cliente c where c.id=:id", String.class)
                .setParameter("id",2L).getSingleResult();
        System.out.println(nombreCliente);

        System.out.println("--- consulta nombre y apellido por id ----");
        Object[] campos = em.createQuery("Select c.id, c.nombre, c.apellido from Cliente c where c.id=:id", Object[].class)
                .setParameter("id",3L).getSingleResult();
        System.out.println(campos[0]+" "+campos[1]+" "+campos[2]);

        System.out.println("--- consulta nombre y apellido varios registros ----");
        List<Object[]> registros = em.createQuery("Select c.id, c.nombre, c.apellido from Cliente c", Object[].class)
                .getResultList();
        //for( Object[] registro: registros) {
        //    System.out.println(registro[0]+" "+registro[1]+" "+registro[2]);
        //}
        registros.forEach( registro -> {
            System.out.println(registro[0]+" "+registro[1]+" "+registro[2]);
        });

        System.out.println("--- consulta por cliente y forma de pago ----");
        registros = em.createQuery("Select c, c.formaPago from Cliente c", Object[].class).getResultList();
        registros.forEach( registro -> {
            System.out.println("Cliente: " + registro[0] + " Forma pago: " + registro[1]);
        });

        System.out.println("--- consulta que puebla y devuelve objeto entity de una clase personalizada ----");
        clientes = em.createQuery("Select new Cliente(c.nombre,c.apellido) from Cliente c", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("--- consulta que puebla y devuelve objeto normal (no entity) de una clase personalizada ----");
        List< ClienteDto> clientesDto = em.createQuery("Select new org.ngarcia.hibernateapp.dominio.ClienteDto" +
                "(c.nombre,c.apellido) from Cliente c", ClienteDto.class).getResultList();
        clientesDto.forEach(System.out::println);

        System.out.println("--- consulta con nombres de clientes ----");
        List<String> nombres = em.createQuery("Select c.nombre from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("--- consulta con nombres únicos de clientes ----");
        nombres = em.createQuery("Select distinct(c.nombre) from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("--- consulta cantidad de formas de pago ----");
        Long cantidad = em.createQuery("Select count(distinct(c.formaPago)) from Cliente c", Long.class).getSingleResult();
        System.out.println("Cantidad: " + cantidad);

        System.out.println("--- consulta nombres + apellidos concatenados ----");
        //nombres = em.createQuery("Select concat(c.nombre, ' ', c.apellido) from Cliente c", String.class).getResultList();
        nombres = em.createQuery("Select lower(c.nombre) || ' ' || upper(c.apellido) from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("--- consulta buscar por nomnre ----");
        clientes = em.createQuery("Select c from Cliente c where upper(c.nombre) like :parametro", Cliente.class)
                        .setParameter("parametro","%GA%").getResultList();
        clientes.forEach(System.out::println);

        System.out.println("--- consulta por rango ----");
        //clientes = em.createQuery("Select c from Cliente c where c.id between 2 and 4", Cliente.class).getResultList();
        //en este caso busca E y F, porque G no se incluye
        clientes = em.createQuery("Select c from Cliente c where c.nombre between 'E' and 'G'", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("--- consulta con orden ----");
        clientes = em.createQuery("Select c from Cliente c order by c.formaPago asc, c.nombre desc", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("--- consulta total de registros ----");
        Long total = em.createQuery("Select count(c) from Cliente c", Long.class).getSingleResult();
        System.out.println("Total registros: "+ total);

        System.out.println("--- consulta con valor mínimo ----");
        Long minimo = em.createQuery("Select min(c.id) from Cliente c", Long.class).getSingleResult();
        System.out.println("Id mínimo: "+ minimo);

        System.out.println("--- consulta con valor máximo ----");
        Long maximo = em.createQuery("Select max(c.id) from Cliente c", Long.class).getSingleResult();
        System.out.println("Id máximo: "+ maximo);

        System.out.println("--- consulta nombre y largo ----");
        registros = em.createQuery("Select c.nombre, length(c.nombre) from Cliente c", Object[].class).getResultList();
        registros.forEach( registro -> {
            System.out.println(registro[0]+" ("+registro[1]+")");
        });

        System.out.println("--- consulta con el nombre más corto ----");
        Integer minLargo = em.createQuery("Select min(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println("nombre más largo: "+ minLargo);

        System.out.println("--- consulta con el nombre más largo ----");
        Integer maxLargo = em.createQuery("Select max(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println("nombre más largo: "+ maxLargo);

        System.out.println("--- consulta funciones agregacion ----");
        Object[] stats = em.createQuery("Select min(c.id), max(c.id), sum(c.id), avg(c.id), count(c) from Cliente c", Object[].class).getSingleResult();
        System.out.println("min: "+stats[0]+" max: "+stats[1]+" sum: "+stats[2]+" avg: "+stats[3]+" count: "+stats[4]);

        System.out.println("--- consulta con subconsulta, nombre más largo ----");
        String sql = "Select c.nombre, length(c.nombre) from Cliente c where length(c.nombre) = " +
                "(Select max(length(c.nombre)) from Cliente c)";
        registros = em.createQuery(sql, Object[].class).getResultList();
        registros.forEach( registro -> {
            System.out.println(registro[0]+" ("+registro[1]+")");
        });

        System.out.println("--- consulta con subconsulta, ultimo registro ----");
        sql = "Select c from Cliente c where c.id = (Select max(c.id) from Cliente c)";
        cliente = em.createQuery(sql, Cliente.class).getSingleResult();
        System.out.println(cliente);

        System.out.println("--- consulta con where in ----");
        sql = "Select c from Cliente c where c.id in :ids";
        clientes = em.createQuery(sql, Cliente.class)
                .setParameter("ids", Arrays.asList(1L,2L,50L)).getResultList();
        clientes.forEach(System.out::println);

        em.close();
    }
}
