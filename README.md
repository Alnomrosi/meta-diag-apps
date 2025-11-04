# META-DIAG-APPS

## Introduction
- Creating custom diagnostics apps that exposed to sovd server.
- diag  app is an application running in the background that send request to change/inquire/delete recources.
- diag app is application act a middleware between SOVD and the actual application
- diag app is a microservices written in python with RestAPI implementation

## diag_app
- Simple application for SOVD Test act as a layer between the actual applications and the Public SOVD server (a private SOVD server).
- diag application can retrive the available data by the SOVD server. 

    SOVD Client 
        |
        V
    SOVD Server
        |
        V
  diag_app (Private SOVD)        <---- This is the current Project
        |
        V
   Applications
        |
        V
   User Interface


## Source Code Repository
Private apps diagnostics server source code is available at:  
[Diag-Microservice](https://github.com/Alnomrosi/Simple-Apps-Diag-Microservice)



