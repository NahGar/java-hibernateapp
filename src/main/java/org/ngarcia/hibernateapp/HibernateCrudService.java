package org.ngarcia.hibernateapp;

import jakarta.persistence.EntityManager;
import org.ngarcia.hibernateapp.entity.Cliente;
import org.ngarcia.hibernateapp.services.ClienteService;
import org.ngarcia.hibernateapp.services.ClienteServiceImpl;
import org.ngarcia.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class HibernateCrudService {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        ClienteService service = new ClienteServiceImpl(em);

        List<Cliente> clientes = service.listar();
        clientes.forEach(c-> System.out.println("listar: " + c));

        Optional<Cliente> opt = service.porId(2L);
        opt.ifPresent(System.out::println);

        Cliente clienteInsert = new Cliente();
        clienteInsert.setNombre("Para");
        clienteInsert.setApellido("Borrar");
        clienteInsert.setFormaPago("cabal");
        service.guardar(clienteInsert);

        clientes = service.listar();
        clientes.forEach(c-> System.out.println("agrega cliente: " + c));
        Long idClienteNuevo = clienteInsert.getId();

        Optional<Cliente> opt1 = service.porId(idClienteNuevo);
        opt1.get().setNombre("Borrar");
        opt1.get().setApellido("Para");
        service.guardar(opt1.get());

        clientes = service.listar();
        clientes.forEach(c-> System.out.println("modifica cliente: " + c));

        service.eliminar(idClienteNuevo);
        clientes = service.listar();
        clientes.forEach(c-> System.out.println("elimina cliente: " + c));

        em.close();
    }
}
