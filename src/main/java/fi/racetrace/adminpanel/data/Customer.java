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

package fi.racetrace.adminpanel.data;

import java.io.Serializable;
import java.util.List;

public class Customer implements Serializable {
	private int id;
	private String name;
	private String description;
	private List<Event> events;
	private List<Contract> contracts;
	private List<Device> devices;
	private List<DeviceIcon> icons;
	private List<User> users;

	public List<Contract> getContracts() {
		return contracts;
	}

	public String getDescription() {
		return description;
	}

	public List<Event> getEvents() {
		return events;
	}

	public String getName() {
		return name;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<DeviceIcon> getIcons() {
		return icons;
	}

	public void setIcons(List<DeviceIcon> icons) {
		this.icons = icons;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return name;
	}
}
