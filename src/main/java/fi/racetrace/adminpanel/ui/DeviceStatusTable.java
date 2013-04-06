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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.SessionDevice;
import fi.racetrace.adminpanel.ui.columngenerator.BatteryGC;
import fi.racetrace.adminpanel.ui.columngenerator.CourseGC;
import fi.racetrace.adminpanel.ui.columngenerator.FixCountGC;
import fi.racetrace.adminpanel.ui.columngenerator.SpeedGC;

public class DeviceStatusTable extends EditableTable {
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "name",
			"speed", "course", "fixCount", "batteryLevel" };
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Name",
			"Speed", "Course", "Fix #", "Battery" };
	private final List<SessionDevice> devices;

	public DeviceStatusTable(ArrayList<SessionDevice> devices) {
		super(
				new BeanItemContainer<SessionDevice>(SessionDevice.class,
						devices));
		this.devices = devices;
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

		table.addGeneratedColumn("speed", new SpeedGC());
		table.addGeneratedColumn("course", new CourseGC());
		table.addGeneratedColumn("fixCount", new FixCountGC());
		table.addGeneratedColumn("batteryLevel", new BatteryGC());

	}

	@Override
	protected Object getNewItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void saveData() {
		// TODO Auto-generated method stub

	}

}
