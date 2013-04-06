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

import java.util.Arrays;

import org.vaadin.sparklines.Sparklines;

import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class SpeedGC implements ColumnGenerator {

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		Sparklines s = new Sparklines("Stuff", 0, 0, 50, 100);
		s.setDescription("Adds line indicating average");
		s.setValue(Arrays.asList(60, 62, 55, 62, 63, 64, 63, 65, 68, 65, 69,
				70, 75, 74, 75, 74, 78, 76, 74, 85, 70, 65, 63, 64, 69, 74, 65));
		s.setAverageVisible(true);
		return s;
	}

}
