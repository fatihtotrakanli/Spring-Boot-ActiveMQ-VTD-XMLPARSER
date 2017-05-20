## Spring Boot - ActiveMQ - VTD-XML PARSER

Its sample project with Spring Boot uses ActiveMQ and VTD-XML Parser

##### Easy Way to install Apache ActiveMQ
```
brew install activemq
```
then if install is successfully
```
activemq start
```
you can see service started with this command.
```
brew services list
```

##### ActiveMQ Configuration
In application.properties, you can change 'activemq configuration' for your configuration.

ActiveMQ runs at
```
http://localhost:8161
```
and
```
user: admin
pass: admin
```

you can see your queue at ActiveMQ portal.

##### How To Run Project

The project is a spring boot project. For this reason, you can run the Application class by running it.

Application starts normally and inserts two XML file in queue then JMS listener take them and parses with VTD.


** In the initial stage of the project, it will continue to be developed.

## License

The MIT License (MIT) Copyright (c) 2017 Fatih Totrakanlı