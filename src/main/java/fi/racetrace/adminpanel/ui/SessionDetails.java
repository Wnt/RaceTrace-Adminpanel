/*
 *  This file is part of RaceTrace-Adminpanel
 *  Copyright (C) 2012 Vaadin Oy
 *  Copyright (C) 2013 Jonni Nakari <jonni@egarden.fi>

 *  RaceTrace-Adminpanel is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.

 *  RaceTrace-Adminpanel is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.

 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fi.racetrace.adminpanel.ui;

import java.util.ArrayList;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import fi.racetrace.adminpanel.data.CustomerContainer;
import fi.racetrace.adminpanel.data.Session;
import fi.racetrace.adminpanel.data.SessionDevice;

public class SessionDetails extends Panel implements View {

	public static final String NAME = "sessions";
	private final CustomerContainer dataSource;
	private Session session;
	private final VerticalLayout layout = new VerticalLayout();

	public SessionDetails(CustomerContainer dataSource) {
		this.dataSource = dataSource;
		setContent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.session = null;

		int sessiontId = Integer.parseInt(event.getParameters());

		findSession(sessiontId);

		layout.removeAllComponents();

		if (this.session != null) {
			setTitle();
			buildlayout();
		} else {
			Notification.show("Session not found!",
					Notification.Type.WARNING_MESSAGE);
		}
	}

	private void buildlayout() {
		buildBreadcrumbs();
		buildDetailsForm();
		buildDeviceList();
		buildDeviceStatus();
	}

	private void buildDeviceStatus() {
		ArrayList<SessionDevice> devices = new ArrayList<SessionDevice>();
		for (SessionDevice sessionDevice : session.getSessionDevices()) {
			devices.add(sessionDevice);
		}
		layout.addComponent(new DeviceStatusTable(devices));

	}

	private void buildDeviceList() {
		layout.addComponent(new SessionDeviceTable(session));
	}

	private void buildDetailsForm() {
		layout.addComponent(new SessionForm(session));
	}

	private void buildBreadcrumbs() {
		HorizontalLayout b = new HorizontalLayout();
		layout.addComponent(b);

		b.addComponent(new Link("Customers", new ExternalResource("#!")));
		b.addComponent(new Label(" >> "));
		b.addComponent(new Link(session.getEvent().getCustomer().getName(),
				new ExternalResource("#!" + CustomerDetails.NAME + "/"
						+ session.getEvent().getCustomer().getId())));
		b.addComponent(new Label(" >> "));
		b.addComponent(new Link(session.getEvent().getName(),
				new ExternalResource("#!" + EventDetails.NAME + "/"
						+ session.getEvent().getId())));
		b.addComponent(new Label(" >> "));
		b.addComponent(new Link(session.getName(), new ExternalResource("#!"
				+ NAME + "/" + session.getId())));

	}

	private void setTitle() {
		Page.getCurrent().setTitle(
				session.getName() + " - Sessions - RaceTrace");
	}

	protected void findSession(int eventId) {
		for (Object iid : dataSource.getItemIds()) {
			for (fi.racetrace.adminpanel.data.Event customerEvent : dataSource
					.getItem(iid).getBean().getEvents()) {
				for (Session eventSession : customerEvent.getSessions()) {
					if (eventSession.getId() == eventId) {
						this.session = eventSession;
						return;
					}
				}
			}
		}
	}

}
