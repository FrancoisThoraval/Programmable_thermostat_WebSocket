/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LuccheseThoraval.Java.Websocket;

import com.FranckBarbier.Java._Programmable_thermostat_MODEL._Programmable_thermostat.Programmable_thermostat;
import com.FranckBarbier.Java._Temperature.*;   
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
    //private Temperature _temp = new Temperature(20.F,Temperature.Celsius);
    //private Programmable_thermostat _thermostat = new Programmable_thermostat(_temp);
    
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
            switch(jsonMessage.getString("action")){
                //TODO complete this when we actually know how to use Thermostat's functions !
                case "run_program": {
                    //run the Barbier's program function
                
                    System.out.println("run_program executed");
                    sessionHandler.run_program(message);
                }break;
                case "fan_switch_auto":{
                    System.out.println("fan_switch_auto executed");
                    sessionHandler.fan_switch_auto(message);
                }break;
            }
        }
    }
}
