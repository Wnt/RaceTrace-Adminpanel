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

import java.util.Date;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.ui.*;

import fi.racetrace.adminpanel.data.Contract;

public class DeviceCountGC implements Table.ColumnGenerator{

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		Label l = null;
		Item item = source.getItem(itemId);
		List<Contract> contracts = (List<Contract>) item.getItemProperty("contracts").getValue();
		int contractCount = 0;
		Date now = new Date();
		for (Contract contract : contracts) {
			if (contract.getEnd().after(now)) {
				contractCount += contract.getDeviceCount();
			}
		}
		l = new Label("" + contractCount);
		return l;
	}

}
