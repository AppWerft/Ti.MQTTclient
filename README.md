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
var mod = require("de.appwerft.mqtt");
var conn = mod.createMQTTClient({
    url : "iot.eclipse.org:1883",
    id :"12345"
})


conn.disconnect();
~~~