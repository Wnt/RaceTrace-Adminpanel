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

import org.vaadin.addon.levelindicator.Levelindicator;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import fi.racetrace.adminpanel.data.SessionDevice;
import fi.racetrace.adminpanel.data.Update;

public class FixCountGC implements ColumnGenerator {

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		final SessionDevice sessionDevice = (SessionDevice) itemId;
		Update latestUpdate = sessionDevice.getDevice().getLatestUpdate();

		HorizontalLayout hl = new HorizontalLayout();

		int lvl = (int) Math
				.round(((double) latestUpdate.getFixCount() - 1) / 3);

		hl.addComponent(new Levelindicator(3, lvl));
		hl.addComponent(new Label("" + latestUpdate.getFixCount()));
		return hl;
	}
}
