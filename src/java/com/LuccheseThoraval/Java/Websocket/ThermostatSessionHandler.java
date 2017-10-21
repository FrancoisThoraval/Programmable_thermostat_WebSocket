/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LuccheseThoraval.Java.Websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

/**
 *
 * @author Francois
 */
@ApplicationScoped
public class ThermostatSessionHandler {
    private final Set<Session> sessions = new HashSet<>();
    
    public void addSession(Session session){
        System.out.println("addSession");
        sessions.add(session);
    }
    
    public void removeSession (Session session){
        sessions.remove(session);
    }
    
    public void runProgram(){
        JsonObject addMessage = createAddMessage();
        System.out.println(addMessage);
        sendToAllConnectedSessions(addMessage);
    }
    
    private JsonObject createAddMessage(){
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "run_program")
                .add("description", "test")
                .build();
        
        return addMessage;
    }
    
    private void sendToAllConnectedSessions(JsonObject message){
        for (Session session : sessions){
            //System.out.println(session + " " + message);
            sendToSession(session, message);
        }
    }
    
    private void sendToSession(Session session, JsonObject message){
        try{
            session.getBasicRemote().sendText(message.toString());
            System.out.println(message.toString());
        } catch(IOException ex){
            System.out.println("error ?");
            sessions.remove(session);
            Logger.getLogger(ThermostatSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
