"use strict";

window.onload = init;

var socket = new WebSocket("ws://localhost:8080/Programmable_thermostat_WebSocket/actions");
socket.onmessage = onMessage;

function onMessage(event){
    console.log("message received !");
    var thermostat = JSON.parse(event.data);
    
    
    switch(thermostat.action){
        //Tout les inputs de Programmable_thermostat_input.java
        case "fan_switch_auto": fan_switch_auto();
            break;
        case "fan_switch_on" : fan_switch_on();
            break;
        case "f_c" : f_c();
            break;
        case "hold_temp" : hold_temp();
            break;
        case "run_program": run_program();
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

function run_program(){
    console.log("it's working !");
}

function runProgram(event){
    var run = {
        action : "run_program",
        description: "program should run"
    };
    socket.send(JSON.stringify(run));
}

function init(){
    console.log("app loaded");
    
    $('#run').on('click',runProgram);
}


