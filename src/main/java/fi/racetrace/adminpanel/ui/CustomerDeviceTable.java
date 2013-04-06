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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.Customer;
import fi.racetrace.adminpanel.data.Device;

public class CustomerDeviceTable extends EditableTable {

	// TODO status - Vain sopimusten mukainen määrä devicejä voi olla enabled
	// tilassa samaan aikaan.
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "name",
			"imei", "phoneNumber" }; // , "status" };
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Name",
			"IMEI", "Phone number" }; // , "Status" };
	private final Customer customer;

	public CustomerDeviceTable(Customer customer) {
		super(
				new BeanItemContainer<Device>(Device.class,
						customer.getDevices()));
		this.customer = customer;
		buildTable();
		buildButtons();
	}

	@Override
	protected void initTable() {
		table = new Table("Devices", dataSource);
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
	protected Object getNewItem() {
		return new Device();
	}

	@Override
	protected void saveData() {
		ArrayList<Device> devices = new ArrayList<Device>(dataSource.size());
		for (Object iid : dataSource.getItemIds()) {
			devices.add((Device) dataSource.getItem(iid).getBean());
		}
		customer.setDevices(devices);
	}
}
