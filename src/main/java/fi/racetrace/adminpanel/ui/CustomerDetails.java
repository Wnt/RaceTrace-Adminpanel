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

import com.vaadin.data.util.BeanItem;
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

import fi.racetrace.adminpanel.data.Customer;
import fi.racetrace.adminpanel.data.CustomerContainer;

public class CustomerDetails extends Panel implements View {

	public static final String NAME = "customers";
	private final CustomerContainer dataSource;
	private BeanItem<Customer> customer = null;
	private final VerticalLayout layout = new VerticalLayout();

	public CustomerDetails(CustomerContainer dataSource) {
		this.dataSource = dataSource;
		setContent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		customer = null;

		int idParam = Integer.parseInt(event.getParameters());

		findCustomer(idParam);

		layout.removeAllComponents();

		if (customer != null) {
			setTitle();
			buildlayout();
		} else {
			Notification.show("Customer not found!",
					Notification.Type.WARNING_MESSAGE);
		}

	}

	protected void findCustomer(int idParam) {
		for (Customer iid : dataSource.getItemIds()) {
			Customer customer = dataSource.getItem(iid).getBean();
			if (customer.getId() == idParam) {
				this.customer = dataSource.getItem(customer);
				return;
			}
		}
	}

	private void setTitle() {
		Page.getCurrent().setTitle(
				customer.getItemProperty("name").getValue()
						+ " - Customers - RaceTrace");
	}

	private void buildlayout() {
		buildBreadcrumbs();
		buildCustomerForm();
		buildContractsList();
		buildDeviceList();
		buildEventsList();
	}

	private void buildDeviceList() {
		layout.addComponent(new CustomerDeviceTable(customer.getBean()));
	}

	private void buildBreadcrumbs() {
		HorizontalLayout b = new HorizontalLayout();
		layout.addComponent(b);

		b.addComponent(new Link("Customers", new ExternalResource("#!")));
		b.addComponent(new Label(" >> "));
		b.addComponent(new Link((String) customer.getItemProperty("name")
				.getValue(), new ExternalResource("#!" + CustomerDetails.NAME
				+ "/" + customer.getItemProperty("id").getValue())));
	}

	private void buildEventsList() {
		layout.addComponent(new CustomerEventTable(customer.getBean()));
	}

	private void buildContractsList() {
		layout.addComponent(new CustomerContractTable(customer.getBean()));
	}

	private void buildCustomerForm() {
		layout.addComponent(new CustomerForm(customer));
	}
}
