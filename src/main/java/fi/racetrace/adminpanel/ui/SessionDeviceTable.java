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
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.Device;
import fi.racetrace.adminpanel.data.DeviceIcon;
import fi.racetrace.adminpanel.data.Session;
import fi.racetrace.adminpanel.data.SessionDevice;
import fi.racetrace.adminpanel.ui.columngenerator.DeviceIconGC;
import fi.racetrace.adminpanel.ui.columngenerator.TrailLengthGC;

public class SessionDeviceTable extends EditableTable {
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "device",
			"name", "color", "iconImage", "trailLengthSlider" };
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Device",
			"Name", "Color", "Icon", "Trail (seconds)" };
	private final Session session;

	public SessionDeviceTable(Session session) {
		super(new BeanItemContainer<SessionDevice>(SessionDevice.class,
				session.getSessionDevices()));
		this.session = session;
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
		table = new Table("Devices", dataSource);

		List<DeviceIcon> customerIcons = session.getEvent().getCustomer()
				.getIcons();
		DeviceIconGC deviceIconGenerator = new DeviceIconGC(customerIcons);

		table.addGeneratedColumn("iconImage", deviceIconGenerator);
		table.addGeneratedColumn("trailLengthSlider", new TrailLengthGC());
	}

	@Override
	protected EditOneFieldFactory getFieldFactory() {
		return new EditOneFieldFactory() {

			@Override
			public Field<?> createField(Container container, Object itemId,
					Object propertyId, Component uiContext) {

				Field field = null;
				if (itemId.equals(editableItemId)) {
					if ("device".equals(propertyId)) {
						BeanItemContainer<Device> customerDevices = new BeanItemContainer<Device>(
								Device.class, session.getEvent().getCustomer()
										.getDevices());
						ComboBox cb = new ComboBox("Device", customerDevices);
						cb.setWidth(100, Unit.PIXELS);
						field = cb;
					} else {
						field = super.createField(container, itemId,
								propertyId, uiContext);
					}
				}
				return field;
			}
		};
	}

	@Override
	protected SessionDevice getNewItem() {
		SessionDevice sd = new SessionDevice();
		sd.setSession(session);
		return sd;
	}

	@Override
	protected void saveData() {
		ArrayList<SessionDevice> sds = new ArrayList<SessionDevice>(
				dataSource.size());
		for (Object iid : dataSource.getItemIds()) {
			sds.add((SessionDevice) dataSource.getItem(iid).getBean());
		}
		session.setSessionDevices(sds);
	}

}
