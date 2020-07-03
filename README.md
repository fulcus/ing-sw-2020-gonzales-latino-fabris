# Final Project - Software Engineering

  

  

  

##### Francesco Gonzales ([fulcus] (https://github.com/fulcus))

##### Alberto Latino ([albertolatino] (https://github.com/albertolatino))

##### Vittorio Fabris ([VittoFab] (https://github.com/VittoFab))
  

-------------------------------------------------- ----

  

  

## How to run the game ##

  Server and Client jars are available here:  LINK TO     INSERT


### Server


Go to the folder where the jar is located and from the terminal use the command `java -jar server.jar`

  

### Client


For each client: go to the folder where the jar is located and from the terminal and use the command `java -jar client.jar`

  

-------------------------------------------------- ----


## 1. Test Coverage ##


- Model and controller tests have been implemented trying to obtain almost complete code coverage.

 
-------------------------------------------------- ----

## 2. UML ##

- In order to maximize the usability and readability of the UML class diagrams, we decided to create a general synthetic diagram that shows only the most representative  relations and attributes. Moreover we created a diagram for each main package with the IntelliJ autogeneration tool. 



- <a href="#"> Model class diagram </a>

- <a href="#"> View class diagram </a>

- <a href="#"> GoCards class diagram </a>

- <a href="#"> Controller class diagram </a>


  

-------------------------------------------------- ----

## 3. Implemented features ##



- Complete rules

- Socket connection

- CLI

- GUI


- ### Advanced features

- Advanced gods

- Multiple games
  

-------------------------------------------------- ----

  

## 4. Implementation choices ##


- ### MVC


The implementation of the chosen MVC pattern is the "hybrid" one in which some, that is, a part of the input controls areis also done on the client side to minimize network usage and server load. The controller takes care of managing turnshifts, updating the model and managing events, requests and responses with the Client. IOn the server there is a Virtual View is implemented, on which the Controller calls methods to update it and submits game requests. In turn, Virtual View will forwards the methods to the client using the output stream.


- #### Model

  

  

It representhas the status of the application. In particular, there are datais the information of the single Game, the Players, the Board and all the related information regarding workers and constructions.

  

  

- #### View

  

  

Provides the user with a representation of the model b. By saving and updating locally a partial copy of the state of the game. I, in particular a copy of the Board is saved with all the related information.

  

  

- #### Controller

  

  

It has the application logic and displays user inputs from the view to makes changes ton the model, by using user inputs. .

  

  

! [MVC] (dMVC.png)

  

  

- ### Observers

  

  

An __observer__ pattern hwas been used to implement the notification mechanism. In particular, the model and the views represent the observed and the observer respectively. Each cell in the game is observed, and as soon as there is a change in the state of that cell, clients are notified.

  

  

Given the presence of the network, it was necessary to duplicate the observer pattern also on the client. In fact, the client class receives an update from the Network Handler class.

  

  

- ### Network

  

  

WeIt was decided to design the communication between server and client in order to make communication independent from the type of graphical interface chosen by the user. Therefore, the server server therefore always sends the same messages regardless of whether the client has chosen to play withvia CLI or GUI. Furthermore, two types of information are sharexchanged: those relating to the game and those useful for monitoring and managing the connection status of clients (PING).

  

  

Inside the server, there is a class that represents the virtual view associated with each client, on which the controller calls the methods which are then forwarded to the physical client throughvia the network.

  

  

The messages have been divided into: Request (server -> client) and Response (client -> server).

  

  

- #### Disconnection

  

  

A mechanism has been implemented for detecting disconnections by the client due tofor network errors or for closing the application.

  

  

Disconnections caused by voluntary closure of the client are detected by the server through the management of network exceptions (SocketException).

  

  

Disconnections approx