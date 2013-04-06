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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.vaadin.vol.Point;

import com.vaadin.data.util.BeanItemContainer;

public class CustomerContainer extends BeanItemContainer<Customer> implements
		Serializable {

	private static Random r = new Random();
	private static int eventId = 1;
	public static int msToDaysMultiplier = 1000 * 60 * 60 * 24;
	public static int sixMonthsInDays = 30 * 6;
	public static Date now = new Date();
	private static int sessionId = 1;
	private static String[] testColors = { "00f2f2", "1f7a58", "4d9900",
			"4dff79", "6f1f7a", "7a411f", "43c230", "70b3db", "002699",
			"4330c2", "990000", "a64dff", "cedb70", "db7098", "f2b600",
			"f200b6" };
	private static int deviceIconId = 1;
	private static int updateId = 1;

	public CustomerContainer() throws IllegalArgumentException {
		super(Customer.class);
	}

	public static CustomerContainer createWithTestData(
			UserContainer userContainer) {
		CustomerContainer c = null;

		int userIndex = 0;

		try {
			c = new CustomerContainer();
			for (int i = 0; i < 8; i++) {
				Customer customer = new Customer();
				customer.setId(i + 1);
				customer.setName("Customer " + (i + 1));
				customer.setDescription("Description of customer " + (i + 1));

				ArrayList<User> users = new ArrayList<User>();
				if (userIndex < userContainer.size()) {
					User user = userContainer.getIdByIndex(userIndex);
					users.add(user);
					List<Customer> userCustomerList = user.getCustomers();
					if (userCustomerList == null) {
						userCustomerList = new ArrayList<Customer>();
						user.setCustomers(userCustomerList);
					}
					userCustomerList.add(customer);
					userIndex++;
				}
				customer.setUsers(users);

				createTestDevices(customer);
				createTestIcons(customer);
				createTestEvents(customer);
				createTestContracts(customer);

				c.addItem(customer);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return c;
	}

	private static void createTestIcons(Customer customer) {
		String[] icons = { "arrow.png", "boat.png" };
		ArrayList<DeviceIcon> deviceIcons = new ArrayList<DeviceIcon>(2);
		for (String icon : icons) {
			DeviceIcon i = new DeviceIcon();
			i.setId(deviceIconId);
			i.setPath(icon);

			deviceIcons.add(i);
			deviceIconId++;
		}
		customer.setIcons(deviceIcons);
	}

	protected static void createTestDevices(Customer customer) {
		int deviceCount = r.nextInt(12) + 4;

		ArrayList<Device> devices = new ArrayList<Device>(deviceCount);

		for (int i = 0; i < deviceCount; i++) {
			Device device = new Device();
			device.setCustomer(customer);
			device.setName("Device #" + (i + 1));
			device.setImei("01289600"
					+ String.format("%07d", r.nextInt(10000000)));
			device.setPhoneNumber("+35850"
					+ String.format("%07d", r.nextInt(10000000)));

			devices.add(device);
		}
		customer.setDevices(devices);
	}

	protected static void createTestEvents(Customer customer) {
		int eventCount = r.nextInt(3) + 1;

		ArrayList<Event> events = new ArrayList<Event>(eventCount);

		for (int i = 0; i < eventCount; i++) {
			Event event = new Event();
			event.setId(eventId);
			event.setCustomer(customer);
			event.setName("Event " + (i + 1));
			event.setDescription("Description of event " + (eventId));
			// 3 - 6 vrk menneisyydessä
			event.setStart(new Date(now.getTime() - msToDaysMultiplier * 3
					- r.nextInt(msToDaysMultiplier * 3)));
			// 3 - 0 vrk menneisyydessä
			event.setEnd(new Date(now.getTime() - msToDaysMultiplier * 3
					+ r.nextInt(msToDaysMultiplier * 3)));

			createTestSessions(event);

			events.add(event);
			eventId++;
		}
		customer.setEvents(events);
	}

	protected static void createTestSessions(Event event) {
		int sessionCount = r.nextInt(3) + 1;

		ArrayList<Session> sessions = new ArrayList<Session>(sessionCount);

		for (int i = 0; i < sessionCount; i++) {
			Session session = new Session();
			session.setId(sessionId);
			session.setEvent(event);
			session.setName("Session in event " + event.getName());
			session.setStart(new Date(event.getStart().getTime() + 1000 * 60
					* r.nextInt(60) * 10)); // 1 - 10 tuntia eventin alun
											// jälkeen
			session.setEnd(new Date(session.getStart().getTime() + 1000 * 60
					* r.nextInt(60) * 10)); // 1 - 10 tuntia startin jälkeen
			session.setCenterLat(60.452581);
			session.setCenterLon(22.30130);
			session.setZoom(12);

			generateTestSessionDevices(session);

			sessions.add(session);
			sessionId++;
		}
		event.setSessions(sessions);
	}

	protected static void generateTestSessionDevices(Session session) {
		List<Device> customerDevices = session.getEvent().getCustomer()
				.getDevices();
		int customerDeviceCount = customerDevices.size();
		int deviceCount = r.nextInt(customerDeviceCount - 1) + 1;

		ArrayList<SessionDevice> sessionDevices = new ArrayList<SessionDevice>(
				deviceCount);

		for (int i = 0; i < deviceCount; i++) {
			SessionDevice device = new SessionDevice();
			device.setSession(session);
			device.setDevice(customerDevices.get(i));
			device.setName("SD #" + (i + 1));
			device.setColor(testColors[i]);
			device.setIcon(session.getEvent().getCustomer().getIcons().get(0));
			device.setTrailLength(60);

			generateTestUpdates(device.getDevice(), session);

			sessionDevices.add(device);
		}
		session.setSessionDevices(sessionDevices);
	}

	private static void generateTestUpdates(Device device, Session session) {
		int updateCount = 100;

		for (int i = 0; i < updateCount; i++) {
			Update update = new Update();

			update.setId(updateId);
			update.setDevice(device);
			update.setTimestamp(new Date(session.getStart().getTime() + 1000
					* 60 + i * 4));
			update.setLatLon(new Point(22.3010 - 0.06 + r.nextDouble() * 0.06,
					60.45234 - 0.06 + r.nextDouble() * 0.06));
			update.setSpeed(5 + r.nextInt(10));
			update.setCourse(r.nextInt(360));
			update.setFixCount(3 + r.nextInt(9));
			update.setAltitude(-10 + r.nextInt(90));
			update.setBatteryLevel(3.82 + r.nextDouble() * 0.4);

			List<Update> allUpdates = device.getUpdates();
			if (allUpdates == null) {
				allUpdates = new ArrayList<Update>();
				device.setUpdates(allUpdates);
			}
			allUpdates.add(update);
			updateId++;
		}
	}

	protected static void createTestContracts(Customer c) {
		int contractCount = r.nextInt(3) + 1;

		ArrayList<Contract> contracts = new ArrayList<Contract>(contractCount);

		for (int i = 0; i < contractCount; i++) {
			Contract contract = new Contract();
			contract.setName("Contract " + (i + 1));
			contract.setDeviceCount(r.nextInt(10) + 5);
			// 12 - 6 kk menneisyydessä
			contract.setStart(new Date(now.getTime() - msToDaysMultiplier
					* (sixMonthsInDays + r.nextInt(sixMonthsInDays))));
			// -3 - 3kk
			contract.setEnd(new Date(now.getTime() + msToDaysMultiplier
					* (r.nextInt(sixMonthsInDays) - sixMonthsInDays / 2)));
			contracts.add(contract);
		}
		c.setContracts(contracts);
	}
}
