# NATS Practice

This is a Spring Boot-based application for a technical challenge.
The main functionalities are as follows:
* Pull twitter data through Twitter4j APIs
* Set up NATS client 
* Subscribe specified subject(s) and read messages from NATS
* Publish message to NATS via NATS client
* Store the messages received by subscriber to PosgreSQL database

Docker image can be built by running Docker Build command with the Dockerfile

### Reference Documentation

* [Twitter4j](http://twitter4j.org/)
* [Twitter4j Java Doc](http://twitter4j.org/javadoc/)
* [Java Client for NATS](https://github.com/nats-io/nats.java)
* [Publish and Receive Messages with NATS Java Client](https://www.baeldung.com/nats-java-client)
* [Minikube](https://minikube.sigs.k8s.io/docs/start/)
* [Deploy Spring Boot App on Kubernetes](https://gorillalogic.com/blog/build-and-deploy-a-spring-boot-app-on-kubernetes-minikube/)
