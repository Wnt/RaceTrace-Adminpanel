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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.Contract;
import fi.racetrace.adminpanel.data.Customer;

public class CustomerContractTable extends EditableTable {
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "name",
			"start", "end", "deviceCount" };
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Name",
			"Start", "End", "Device count" };
	private final Customer customer;

	public CustomerContractTable(Customer customer) {
		super(new BeanItemContainer<Contract>(Contract.class,
				customer.getContracts()));
		this.customer = customer;
		buildTable();
		buildButtons();
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
	protected void initTable() {
		table = new Table("Contracts", dataSource) {
			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property<?> property) {
				if (property.getType() == Date.class
						&& property.getValue() != null) {
					SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
					return df.format((Date) property.getValue());
				}
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
	}

	@Override
	protected Contract getNewItem() {
		return new Contract();
	}

	@Override
	protected void saveData() {
		ArrayList<Contract> contracts = new ArrayList<Contract>(
				dataSource.size());
		for (Object iid : dataSource.getItemIds()) {
			contracts.add((Contract) dataSource.getItem(iid).getBean());
		}
		System.out.println("saving contracts: " + contracts);
		customer.setContracts(contracts);
	}

}
