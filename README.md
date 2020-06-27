
# Progetto Finale - Ingegneria del Software

  

##### Francesco Gonzales ([fulcus](https://github.com/fulcus))

##### Alberto Latino ([albertolatino](https://github.com/albertolatino))

##### Vittorio Fabris ([VittoFab](https://github.com/VittoFab))

------------------------------------------------------

## Come far partire il gioco ##

### Server

Posizionarsi nella cartella `jar/server` da terminale e utilizzare il comando `java -jar server.jar`

### Client

Per ogni client: posizionarsi nella cartella `jar/client` da terminale e utilizzare il comando `java -jar client.jar`

  

------------------------------------------------------

## 1. Test Coverage ##

- Sono stati implementati i test del model e controller cercando di ottenere una copertura quasi completa del codice. Viene riportato di seguito il report della copertura.

- Overview generale dell'analisi:

![Overview](/sonar/overview.PNG)

- Test coverage:

![Coverage](/sonar/coverage.PNG)

------------------------------------------------------

## 2. UML ##

- Al fine di massimizzare la fruibilità e la leggibilità dei diagrammi delle classi UML si è deciso di creare un diagramma diverso per ogni package principale. In quest'ottica, i collegamenti tra classi di package differenti sono stati realizzati sostituendo alla classe esterna al package una referenza ad essa (la rappresentazione completa di tale classe è accessibile attraverso il diagramma del package corrispondente);

- <a href="#"> Model class diagram </a>

- <a href="#"> View class diagram </a>

- <a href="#"> Cards class diagram </a>

- <a href="#"> Controller class diagram </a>

- <a href="#"> Network class diagram </a>

- <a href="#"> Utils class diagram </a>

------------------------------------------------------

## 3. Funzionalità implementate ##

- Regole complete

- Connessione Socket

- CLI

- GUI

- ### Funzionalità avanzate

- Divinità Avanzate

- Partite Multiple

------------------------------------------------------

## 4. Scelte implementative ##

  

- ### MVC

L'implementazione del pattern MVC scelta è quella del "hybrid" ovvero una parte dei controlli su input viene fatta anche lato client per minimizzare l'utilizzo della rete e il carico del server.

  

- #### Model

Possiede lo stato dell'applicazione.

- #### View

Fornisce all'utente una rappresentazione del model.

- #### Controller

Possiede la logica dell'applicazione e appa gli input degli utenti provenienti dalla view per effettuare cambiamenti sul model.

![MVC](dMVC.png)

- ### Observers

Per realizzare un meccanismo di notifica è stato utilizzato un pattern __observer__. In particolare, il model e le view rappresentano rispettivamente l'osservato e osservatore.

Per quanto riguarda la connessione socket è stato necessario duplicare il pattern observer per permettere la trasmissione delle notifiche (view osserva SocketClientController, SocketThread osserva il model).

- ### Network

Si è deciso di progettare la comunicazione tra server e client in modo da rendere indipendente la comunicazione dal tipo di interfaccia grafica scelta dall'utente. Il server manda quindi sempre gli stessi messaggi indipendentemente che il client abbia scelto di giocare tramite CLI o GUI. Inoltre, vengono scambiate due tipi di informazioni: quelle inerenti al gioco e quelle utili per monitorare e gestire lo stato di connessione dei clients (PING).

All'interno del server, vi è una classe che rappresenta la virtual view associata ad ogni client, sulla quale il controller chiama i metodi che vengono poi inoltrati al client fisico tramite rete.

I messaggi sono stati suddivisi in: Request (server -> client) e Response (client -> server).

- #### Disconnessione

È stato implementato un meccanismo di rilevamento delle disconnessioni da parte del client per errori di rete o per chiusura dell'applicativo.

Le disconnessioni causate da chiusura volontaria del client sono rilevate dal server attraverso la gestione delle eccezioni di rete (SocketException).

Le disconnessioni causate da errori sono rilevate dal server attraverso ping mandato dal client, che notifica quindi agli altri client la disconnessione di un client. Inoltre, viene mandato anche un ping dal server al client in modo che il client possa accorgersi di eventuali problemi di rete.  

- ### Multithreading

Si è reso necessario l'uso del multithreading, per la gestione della funzionalità multi partita, e per gestire la comunicazione tra client e server. Nel server, è presente un thread per ogni client, per gestire la comunicazione. In particolare, il thread si occupa di ricevere tutti i dati provenienti dal client, i quali vengono poi inoltrati alle varie classi che gestiranno l'informazione. 

- ### Gods

Avendo comportamenti simili tra loro, si è deciso di implementare gli dei pubblico un pattern __strategy__ che per ogni azione predefinita (move, build, win) permettesse l'utilizzo di un determinato algoritmo o ne aggiungesse di nuove, sfruttando il polimorfismo di Java.

- ### GUI

Per la GUI è stato usato JavaFX, utilizzando SceneBuilder per la realizzazione delle scene per una resa grafica più ricca, una maggiore velocità di sviluppo e una separazione più netta tra view e controller lato client.

- ### Limitazioni

In fase di progettazione iniziale si sono delineate alcune funzionalità accessorie a bassa priorità da implementare soltanto in fase di perfezionamento:

- animazioni e arricchimento grafico dell'esperienza utente;

- cambio di modalità di connessione a partita in corso

<!--stackedit_data:

eyJoaXN0b3J5IjpbNTg2NDYwMTc3LC04NzE0MDQ3NTEsMjE1OD

g2ODIyLDExOTk2NjU1OTIsNTYzOTk5MzQzXX0=

-->


