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

import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import fi.racetrace.adminpanel.data.DeviceIcon;

public class DeviceIconGC implements Table.ColumnGenerator {

	private final List<fi.racetrace.adminpanel.data.DeviceIcon> icons;

	public DeviceIconGC(List<fi.racetrace.adminpanel.data.DeviceIcon> icons) {
		super();
		this.icons = icons;
	}

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		Component c = null;

		if (source.isEditable() && itemId.equals(source.getValue())) {
			// TODO bindaus modeliin
			ComboBox cb = new ComboBox(
					"Icon",
					new BeanItemContainer<fi.racetrace.adminpanel.data.DeviceIcon>(
							fi.racetrace.adminpanel.data.DeviceIcon.class, icons));
			cb.setWidth(100, Unit.PIXELS);

			final Item sdItem = source.getItem(itemId);

			cb.setValue(sdItem.getItemProperty("icon"));

			cb.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					sdItem.getItemProperty("icon").setValue(
							event.getProperty().getValue());
				}
			});

			c = cb;
		} else {
			Item item = source.getItem(itemId);
			DeviceIcon di = ((DeviceIcon) item.getItemProperty("icon")
					.getValue());
			if (di != null) {
				String icon = di.getPath();
				Embedded e = new Embedded("icon", new ThemeResource("img/"
						+ icon));
				c = e;
			} else {
				Label l = new Label("N/A");
				c = l;
			}
		}
		return c;
	}
}
