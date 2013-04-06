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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.Contract;

public class ContractCountGC implements Table.ColumnGenerator {

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		Item item = source.getItem(itemId);
		List<Contract> contracts = (List<Contract>) item.getItemProperty(
				"contracts").getValue();

		Date now = new Date();

		int countactCount = 0;
		for (Contract contract : contracts) {
			if (contract.getStart().before(now) && contract.getEnd().after(now)) {
				countactCount++;
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return countactCount;
	}

}