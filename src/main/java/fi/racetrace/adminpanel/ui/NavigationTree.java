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

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Tree;

@SuppressWarnings("serial")
public class NavigationTree extends Tree implements ItemClickListener {
    public static final Object CUSTOMERS = "Customers";
    public static final Object USERS = "Users";
	private Navigator navigator;

    public NavigationTree(Navigator navigator) {
    	this.navigator = navigator;
        addItem(CUSTOMERS);
        addItem(USERS);

        setChildrenAllowed(CUSTOMERS, false);
        setChildrenAllowed(USERS, false);

        /*
         * We want items to be selectable but do not want the user to be able to
         * de-select an item.
         */
        setSelectable(true);
        setNullSelectionAllowed(false);

        // Make application handle item click events
        addItemClickListener(this);

    }
	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.getSource() == this) {
            Object itemId = event.getItemId();
            if (itemId != null) {
                if (CUSTOMERS.equals(itemId)) {
                    navigator.navigateTo(CustomerList.NAME);
                } else if (USERS.equals(itemId)) {
                    navigator.navigateTo(UserView.NAME);
                }
            }
        }
	}
}
