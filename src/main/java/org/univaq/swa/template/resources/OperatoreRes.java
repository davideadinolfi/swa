package org.univaq.swa.template.resources;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.univaq.swa.framework.model.Missione;
import org.univaq.swa.framework.model.Operatore;
import org.univaq.swa.framework.model.Richiesta;
import org.univaq.swa.framework.model.Ruolo;
import org.univaq.swa.framework.model.StatoOp;
import org.univaq.swa.framework.model.User;
import org.univaq.swa.template.exceptions.RESTWebApplicationException;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Singleton
@Path("operators")
public class OperatoreRes {
    private LinkedList<Operatore> operatori = new LinkedList<>();
    @PostConstruct
    public void init() {
        // Inizializza la lista con alcuni operatori di esempio
        for (int i = 0; i < 10; i++) {
            Operatore operatore = new Operatore();
            operatore.setId(i + 1);
            operatore.setPassword("password" + (i + 1));
            operatore.setEmail("email" + (i + 1) + "@example.com");
            operatore.setStato(org.univaq.swa.framework.model.StatoOp.LIBERO);
            operatore.addInformazioni("PATENTE " + (i + 1));
            operatore.addInformazioni("SKILL " + (i + 1));
            operatore.addMissione(MissioneRes.getMissioneById(i %3));
            operatore.addMissione(MissioneRes.getMissioneById(i %3 + 1));
            operatori.add(operatore);
        }
}
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLiberi(
        @QueryParam("page") @DefaultValue("1") int page,
        @QueryParam("size") @DefaultValue("5") int size,
        @Context UriInfo uriinfo) throws RESTWebApplicationException {
       
        List<Operatore> filtered = operatori.stream()
        .filter(o -> (o.getStato().equals(StatoOp.LIBERO))) // Filtra per stato e successo
        .collect(Collectors.toList());

        int start = (page - 1) * size;
        int end = Math.min(start + size, filtered.size());

        if (start >= filtered.size()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        List<Operatore> pagina = filtered.subList(start, end);

        return Response.ok(pagina).build();
  
}
    @GET
    @Path("{id}/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistoryById(@PathParam("id") String id) {
        if(id == null || id.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID non valido").build();
        }
        for (Operatore o : operatori) {
            if (o.getId() == Integer.parseInt(id)) {
                return Response.ok(o.getMissioni()).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        if(id == null || id.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID non valido").build();
        }
        for (Operatore o : operatori) {
            if (o.getId() == Integer.parseInt(id)) {
                return Response.ok(o).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}