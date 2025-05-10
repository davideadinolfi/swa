package org.univaq.swa.template.resources;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.univaq.swa.framework.model.Missione;
import org.univaq.swa.framework.model.Richiesta;
import org.univaq.swa.framework.model.StatoRichiesta;
import org.univaq.swa.framework.security.AuthHelpers;
import org.univaq.swa.template.exceptions.RESTWebApplicationException;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;


@Singleton
@Path("requests")
public class RichiestaRes {
    private static LinkedList<Richiesta> richieste = new LinkedList<>();

    @PostConstruct
    public void init() {
        // Inizializza la lista con alcune richieste di esempio
        for (int i = 0; i < 10; i++) {
            Richiesta richiesta = new Richiesta();
            richiesta.setId(i + 1);
            richiesta.setEmail("email" + (i + 1) + "@example.com");
            richiesta.setIndirizzo("Indirizzo " + (i + 1));
            richiesta.setStato(StatoRichiesta.CHIUSA);
            richiesta.setSuccesso(i % 6);
            richiesta.setConvalida(true);
            richieste.add(richiesta);
        }
    }

@GET
@Path("{id}")
@Produces(MediaType.APPLICATION_JSON)
public Response getById(@PathParam("id") String id) {
    if(id == null || id.isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST).entity("ID non valido").build();
    }
    for (Richiesta r : richieste) {
        if (r.getId() == Integer.parseInt(id)) {
            return Response.ok(r).build();
        }
    }
    return Response.status(Response.Status.NOT_FOUND).build();
}

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(
        @Context UriInfo uriinfo,
        @QueryParam("stato")@DefaultValue("") String stato,
        @QueryParam("page") @DefaultValue("1") int page,
        @QueryParam("size") @DefaultValue("5") int size,
        @QueryParam("successo")@DefaultValue("true")boolean successo) throws RESTWebApplicationException {
       
        List<Richiesta> filtered = richieste.stream()
        .filter(r -> ((stato.isEmpty() || r.getStato().name().equalsIgnoreCase(stato)) &&
        (successo == false || r.getSuccesso() < 5))) // Filtra per stato e successo
        .collect(Collectors.toList());

        int start = (page - 1) * size;
        int end = Math.min(start + size, filtered.size());

        if (start >= filtered.size()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        List<Richiesta> pagina = filtered.subList(start, end);

        return Response.ok(pagina).build();

    
}
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response aggiungiRichiesta(Richiesta richiesta, @Context UriInfo uriInfo) {
    // Simula l'aggiunta assegnando un ID fittizio
    richiesta.setId(richieste.size() + 1);
    richiesta.setStato(StatoRichiesta.ATTIVA); // Imposta lo stato iniziale
    richiesta.setConvalida(false);
    richieste.add(richiesta);

    // Costruisce URI della nuova risorsa
    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(richiesta.getId()));
    return Response.created(builder.build())
                   .entity(richiesta) // ritorna anche il JSON dell’oggetto creato
                   .build();
}
@GET
@Path("{id}/validate")
@Produces(MediaType.APPLICATION_JSON)
public Response convalidaRichiesta(@PathParam("id") int id) {
    for (Richiesta r : richieste) {
        if (r.getId() == id) {
            r.setConvalida(true);
            return Response.ok(r).build();
        }
    }
    return Response.status(Response.Status.NOT_FOUND).entity("Richiesta non trovata").build();
}
@GET
@Path("{id}/reject")
@Produces(MediaType.APPLICATION_JSON)
public Response rifiutaRichiesta(@PathParam("id") int id, @CookieParam("token") String token) {
    if(token==null || token.isEmpty()) {
        return Response.status(Response.Status.UNAUTHORIZED).entity("Token non esistente").build();
    }
    if(! "u".equals(AuthHelpers.getInstance().validateToken(token))) {
        return Response.status(Response.Status.UNAUTHORIZED).entity("Token non valido").build();
    }
    for (Richiesta r : richieste) {
        if (r.getId() == id) {
            r.setStato(StatoRichiesta.IGNORATA);
            return Response.ok(r).build();
        }
    }
    return Response.status(Response.Status.NOT_FOUND).entity("Richiesta non trovata").build();
}

public static Richiesta getRichiestaById(int id) {
  return richieste.stream()
            .filter(r -> r.getId() == id)
            .findFirst()
            .orElse(null);
}

}