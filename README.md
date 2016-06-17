Ti.MQTTclient
=============

This is a Titanium module for realizing  a mqtt client. It wrapps the [AndroidMQTTDemo](https://github.com/jeffprestes/AndroidMQTTDemo) from [Jeff Prestes](https://github.com/jeffprestes).

Enables an android application to communicate with an MQTT server using non-blocking methods.

Implementation of the MQTT asynchronous client interface {@link org.eclipse.paho.client.mqttv3.IMqttAsyncClient} , using the MQTT android service to actually interface with MQTT server. It provides android applications  simple programming interface to all features of the MQTT version 3.1


specification including:

* connect
* publish
* subscribe
* unsubscribe
* disconnect


~~~
var MQTT = require("de.appwerft.mqtt");
var mqttClient = MQTT.createMQTTClient({
    clientId : "Java_Test",
    url : "tcp://iot.eclipse.org:1883"
});

mqttClient.connect();
mqttClient.subscribe({
    qos : mqttClient.QOS_AT_LEAST_ONCE,
    topicFilter : "myfilter",
    onload : function(_e) {
        console.log(_e);
    }
});
mqttClient.publish({
    message : "test",
    topicFilter : "myfilter",
    qos : mqttClient.QOS_AT_LEAST_ONCE
});
~~~