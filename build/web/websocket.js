"use strict";

let Window_loaded = null;
Object.defineProperty(window, "Window_loaded", {value: new Promise(g => {
    Window_loaded = main;
}), enumerable: false, configurable: false, writable: false});
window.addEventListener('load', Window_loaded);

var socket = new WebSocket("ws://localhost:8080/Programmable_thermostat_WebSocket/actions");
socket.onmessage = onMessage;


function onMessage(event){
    console.log("message received !");
    var thermostat = JSON.parse(event.data);
    
    
    switch(thermostat.action){
        //Connexion
        case "connexion_created": console.log(thermostat.description);
            break;
        
        //Tout les inputs de Programmable_thermostat_input.java
        case "fan_switch_auto": fan_switch_auto(thermostat.description);
            break;
        case "fan_switch_on" : fan_switch_on();
            break;
        case "f_c" : f_c(thermostat.unit,thermostat.temp,thermostat.description);
            break;
        case "hold_temp" : hold_temp();
            break;
        case "run_program": run_program(thermostat.description);
            break;
        case "season_switch_cool" : season_switch_cool();
            break;
        case "season_switch_heat" : season_switch_heat();
            break;
        case "season_switch_off" : season_switch_off();
            break;
        case "set_clock": set_clock();
            break;
        case "set_day": set_day();
            break;
        case "temp_down": temp_down();
            break;
        case "temp_up": temp_up();
            break;
        case "time_backward": time_backward();
            break;
        case "time_forward": time_forward();
            break;
        case "view_program": view_program();
            break;
        //------------------------------------------------------
        //Tout les output de Programmable_thermostat_output.java    
        case "display_ambient_temperature":display_ambient_temperature();
            break;
        case "display_current_date_and_time": display_current_date_and_time();
            break;
        case "display_period_and_program_time": display_period_and_program_time();
            break;
        case "display_program_target_temperature": display_program_target_temperature();
            break;
        case "display_target_temperature":display_target_temperature();
            break;
        case "update_run_indicator_status":update_run_indicator_status();
            break;
    }
}

function run_program(description){
    console.log("Description: " + description);
}

function fan_switch_auto(description){
    console.log("Description: " + description);
}

function f_c(unit,temp,description){
    $('#actualTemp').html("Actual temp: " + temp + "°" + unit);
    console.log("Temp: " + temp + "°" + unit);
    console.log("Description: " + description);
}

function main(){
    console.log("app loaded");
    
    //Clic sur le bouton "run program"
    $('#run_program').on('click',function(event){
        var message = {
            action : "run_program",
            description: "program should run"
        };
        socket.send(JSON.stringify(message));
    });
    
    $('#fan_switch_auto').on('change',function(event){
        console.log("clicked on fan switch");
        var message = {
            action : "fan_switch_auto",
            description : "Switch fan on automatic mode"
        };
        socket.send(JSON.stringify(message));
    });
    
    $('#tempOptions').on('click',function(event){
        if($('#optionC').is(":checked")){
            $('#optionF').prop("checked", false);
            console.log("checked temp in C");
            var message = {
                action : "f_c",
                unit : "C",
                description : "Change unit in Celsius"
            };
        }else if ($('#optionF').is(":checked")){
            $('#optionC').prop("checked", false);
            console.log("checked temp in F");
            var message = {
                action : "f_c",
                unit : "F",
                description : "Change unit in Fahrenheit"
            };
        }else if ($('#optionHoldTemp').is(":checked")){
            console.log("checked hold temp");
        }
        
        socket.send(JSON.stringify(message));
    });
}


