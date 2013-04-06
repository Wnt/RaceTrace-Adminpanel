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

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.VerticalLocationIs;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

import fi.racetrace.adminpanel.data.Contract;
import fi.racetrace.adminpanel.data.Customer;
import fi.racetrace.adminpanel.data.CustomerContainer;
import fi.racetrace.adminpanel.data.User;
import fi.racetrace.adminpanel.data.UserContainer;

public class UserView extends Panel implements View {

	public static final String NAME = "users";
	private final UserContainer userDataSource;
	private UserTable userList;
	private final CustomerContainer customerDataSource;
	private HorizontalLayout split;
	private Table customerTable;
	private Table groupsTable;
	private VerticalLayout customersGroups;

	public enum Mode {
		ALL, UNLINKED, CUSTOMER;
	}

	private Mode userListMode = Mode.ALL;

	public UserView(UserContainer userDataSource,
			CustomerContainer customerDataSource) {
		this.userDataSource = userDataSource;
		this.customerDataSource = customerDataSource;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// addComponent(new Label("User list"));
		if (split == null) {
			this.buildLayout();
		}
		setContent(split);
		groupsTable.select(0);
		setTitle();
	}

	private void buildLayout() {
		split = new HorizontalLayout();

		customersGroups = new VerticalLayout();
		split.addComponent(customersGroups);

		buildGroupsTable();
		buildCustomersTable();

		userList = new UserTable(this);
		split.addComponent(userList);

	}

	private void buildCustomersTable() {
		customerTable = new Table("Customers", customerDataSource);
		customersGroups.addComponent(customerTable);

		customerTable.setWidth(164, Unit.PIXELS);
		customerTable.setSelectable(true);
		customerTable.setImmediate(true);
		// customerTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		customerTable.addGeneratedColumn("userCount", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Label l = null;
				Item item = source.getItem(itemId);
				List<Contract> users = (List<Contract>) item.getItemProperty(
						"users").getValue();
				l = new Label("" + users.size());
				return l;
			}
		});

		String[] visibleColumns = { "name", "userCount" };
		customerTable.setVisibleColumns(visibleColumns);
		String[] columnHeaders = { "Name", "Users" };
		customerTable.setColumnHeaders(columnHeaders);

		customerTable
				.addValueChangeListener(new Property.ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						if (event.getProperty().getValue() != null) {
							setUserListMode(Mode.CUSTOMER);
						}
					}

				});

		createCustomerDropHandler();
	}

	private void useCustomerUsers() {
		groupsTable.select(null);
		userList.setCustomer((Customer) customerTable.getValue());
	}

	private void createCustomerDropHandler() {
		customerTable.setDropHandler(new DropHandler() {

			@Override
			public void drop(DragAndDropEvent dropEvent) {
				DataBoundTransferable t = (DataBoundTransferable) dropEvent
						.getTransferable();
				User user = (User) t.getItemId();

				AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) dropEvent
						.getTargetDetails());
				Customer customer = (Customer) dropData.getItemIdOver();

				List<User> customerUsers = customer.getUsers();
				if (customerUsers == null) {
					customerUsers = new ArrayList<User>();
				}
				customerUsers.add(user);

				List<Customer> userCustomerList = user.getCustomers();
				if (userCustomerList == null) {
					userCustomerList = new ArrayList<Customer>();
					user.setCustomers(userCustomerList);
				}
				userCustomerList.add(customer);

				customerTable.refreshRowCache();
				userList.refreshRowCache();
				updateGroupsTable();

				if (userListMode == Mode.UNLINKED) {
					// refresh user table
					useUnlinkedUsers();
				}

				Notification.show("Added " + user.getEmail() + " to "
						+ customer);
			}

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return VerticalLocationIs.MIDDLE;
			}

		});
	}

	private void buildGroupsTable() {
		groupsTable = new Table("Groups");
		customersGroups.addComponent(groupsTable);

		groupsTable.addContainerProperty("Group", String.class, null);
		groupsTable.addContainerProperty("Users", String.class, null);

		groupsTable.setPageLength(2);
		groupsTable.setWidth(164, Unit.PIXELS);
		groupsTable.setSelectable(true);
		groupsTable.setImmediate(true);

		String[] groupAll = { "All users", userDataSource.size() + "" };
		String[] groupUnlinked = { "Unlinked",
				userDataSource.getUnlinkedCount() + "" };

		groupsTable.addItem(groupAll, 0);
		groupsTable.addItem(groupUnlinked, 1);

		groupsTable.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					if (event.getProperty().getValue().equals(0)) {
						setUserListMode(Mode.ALL);
					} else {
						setUserListMode(Mode.UNLINKED);
					}
				}
			}

		});
	}

	public void setUserListMode(Mode mode) {
		userListMode = mode;

		switch (mode) {
		case UNLINKED:
			useUnlinkedUsers();
			break;
		case CUSTOMER:
			useCustomerUsers();
			break;
		default:
			useAllUsers();
			break;
		}
	}

	public Mode getUserListMode() {
		return userListMode;
	}

	private void useUnlinkedUsers() {
		userList.setCustomerNull();
		userList.setDataSource(userDataSource.getUnlinked());
		customerTable.select(null);
	}

	private void useAllUsers() {
		userList.setCustomerNull();
		userList.setDataSource(userDataSource);
		customerTable.select(null);
	}

	private void setTitle() {
		Page.getCurrent().setTitle("Users - RaceTrace");
	}

	public BeanItemContainer<User> getUserDataContainer() {
		return userDataSource;
	}

	public void updateGroupsTable() {
		groupsTable.getItem(0).getItemProperty("Users")
				.setValue(userDataSource.size() + "");
		groupsTable.getItem(1).getItemProperty("Users")
				.setValue(userDataSource.getUnlinkedCount() + "");

	}

	public void reloadTables() {
		updateGroupsTable();
		customerTable.refreshRowCache();
	}

	public void removeUser(User user) {
		userDataSource.removeItem(user);
	}
}
