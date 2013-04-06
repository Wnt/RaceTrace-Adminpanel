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

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import fi.racetrace.adminpanel.data.SessionDevice;

public class TrailLengthGC implements Table.ColumnGenerator {

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		Component c = null;

		if (source.isEditable() && itemId.equals(source.getValue())) {

			VerticalLayout vl = new VerticalLayout();
			c = vl;
			final SessionDevice sessionDevice = (SessionDevice) itemId;

			Slider slider = new Slider(0, 60 * 20);
			vl.addComponent(slider);

			final Label label = new Label(
					formatSeconds(sessionDevice.getTrailLength()));
			vl.addComponent(label);

			slider.setPropertyDataSource(new BeanItem<SessionDevice>(
					sessionDevice).getItemProperty("trailLength"));
			slider.setWidth(100, Unit.PERCENTAGE);
			slider.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Double length = Double.parseDouble(event.getProperty()
							.getValue().toString());
					label.setValue(formatSeconds(length));
					// sessionDevice.setTrailLength(length);
				}
			});
		} else {
			Label l = new Label(
					formatSeconds(((SessionDevice) itemId).getTrailLength()));
			c = l;
		}
		return c;
	}

	public static String formatSeconds(double seconds) {
		Date date = new Date((long) seconds * 1000);
		String format = null;
		if (seconds % 60 == 0) {
			format = "m 'min'";
		} else {
			format = "m 'min,' s 'sec'";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
}
