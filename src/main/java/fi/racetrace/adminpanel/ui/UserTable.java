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
import com.vaadin.event.Action;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;

import fi.racetrace.adminpanel.data.Customer;
import fi.racetrace.adminpanel.data.User;
import fi.racetrace.adminpanel.ui.UserView.Mode;
import fi.racetrace.adminpanel.ui.columngenerator.CustomerListGC;

public class UserTable extends EditableTable {
	public static final Object[] NATURAL_COL_ORDER = new Object[] { "id",
			"email", "lastActive" };
	public static final String[] COL_HEADERS_ENGLISH = new String[] { "Id",
			"Email", "Last active" };
	public static final Object[] NATURAL_COL_ORDER_GROUPS = new Object[] {
			"id", "email", "lastActive", "customerList" };
	public static final String[] COL_HEADERS_ENGLISH_GROUPS = new String[] {
			"Id", "Email", "Last active", "Customers" };
	private Customer customer;
	private final BeanItemContainer<User> allUsers;
	private final UserView userView;
	private final Action actionUnlinkUser = new Action("Unlink from customer");

	public UserTable(UserView userView) {
		super(userView.getUserDataContainer(), "Users");
		this.userView = userView;
		dataSource = userView.getUserDataContainer();
		allUsers = (BeanItemContainer<User>) dataSource;
		buildTable();
		buildButtons();
	}

	@Override
	protected String[] getColHeaders() {
		return userView.getUserListMode() == Mode.CUSTOMER ? COL_HEADERS_ENGLISH
				: COL_HEADERS_ENGLISH_GROUPS;
	}

	@Override
	protected Object[] getNaturalColOrder() {
		return userView.getUserListMode() == Mode.CUSTOMER ? NATURAL_COL_ORDER
				: NATURAL_COL_ORDER_GROUPS;
	}

	@Override
	protected void initTable() {
		table = new Table(null, dataSource) {
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

		table.addGeneratedColumn("customerList", new CustomerListGC());

		table.setDragMode(TableDragMode.ROW);
	}

	@Override
	public Action[] getActions(Object target, Object sender) {
		Action[] actions = null;
		if (userView.getUserListMode() == Mode.CUSTOMER) {
			actions = new Action[] { actionNew, actionEdit, actionUnlinkUser,
					actionDelete };
		} else {
			actions = new Action[] { actionNew, actionEdit, actionDelete };
		}
		return actions;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {

		super.handleAction(action, sender, target);
		if (action == actionUnlinkUser && customer != null) {
			User user = (User) table.getValue();
			user.getCustomers().remove(customer);
			customer.getUsers().remove(user);

			// reloads user table
			setCustomer(customer);
			userView.reloadTables();
		}
	}

	@Override
	protected User getNewItem() {
		User u = new User();
		if (customer != null) {
			ArrayList<Customer> c = new ArrayList<Customer>();
			u.setCustomers(c);
			c.add(customer);

			List<User> customerUsers = customer.getUsers();
			if (customerUsers == null) {
				customerUsers = new ArrayList<User>();
			}
			customerUsers.add(u);
			allUsers.addBean(u);
		}
		return u;
	}

	@Override
	protected void saveData() {
		userView.reloadTables();
	}

	@Override
	protected void removeItem(Object object) {
		User user = (User) object;
		if (customer != null) {
			customer.getUsers().remove(user);
		}
		userView.removeUser(user);
		// TODO tarviiko tätä tehdä?
		// user.setCustomers(null);
	}

	public void setDataSource(BeanItemContainer<User> ds) {
		dataSource = ds;
		table.setContainerDataSource(dataSource);
		table.setVisibleColumns(getNaturalColOrder());
	}

	public void setCustomer(Customer customer) {
		List<User> users = customer.getUsers();
		setDataSource(new BeanItemContainer<User>(User.class, users));
		this.customer = customer;
	}

	public void setCustomerNull() {
		this.customer = null;
	}

	public void refreshRowCache() {
		table.refreshRowCache();
	}
}
