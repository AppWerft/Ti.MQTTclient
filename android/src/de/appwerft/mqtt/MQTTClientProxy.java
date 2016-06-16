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
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.util.TiConvert;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

// This proxy can be created by calling Mqqt.createExample({message: "hello world"})
@Kroll.proxy(creatableInModule = MqttModule.class)
public class MQTTClientProxy extends KrollProxy {
	// Standard Debugging variables
	private static final String LCAT = "MQTT";

	public URI serverUri = null;
	private static final String SANDBOX = "tcp://iot.eclipse.org:1883";
	public String clientId;
	private MqttClient aClient;

	// https://eclipse.org/paho/clients/java/
	public MQTTClientProxy() {
		super();

	}

	@Override
	public void handleCreationDict(KrollDict options) {
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
		super.handleCreationDict(options);
	}

	@Kroll.method
	public void connect() {
		try {
			aClient = new MqttClient(serverUri.toString(), clientId);
			Log.d(LCAT, "new MqttClient created " + serverUri.toString());
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

}