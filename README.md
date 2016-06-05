Ti.MQTTclient
=============

This is a Titanium module for realizing  a mqtt client. It wrapps the [AndroidMQTTDemo](https://github.com/jeffprestes/AndroidMQTTDemo) from [Jeff Prestes](https://github.com/jeffprestes).


~~~
var mod = require("de.appwerft.mqtt");
var conn = mod.createMQTTClient({
    url : "iot.eclipse.org:1883"
})


conn.disconnect();
~~~