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

public class EventDetails extends Panel implements View {

	public static final String NAME = "events";
	private final CustomerContainer dataSource;
	private fi.racetrace.adminpanel.data.Event event = null;
	private final VerticalLayout layout = new VerticalLayout();

	public EventDetails(CustomerContainer dataSource) {
		this.dataSource = dataSource;
		setContent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.event = null;

		int eventId = Integer.parseInt(event.getParameters());

		findEvent(eventId);

		layout.removeAllComponents();

		if (this.event != null) {
			setTitle();
			buildlayout();
		} else {
			Notification.show("Event not found!",
					Notification.Type.WARNING_MESSAGE);
		}
	}

	protected void findEvent(int eventId) {
		for (Object iid : dataSource.getItemIds()) {
			for (fi.racetrace.adminpanel.data.Event customerEvent : dataSource
					.getItem(iid).getBean().getEvents()) {
				if (customerEvent.getId() == eventId) {
					this.event = customerEvent;
					return;
				}
			}
		}
	}

	private void buildlayout() {
		buildBreadcrumbs();
		buildCustomerForm();
		buildSessionList();
	}

	private void buildSessionList() {
		layout.addComponent(new SessionTable(event));
	}

	private void buildCustomerForm() {
		layout.addComponent(new EventForm(event));

	}

	private void buildBreadcrumbs() {
		HorizontalLayout b = new HorizontalLayout();
		layout.addComponent(b);

		b.addComponent(new Link("Customers", new ExternalResource("#!")));
		b.addComponent(new Label(" >> "));
		b.addComponent(new Link(event.getCustomer().getName(),
				new ExternalResource("#!" + CustomerDetails.NAME + "/"
						+ event.getCustomer().getId())));
		b.addComponent(new Label(" >> "));
		b.addComponent(new Link(event.getName(), new ExternalResource("#!"
				+ NAME + "/" + event.getId())));

	}

	private void setTitle() {
		Page.getCurrent().setTitle(event.getName() + " - Events - RaceTrace");
	}

}
