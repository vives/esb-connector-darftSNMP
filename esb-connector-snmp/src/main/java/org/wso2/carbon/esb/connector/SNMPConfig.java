/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.esb.connector;

import org.apache.synapse.MessageContext;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.Connector;

import java.io.IOException;

/*
 * Class for initiate the SNMP Connector
 */
public class SNMPConfig extends AbstractConnector implements Connector {

	/*
     * Initiate the connection
     *
     * @param messageContext the message context
	 */
	@Override
	public void connect(MessageContext messageContext) throws ConnectException {
		try {
			start(messageContext);
		} catch (IOException e) {
			handleException("Error while initiating the snmp " + e.getMessage(), e, messageContext);
		}
	}

	/**
	 * Start the Snmp session. If you communication or get the listen() method you will not get any
	 * answers because
	 * the  is asynchronous and the listen() method listens for answers.
	 *
	 * @param messageContext the message context
	 */
	private void start(MessageContext messageContext) throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		// Create Snmp object for sending data to Agent
		Snmp snmp = new Snmp(transport);
		messageContext.setProperty(SNMPConstants.SNMP, snmp);
		transport.listen();
	}
}