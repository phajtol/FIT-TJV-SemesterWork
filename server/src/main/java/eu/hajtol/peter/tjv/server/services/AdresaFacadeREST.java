/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hajtol.peter.tjv.server.services;

import eu.hajtol.peter.tjv.server.entities.Adresa;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author peter
 */
@Stateless
@Path("adresa")
public class AdresaFacadeREST extends AbstractFacade<Adresa> {

    @PersistenceContext(unitName = "eu.hajtol.peter.TJV_server_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public AdresaFacadeREST() {
        super(Adresa.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Adresa entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Adresa entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Adresa find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adresa> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adresa> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //custom
    
    @GET
    @Path("search/{query}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adresa> findUsers(@PathParam("query") String query) {
        List<Adresa> allAddresses = super.findAll();
        List<Adresa> res = new ArrayList();

        for (Adresa address : allAddresses) {
            if (
                (address.getCislo() != null && address.getCislo().toString().toLowerCase().contains(query.trim().toLowerCase()))
             || (address.getUlica() != null && address.getUlica().toLowerCase().contains(query.trim().toLowerCase()))
             || (address.getMesto() != null && address.getMesto().toLowerCase().contains(query.trim().toLowerCase()))
             || (address.getPsc() != null && address.getPsc().toLowerCase().contains(query.trim().toLowerCase()))
             || (address.getKrajina() != null && address.getKrajina().toLowerCase().contains(query.trim().toLowerCase()))
               ) {
                res.add(address);
            }
        }
        
        return res;
    }
    
}
