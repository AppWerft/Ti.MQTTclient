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


```javascript
! function() {
    var $ = Titanium.UI.createWindow({
        title : 'MQTT  Test',
        backgroundImage : '/karo.png'
    });
    var button = Ti.UI.createButton({
        height : 80,
        width : '80%',
        title : 'Send model name'
    });

    var MQTT = require("de.appwerft.mqtt");
    var mqttClient = MQTT.createMQTTClient();

    mqttClient.connect({
        clientId : "Java_Test",
        url : "tcp://iot.eclipse.org:1883"
    });

    setTimeout(function() {
        mqttClient.subscribe({
            topic : "info",
            qos : mqttClient.QOS_AT_LEAST_ONCE,
            onload : function(_payload) {
                Ti.UI.createNotification(_payload).show();
            }
    });

    }, 100);
    $.add(button);
    button.addEventListener('click', function() {
        mqttClient.publish({
            message : Ti.Platform.model + ' with ' + Ti.Platform.availableMemory,
            topic : "info",
            qos : mqttClient.QOS_AT_LEAST_ONCE
        });
    });
    $.open();
}();
```

Methods
-------

createMQTTClient()

subscribe();

unsubscribe;

publish();

connect();

close();

getClientId();

getServerURI();

disconnect();

isConnected()

reconnect();