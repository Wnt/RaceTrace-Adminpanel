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
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import fi.racetrace.adminpanel.data.Customer;
import fi.racetrace.adminpanel.data.Session;

public class CustomerEventTable extends EditableTable {
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "name",
			"start", "end", "sessionCount", "detailsLink" };
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Name",
			"Start", "End", "Session count", "Details" };
	private final Customer customer;

	public CustomerEventTable(Customer customer) {
		super(new BeanItemContainer<fi.racetrace.adminpanel.data.Event>(
				fi.racetrace.adminpanel.data.Event.class, customer.getEvents()));
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
		table = new Table("Events", dataSource) {
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
		table.addGeneratedColumn("detailsLink", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {

				Link lnk = new Link("Show details",
						new ExternalResource("#!"
								+ EventDetails.NAME
								+ "/"
								+ ((fi.racetrace.adminpanel.data.Event) itemId)
										.getId()));
				return lnk;
			}
		});
		table.addGeneratedColumn("sessionCount", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				List<Session> sessions = ((fi.racetrace.adminpanel.data.Event) itemId)
						.getSessions();
				int sessionCount = 0;
				if (sessions != null) {
					sessionCount = sessions.size();
				}
				Label label = new Label("" + sessionCount);
				return label;
			}
		});
	}

	@Override
	protected fi.racetrace.adminpanel.data.Event getNewItem() {
		return new fi.racetrace.adminpanel.data.Event();
	}

	@Override
	protected void saveData() {
		ArrayList<fi.racetrace.adminpanel.data.Event> events = new ArrayList<fi.racetrace.adminpanel.data.Event>(
				dataSource.size());
		for (Object iid : dataSource.getItemIds()) {
			events.add((fi.racetrace.adminpanel.data.Event) dataSource.getItem(
					iid).getBean());
		}
		customer.setEvents(events);
	}

}
