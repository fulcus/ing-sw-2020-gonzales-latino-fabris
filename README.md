# Software Engineering Final Project


## How to run the game ##

You need to have JDK 14 installed. It probably works with previous versions up to JDK 8.

You can download the [server](https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/raw/master/deliveries/final/jar/santorini-server.jar) and [client](https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/raw/master/deliveries/final/jar/santorini-client.jar) jars or clone the repo and compile it yourself.

### Server

`cd` into the folder containing the jar and run it with the command `java -jar santorini-server.jar`


### Client

For each client: cd into the folder containing the jar and run it with the command `java -jar santorini-client.jar`

  

-------------------------------------------------- ----

## Implemented features ##

- Complete rules

- Socket connection

- CLI

- GUI


  ### Advanced features

  - Advanced gods

  - Multiple games

 
-------------------------------------------------- ----

## UML ##

- In order to maximize the usability and readability of the UML class diagrams, we decided to create a general synthetic diagram that shows only the most representative  relations and attributes. Moreover we created a diagram for each main package with the IntelliJ autogeneration tool. 


- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/general.pdf"> General diagram </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20model.png"> Model </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20serializable.png"> Controller </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20server.png"> Server </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20client.png"> Client </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20gui.png"> Gui </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20cli.png"> Cli </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20serializable.png"> Serializable </a>

- <a href="https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/uml/Package%20god.png"> God </a>
  

-------------------------------------------------- ----

## Documentation ##


We redacted the [documentation](https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/tree/master/deliveries/final/javadoc) of the code using javadoc. You can browse it cloning the repo and opening `index.html`.

[Here](https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/blob/master/deliveries/final/rules/santorini_rules.pdf) you can find the rules of the game.

-------------------------------------------------- ----

## Test Coverage ##


Model and controller tests have been implemented trying to obtain almost complete code coverage.
You can find it [here](https://github.com/fulcus/ing-sw-2020-gonzales-latino-fabris/tree/master/deliveries/final/report).

-------------------------------------------------- ----


## Implementation choices ##

- ### MVC

  The implementation of the chosen MVC pattern is the "hybrid" one in which  input controls are also done on the client side to minimize network usage and server load. The controller takes care of managing turns, updating the model and managing events, requests and responses with the Client. On the server there is a Virtual View , on which the Controller calls methods and submits game requests. Virtual View will forward the methods to the client using the output stream.


  - #### Model

    It represent the status of the game. In particular, there is the information of the single Game, the Players, the Board and all the related information regarding workers and buildings.

 
  - #### View

    Provides the user with a representation of the model by saving and updating locally a partial copy of the state of the game. In particular a copy of the Board is saved with all the related information.


  - #### Controller

  It has the application logic and makes changes to the model, by using user inputs.


- ### Observers

  An __observer__ pattern has been used to implement the notification mechanism. In particular, the model and the view represent the observed and the observer respectively. Each cell in the game is observed, and as soon as there is a change in the state of that cell, clients are notified.


  Given the presence of the network, it was necessary to duplicate the observer pattern also on the client. In fact, the client class receives an update from the Network Handler class.

- ### Network

  We decided to design the communication between server and client in order to make communication independent from the type of graphical interface chosen by the user. Therefore, the server always sends the same messages regardless of whether the client has chosen to play with CLI or GUI. Furthermore, two types of information are shared: those relating to the game and those useful for monitoring and managing the connection status of clients (PING).
  

  Inside the server, there is a class that represents the virtual view associated with each client, on which the controller calls the methods which are then forwarded to the physical clients through the network.

  Messages have been divided into: Request (server -> client) and Response (client -> server).


  - #### Disconnection  

    A mechanism has been implemented for detecting disconnections of the client due to network errors or application closing.

    Disconnections caused by voluntary closure of the client are detected by the server through the management of network exceptions (SocketException).


------------------------------------------------------

## Authors ##


##### Francesco Gonzales ([fulcus](https://github.com/fulcus))

##### Alberto Latino ([albertolatino](https://github.com/albertolatino))

##### Vittorio Fabris ([VittoFab](https://github.com/VittoFab))
