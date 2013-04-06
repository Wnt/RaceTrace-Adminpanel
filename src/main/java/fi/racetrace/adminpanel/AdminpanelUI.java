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

package fi.racetrace.adminpanel;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.racetrace.adminpanel.data.CustomerContainer;
import fi.racetrace.adminpanel.data.UserContainer;
import fi.racetrace.adminpanel.ui.CustomerDetails;
import fi.racetrace.adminpanel.ui.CustomerList;
import fi.racetrace.adminpanel.ui.EventDetails;
import fi.racetrace.adminpanel.ui.NavigationTree;
import fi.racetrace.adminpanel.ui.SessionDetails;
import fi.racetrace.adminpanel.ui.UserView;

@Theme("Racetrace")
public class AdminpanelUI extends UI {

	private final HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();

	@Override
	public void init(VaadinRequest request) {

		VerticalLayout c = new VerticalLayout();
		horizontalSplit.setSecondComponent(c);
		// Create Navigator, use the UI content layout to display the views
		setNavigator(new Navigator(this, c));

		buildMainLayout();

		UserContainer userDataSource = UserContainer.createWithTestData();
		CustomerContainer customerDataSource = CustomerContainer
				.createWithTestData(userDataSource);
		// Add some Views
		getNavigator().addView(CustomerList.NAME,
				new CustomerList(customerDataSource));

		getNavigator().addView(UserView.NAME,
				new UserView(userDataSource, customerDataSource));
		getNavigator().addView(CustomerDetails.NAME,
				new CustomerDetails(customerDataSource));
		getNavigator().addView(EventDetails.NAME,
				new EventDetails(customerDataSource));
		getNavigator().addView(SessionDetails.NAME,
				new SessionDetails(customerDataSource));

		// Navigate to view
		// getNavigator().navigate();

	}

	private void buildMainLayout() {

		NavigationTree tree = new NavigationTree(getNavigator());
		horizontalSplit.setFirstComponent(tree);
		horizontalSplit.setSplitPosition(200, Unit.PIXELS);
		setContent(horizontalSplit);
		// TODO dynaaminen resize
		horizontalSplit.setHeight("1024px");
	}

	public void setMainComponent(Component c) {
		horizontalSplit.setSecondComponent(c);
	}
}
