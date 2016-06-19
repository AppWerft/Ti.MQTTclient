/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package de.appwerft.mqtt;

import java.net.URI;
import java.net.URISyntaxException;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.TiProperties;
import org.appcelerator.titanium.util.TiConvert;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Kroll.proxy(creatableInModule = MqttModule.class)
public class MQTTClientProxy extends KrollProxy {
	// Standard Debugging variables
	private static final String LCAT = "MQTT";

	protected long timeToWait = -1;
	private URI serverUri = null;
	private static final String SANDBOX = "tcp://broker.hivemq.com:1883";
	private String clientId = "Java_Test";
	private MqttClient aClient;
	MemoryPersistence persistence = new MemoryPersistence();
	MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

	private KrollFunction onloadCallback = null;

	// https://eclipse.org/paho/clients/java/
	public MQTTClientProxy() {
		super();
	}

	private void readOptions(KrollDict options) {
		final TiProperties appProperties;
		appProperties = TiApplication.getInstance().getAppProperties();
		String uriString = appProperties.getString("mqtt_broker", "");

		if (uriString.equals("")) {
			uriString = SANDBOX;
		}
		try {
			serverUri = new URI(TiConvert.toString(uriString));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		if (options.containsKeyAndNotNull("url")) {
			try {
				serverUri = new URI(TiConvert.toString(options
						.get(TiC.PROPERTY_URL)));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		if (options.containsKeyAndNotNull("clientId")) {
			clientId = options.getString("clientId");
		}
		if (serverUri == null)
			try {
				serverUri = new URI(SANDBOX);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void handleCreationDict(KrollDict options) {
		readOptions(options);
		super.handleCreationDict(options);
	}

	@Kroll.method
	public void subscribe(final KrollDict args) throws MqttException {
		/* implementation of "callback" */
		IMqttMessageListener messageListener = new IMqttMessageListener() {
			@Override
			public void messageArrived(String topic, MqttMessage message)
					throws Exception {
				Object onload;
				KrollDict payload = new KrollDict();
				Log.d(LCAT, " message from broker received");
				payload.put("message", message.toString());
				if (args.containsKeyAndNotNull("onload")) {
					onload = args.get("onload");
					if (onload instanceof KrollFunction) {
						onloadCallback = (KrollFunction) onload;
					}
					onloadCallback.call(getKrollObject(), payload);
				}

				Log.d("LCAT",
						"Message: " + topic + " : "
								+ new String(message.getPayload()));
			}
		};
		if (args.containsKeyAndNotNull("topics")) {
			aClient.subscribe(args.getStringArray("topics"), new int[] { 1 },
					new IMqttMessageListener[] { messageListener });
		}
		if (args.containsKeyAndNotNull("topic")) {
			aClient.subscribe(new String[] { args.getString("topic") },
					new int[] { 1 },
					new IMqttMessageListener[] { messageListener });
		}
	}

	@Kroll.method
	public void unsubscribe(KrollDict args) throws MqttException {
		if (args.containsKeyAndNotNull("topic")) {
			String topicFilter = args.getString("topic");
			aClient.unsubscribe(new String[] { topicFilter });
		}
		if (args.containsKeyAndNotNull("topics")) {
			String[] topicFilters = args.getStringArray("topics");
			aClient.unsubscribe(topicFilters);
		}

	}

	@Kroll.method
	public void publish(KrollDict args) {
		String topic = "DEFAULTTOPIC", content = "DEFAULTMESSAGE";
		int qos = 2;
		boolean retained = false;
		if (args.containsKeyAndNotNull("topic")) {
			topic = args.getString("topic");
		}
		if (args.containsKeyAndNotNull("message")) {
			content = args.getString("message");
		}
		if (args.containsKeyAndNotNull("qos")) {
			qos = args.getInt("qos");
		}
		if (args.containsKeyAndNotNull("retained")) {
			retained = args.getBoolean("retained");
		}
		MqttMessage message = new MqttMessage(content.getBytes());
		message.setQos(qos);
		message.setRetained(retained);
		Log.d(LCAT, "Try to publish message");
		try {
			aClient.publish(topic, message);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Kroll.method
	public void connect(@Kroll.argument(optional = true) KrollDict args) {
		mqttConnectOptions.setAutomaticReconnect(true);
		if (args != null && !args.isEmpty()) {
			if (args.containsKeyAndNotNull("cleanSession")) {
				mqttConnectOptions.setCleanSession(args
						.getBoolean("cleanSession"));
			}
			if (args.containsKeyAndNotNull("automaticReconnect")) {
				mqttConnectOptions.setAutomaticReconnect(args
						.getBoolean("automaticReconnect"));
			}
			if (args.containsKeyAndNotNull("connectionTimeout")) {
				mqttConnectOptions.setConnectionTimeout(args
						.getInt("connectionTimeout"));
			}
			if (args.containsKeyAndNotNull("keepAliveInterval")) {
				mqttConnectOptions.setKeepAliveInterval(args
						.getInt("keepAliveInterval"));
			}
			if (args.containsKeyAndNotNull("userName")) {
				mqttConnectOptions.setUserName(args.getString("userName")); // setUserName();
			}
			if (args.containsKeyAndNotNull("password")) {
				mqttConnectOptions.setPassword(args.getString("password")
						.toCharArray());
			}

		}
		try {
			aClient = new MqttClient(serverUri.toString(), clientId,
					persistence);
			aClient.connect(mqttConnectOptions);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Kroll.method
	public void close() throws MqttException {
		aClient.close();
	}

	@Kroll.method
	public String getClientId() {
		return aClient.getClientId();
	}

	@Kroll.method
	public String getServerURI() {
		return aClient.getServerURI().toString();
	}

	@Kroll.method
	public String getCurrentServerURI() {
		return aClient.getCurrentServerURI().toString();
	}

	@Kroll.method
	public String getTopic(String topic) {
		return aClient.getTopic(topic).toString();
	}

	@Kroll.method
	public void disconnect() {
		if (aClient != null)
			try {
				aClient.disconnect();
			} catch (MqttException e) {
				e.printStackTrace();
			}
	}

	@Kroll.method
	public boolean isConnected() {
		return aClient.isConnected();
	}

	@Kroll.method
	public void reconnect() throws MqttException {
		if (aClient != null)
			try {
				aClient.reconnect();
			} catch (MqttException e) {
				e.printStackTrace();
			}
	}

	@Kroll.method
	public static String generateClientId() {
		return MqttAsyncClient.generateClientId();
	}

	public long getTimeToWait() {
		return this.timeToWait;
	}
}