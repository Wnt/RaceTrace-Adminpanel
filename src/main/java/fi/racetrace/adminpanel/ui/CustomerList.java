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
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import fi.racetrace.adminpanel.data.CustomerContainer;

public class CustomerList extends Panel implements View {

	public static final String NAME = "";
	private CustomerTable table = null;
	private VerticalLayout customerList = null;
	private final CustomerContainer dataSource;

	public CustomerList(CustomerContainer dataSource) {
		this.dataSource = dataSource;
	}

	private void buildCustomerLayout() {
		customerList = new VerticalLayout();

		buildBreadcrumbs();

		table = new CustomerTable(this.dataSource);
		customerList.addComponent(table);
	}

	private void buildBreadcrumbs() {

		HorizontalLayout b = new HorizontalLayout();
		customerList.addComponent(b);

		b.addComponent(new Link("Customers", new ExternalResource("#!")));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (customerList == null) {
			this.buildCustomerLayout();
		}
		setContent(customerList);
		setTitle();
	}

	private void setTitle() {
		Page.getCurrent().setTitle("Customers - RaceTrace");
	}

	public CustomerContainer getDataSource() {
		return this.dataSource;
	}

}
