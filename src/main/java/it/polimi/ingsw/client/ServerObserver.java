package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

public interface ServerObserver {

    Object update(Message receivedMessage);

}

