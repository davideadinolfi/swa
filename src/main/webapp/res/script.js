const PAGE_SIZE = 5;
let currentPage = 1;
let stato = "null"; // Stato iniziale del filtro
let richieste = [];
let filtroSuccesso = false; // Flag per il filtro di successo



function renderTable() {
    if (richieste.length === 0) {
        document.getElementById('table-container').innerHTML = "<p>Nessuna richiesta trovata.</p>";
        document.getElementById('next').disabled = true;
        return;
    }

    let html = '<table><tr><th>Email</th><th>Indirizzo</th><th>Stato</th><th>Convalida</th><th>Missione</th><th>successo</th></tr>';
    for (const r of richieste) {
 
        html += `<tr>
            <td><a href="rest/requests/${r.id}">${r.email}</a></td>
            <td><a href="rest/requests/${r.id}">${r.indirizzo}</a></td>
            <td><a href="rest/requests/${r.id}">${r.stato}</a></td>
            <td><a href="rest/requests/${r.id}">${r.convalida}</a></td>
            `
            if(r.missione!=null){html +=`
            <td><a href="rest/missions/${r.missione.id}">${r.missione.obiettivo}</a></td>`;}
            else{html +=`<td>missione non presente</td>`;}
            html += `<td><a href="rest/requests/${r.id}">${r.successo}</a></td>
        </tr>`;
    }
    html += '</table>';

    document.getElementById('table-container').innerHTML = html;
    document.getElementById('page-number').textContent = currentPage;
}



    
document.getElementById('missioneForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const idRichiesta = document.getElementById('idR').value;
    const obiettivo = document.getElementById('obiettivo').value;
    const commenti = document.getElementById('commenti').value;
    

    fetch('rest/missions', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            idRichiesta: idRichiesta,
            obiettivo: obiettivo,
            commenti: commenti,

        })
    })
    .then(response => {
        if (!response.ok) throw new Error(`Errore: ${response.status}`);
        return response.json();
    })
    .then(data => {
        document.getElementById('rispostaMissione').textContent = JSON.stringify(data, null, 2);
    })
    .catch(error => {
        document.getElementById('rispostaMissione').textContent = error;
    });
});



const checkbox = document.getElementById("filtroSuccesso");

    checkbox.addEventListener("change", () => {
        const value = checkbox.checked ? "true" : "false";
        document.getElementById("filtroSuccesso").value = value;
    });


function applicaFiltri() {
    stato = document.getElementById("filtroStato").value;
    filtroSuccesso = document.getElementById("filtroSuccesso").value;
    document.getElementById('next').disabled = false;
    fetchRichieste(); // parte sempre da pagina 1
}

function fetchRichieste() {
    const url = new URL("soccorsoWEB/rest/requests",window.location.origin);
    url.searchParams.set("page", currentPage);
    url.searchParams.set("size", PAGE_SIZE);
    url.searchParams.set("successo", filtroSuccesso);
    if (stato !== "null") {
        url.searchParams.set("stato", stato);
    }
    fetch(url)
        .then(response => {
            if (response.status === 204) {
                return [];
            }
            return response.json();
        })
        .then(data => {
            richieste = data;
            renderTable();
        })
        .catch(error => {
            document.getElementById('table-container').innerHTML = `<p style="color:red;">Errore: ${error}</p>`;
        });
}

document.getElementById('next').addEventListener('click', () => {
    currentPage++;
    fetchRichieste(); // 👈 richiama il server con la nuova pagina
});

document.getElementById('prev').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        document.getElementById('next').disabled = false;
        fetchRichieste(); // 👈 richiama il server con la nuova pagina
    }
});


// Avvio iniziale
fetchRichieste();


document.getElementById('richiestaForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const indirizzo = document.getElementById('indirizzo').value;
    

    fetch('rest/requests', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            indirizzo: indirizzo,

        })
    })
    .then(response => {
        if (!response.ok) throw new Error(`Errore: ${response.status}`);
        return response.json();
    })
    .then(data => {
        document.getElementById('risposta').textContent = JSON.stringify(data, null, 2);
    })
    .catch(error => {
        document.getElementById('risposta').textContent = error;
    });
});

let currentPageUtenti = 1;
const pageSize = 5;

function fetchUtenti(pageUtenti = 1) {
  fetch(`rest/operators?page=${pageUtenti}&size=${pageSize}`)
    .then(res => {
      if (res.status === 204) {
        document.querySelector("#utentiTable tbody").innerHTML = "<tr><td colspan='4'>Nessun utente trovato.</td></tr>";
        return [];
      }
      return res.json();
    })
    .then(data => {
      const tbody = document.querySelector("#utentiTable tbody");
      tbody.innerHTML = "";

      if (!Array.isArray(data) || data.length === 0) {
        tbody.innerHTML = "<tr><td colspan='4'>Nessun utente trovato.</td></tr>";
        return;
      }

      data.forEach(utente => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td><a href="rest/operators/${utente.id}">${utente.id}</a></td>
          <td><a href="rest/operators/${utente.id}">${utente.email}</a</td>
        `;
        tbody.appendChild(row);
      });

      document.getElementById("pageInfoUtenti").innerText = `Pagina ${currentPageUtenti}`;
    })
    .catch(error => {
      console.error("Errore nel fetch utenti:", error);
    });
}

document.getElementById("prevPageUtenti").addEventListener("click", () => {
  if (currentPageUtenti > 1) {
    currentPageUtenti--;
    fetchUtenti(currentPageUtenti);
  }
});

document.getElementById("nextPageUtenti").addEventListener("click", () => {
  currentPageUtenti++;
  fetchUtenti(currentPageUtenti);
});

// Primo caricamento
fetchUtenti(currentPageUtenti);