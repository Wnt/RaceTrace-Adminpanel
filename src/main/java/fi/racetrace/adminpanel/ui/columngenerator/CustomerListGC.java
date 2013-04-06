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

package fi.racetrace.adminpanel.ui.columngenerator;

import java.util.Iterator;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.Customer;

public class CustomerListGC implements Table.ColumnGenerator {

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		Label l = null;
		Item item = source.getItem(itemId);

		List<Customer> customers = (List<Customer>) item.getItemProperty(
				"customers").getValue();

		if (customers != null && !customers.isEmpty()) {
			StringBuffer str = new StringBuffer();
			for (Iterator cIterator = customers.iterator(); cIterator.hasNext();) {
				Customer customer = (Customer) cIterator.next();
				str.append(customer.getName());
				if (cIterator.hasNext()) {
					str.append(", ");
				}
			}
			l = new Label(str.toString());
		} else {
			l = new Label("Not linked");
			l.addStyleName("italic");
		}

		return l;
	}
}
