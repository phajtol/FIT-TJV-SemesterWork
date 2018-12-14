/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hajtol.peter.tjv.server.services;

import eu.hajtol.peter.tjv.server.entities.Uzivatel;
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
@Path("uzivatel")
public class UzivatelFacadeREST extends AbstractFacade<Uzivatel> {

    @PersistenceContext(unitName = "eu.hajtol.peter.TJV_server_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UzivatelFacadeREST() {
        super(Uzivatel.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Uzivatel entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Uzivatel entity) {
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
    public Uzivatel find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Uzivatel> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Uzivatel> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Uzivatel> findUsers(@PathParam("query") String query) {
        List<Uzivatel> allUsers = super.findAll();
        List<Uzivatel> res = new ArrayList();

        for (Uzivatel user : allUsers) {
            if (
                (user.getMeno() != null && user.getMeno().toLowerCase().contains(query.trim().toLowerCase()))
             || (user.getPriezvisko() != null && user.getPriezvisko().toLowerCase().contains(query.trim().toLowerCase()))
             || (user.getNick() != null && user.getNick().toLowerCase().contains(query.trim().toLowerCase()))
             || (user.getMail() != null && user.getMail().toLowerCase().contains(query.trim().toLowerCase()))
             || (user.getTelefon() != null && user.getTelefon().toLowerCase().contains(query.trim().toLowerCase()))
               ) {
                res.add(user);
            }
        }
        
        return res;
    }
    
}
