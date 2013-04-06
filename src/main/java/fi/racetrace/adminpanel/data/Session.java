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
import java.util.Date;
import java.util.List;

public class Session implements Serializable {
	private int id;
	private Event event;
	private List<SessionDevice> sessionDevices;
	private String name;
	private String description;
	private Date start;
	private Date end;
	private int zoom;
	private double centerLat;
	private double centerLon;

	public double getCenterLat() {
		return centerLat;
	}

	public double getCenterLon() {
		return centerLon;
	}

	public String getDescription() {
		return description;
	}

	public Date getEnd() {
		return end;
	}

	public String getName() {
		return name;
	}

	public Date getStart() {
		return start;
	}

	public int getZoom() {
		return zoom;
	}

	public void setCenterLat(double centerLat) {
		this.centerLat = centerLat;
	}

	public void setCenterLon(double centerLon) {
		this.centerLon = centerLon;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<SessionDevice> getSessionDevices() {
		return sessionDevices;
	}

	public void setSessionDevices(List<SessionDevice> sessionDevices) {
		this.sessionDevices = sessionDevices;
	}
}
