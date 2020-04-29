package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

public interface ServerObserver {

    Object update(Message receivedMessage);

    Object setMyTurn(Message turnStatus);

    //TODO add other methods that will be called on the client by the networkHandler
}

