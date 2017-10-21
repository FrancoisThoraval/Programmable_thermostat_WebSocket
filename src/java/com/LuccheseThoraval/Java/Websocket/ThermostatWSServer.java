/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LuccheseThoraval.Java.Websocket;

import com.FranckBarbier.Java._Programmable_thermostat_MODEL._Programmable_thermostat.Programmable_thermostat;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Francois
 */
@ApplicationScoped
@ServerEndpoint("/actions")
public class ThermostatWSServer {
    
    @Inject
    private ThermostatSessionHandler sessionHandler;
    
    @OnOpen
    public void onOpen(Session session){
        sessionHandler.addSession(session);
    }
    
    @OnClose
    public void onClose(Session session){
        sessionHandler.removeSession(session);
    }
    
    @OnError
    public void onError(Throwable error){
        Logger.getLogger(ThermostatWSServer.class.getName()).log(Level.SEVERE,null,error);
    }
    
    @OnMessage
    public void messageHandler(String message, Session session){
        try(JsonReader reader = Json.createReader(new StringReader(message))){
            JsonObject jsonMessage = reader.readObject();
            if("run_program".equals(jsonMessage.getString("action"))){
                //run the run program function
                
                System.out.println("i run the program");
                sessionHandler.runProgram();
            } else {
            }
        }
    }
}
