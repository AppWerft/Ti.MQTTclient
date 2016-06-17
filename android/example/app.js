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
