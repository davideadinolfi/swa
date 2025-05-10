package org.univaq.swa.template.resources;

import java.time.LocalDateTime;
import java.util.LinkedList;

import org.univaq.swa.framework.model.Missione;
import org.univaq.swa.framework.model.MissioneRichiesta;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Singleton
@Path("missions")
public class MissioneRes {
    private static LinkedList<Missione> missioni = new LinkedList<>();
    @PostConstruct
    public void init() {
        // Inizializza la lista con alcune missioni di esempio
        for (int i = 0; i < 10; i++) {
            Missione missione = new Missione();
            missione.setId(i + 1);
            missione.setObiettivo("Obiettivo " + (i + 1));
            missione.setInizio(LocalDateTime.now().plusDays(i));
            missione.setFine(LocalDateTime.now().plusDays(i + 1));
            missione.setCommenti("Commenti " + (i + 1));
            missioni.add(missione);
        }
}
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response aggiungiMissione(MissioneRichiesta m, @Context UriInfo uriInfo) {
    // Simula l'aggiunta assegnando un ID fittizio
    Missione missione = new Missione();
    missione.setObiettivo(m.getObiettivo());
    missione.setCommenti(m.getCommenti());
    missione.setId(missioni.size() + 1);
    try{
    RichiestaRes.getRichiestaById(m.getIdRichiesta()).setMissione(missione);
    }catch (Exception e) {
        return Response.status(Response.Status.BAD_REQUEST).entity("ID non valido").build();
    }
    missioni.add(missione);
    // Costruisce URI della nuova risorsa
    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(missione.getId()));
    return Response.created(builder.build())
                   .entity(missione) // ritorna anche il JSON dell’oggetto creato
                   .build();
}
@GET
@Path("{id}")
@Produces(MediaType.APPLICATION_JSON)
public Response getById(@PathParam("id") String id) {
    if(id == null || id.isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST).entity("ID non valido").build();
    }
    for (Missione m : missioni) {
        if (m.getId() == Integer.parseInt(id)) {
            return Response.ok(m).build();
        }
    }
    return Response.status(Response.Status.NOT_FOUND).entity("Missione non trovata").build();
}
public static Missione getMissioneById(int id) {
    for (Missione m : missioni) {
        if (m.getId() == id) {
            return m;
        }
    }
    return null;
}}
