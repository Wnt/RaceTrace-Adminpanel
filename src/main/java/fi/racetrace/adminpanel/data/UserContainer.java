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

import com.vaadin.data.util.BeanItemContainer;

public class UserContainer extends BeanItemContainer<User> {
	public UserContainer() throws IllegalArgumentException {
		super(User.class);
	}

	public static UserContainer createWithTestData() {
		UserContainer c = null;
		try {
			c = new UserContainer();
			for (int i = 0; i < 10; i++) {
				User user = new User();
				user.setId(i + 1);
				user.setEmail("User " + (i + 1));

				c.addItem(user);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return c;
	}

	public int getUnlinkedCount() {
		int count = 0;
		for (User user : getItemIds()) {
			if (user.getCustomers() == null || user.getCustomers().isEmpty()) {
				count++;
			}
		}
		return count;
	}

	public BeanItemContainer<User> getUnlinked() {
		BeanItemContainer<User> c = new BeanItemContainer<User>(User.class);
		for (User user : getItemIds()) {
			if (user.getCustomers() == null || user.getCustomers().isEmpty()) {
				c.addItem(user);
			}
		}
		return c;
	}
}
