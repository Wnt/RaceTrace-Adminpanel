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

import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.Customer;
import fi.racetrace.adminpanel.data.CustomerContainer;
import fi.racetrace.adminpanel.ui.columngenerator.ContractCountGC;
import fi.racetrace.adminpanel.ui.columngenerator.CustomerLinkGC;
import fi.racetrace.adminpanel.ui.columngenerator.DeviceCountGC;

public class CustomerTable extends EditableTable {
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "name",
			"activeContracts", "contractEnd", "deviceCount", "detailsLink" };
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Name",
			"Active contracts", "Last contract end", "Device count", "Details" };

	public CustomerTable(CustomerContainer customers) {
		super(customers);
		buildTable();
		buildButtons();

	}

	@Override
	protected void initTable() {
		table = new Table("Customers", dataSource);
		table.addGeneratedColumn("activeContracts", new ContractCountGC());
		table.addGeneratedColumn("contractEnd",
				new ContractEndColumnGenerator());
		table.addGeneratedColumn("deviceCount", new DeviceCountGC());
		table.addGeneratedColumn("detailsLink", new CustomerLinkGC());
	}

	@Override
	protected String[] getColHeaders() {
		return COL_HEADERS_ENGLISH;
	}

	@Override
	protected Object[] getNaturalColOrder() {
		return NATURAL_COL_ORDER;
	}

	@Override
	protected Customer getNewItem() {
		return new Customer();
	}

	@Override
	protected void saveData() {
		// TODO Auto-generated method stub

	}
}
