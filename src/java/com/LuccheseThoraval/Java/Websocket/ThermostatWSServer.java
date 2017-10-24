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
import javax.json.spi.JsonProvider;
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
    private static Temperature _initTemp;
    private Temperature _temp;
    private Programmable_thermostat _thermostat;
    
    static{
        try{
            _initTemp = new Temperature(21.F,Temperature.Celsius,1.F); //value,unit,step
            
        }catch(Exception e){
            System.err.println(e);
        }
        
    }
    
    private void initThermostat(){
        _temp = (Temperature) _initTemp.clone();
        try{
            _thermostat = new Programmable_thermostat(_temp);
            System.out.println("Thermostat initialized !");
            //_thermostat.temp_up();
            
            //System.out.println(_thermostat.getAmbientTemperature().asCelsius() + "°C");
        }catch(Exception e){
            System.err.println(e);
        }
        
        
    }
    
    @Inject
    private ThermostatSessionHandler sessionHandler;
    
    @OnOpen
    public void onOpen(Session session){
        sessionHandler.addSession(session);
        initThermostat();
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
                
                case "f_c":{
                    System.out.println("f_c executed");
                    JsonProvider provider = JsonProvider.provider();
                    JsonObject updateMessage;
                    System.out.println(jsonMessage.getString("unit"));
                    if(jsonMessage.getString("unit").equals("C")){
                        updateMessage = provider.createObjectBuilder()
                            .add("action", "f_c")
                            .add("unit", jsonMessage.getString("unit"))
                            .add("temp", _thermostat.getAmbientTemperature().asCelsius())
                            .add("description", jsonMessage.getString("description"))
                            .build();
                    }else{
                        updateMessage = provider.createObjectBuilder()
                            .add("action", "f_c")
                            .add("unit", jsonMessage.getString("unit"))
                            .add("temp", _thermostat.getAmbientTemperature().asFahrenheit())
                            .add("description", jsonMessage.getString("description"))
                            .build();
                    }
                    

                    sessionHandler.f_c(updateMessage);
                }
            }
        }
    }
}
