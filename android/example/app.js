! function() {
	const TOPIC = "testtopic/1";
	const CLIENTID = "clientId-IomzIs79oS";
	var $ = Titanium.UI.createWindow({
		title : 'MQTT  Test',
		backgroundImage : '/karo.png'
	});
	$.statusView = Ti.UI.createView({
		top : 0,
		height : 20
	});
	$.add($.statusView);
	var button = Ti.UI.createButton({
		height : 80,
		width : '80%',
		title : 'Publish model name'
	});

	var MQTT = require("de.appwerft.mqtt");
	var mqttClient = MQTT.createMQTTClient();

	mqttClient.connect({
		clientId : CLIENTID,
		url : "tcp://broker.hivemq.com:1883",//"tcp://iot.eclipse.org:1883"
	});

	mqttClient.subscribe({
		topic : TOPIC,
		qos : mqttClient.QOS_AT_LEAST_ONCE,
		onload : function(_payload) {
			Ti.UI.createNotification(_payload).show();
		}
	});

	$.add(button);
	button.addEventListener('click', function() {
		mqttClient.publish({
			message : Ti.Platform.model + ' with ' + (Ti.Platform.availableMemory / 1000000).toFixed(6),
			topic : TOPIC,
			qos : mqttClient.QOS_AT_MOST_ONCE
		});
	});
	$.open();
	setInterval(function() {
		$.statusView.backgroundColor = mqttClient.isConnected() ? "green" : "red";
	}, 500);
}();
