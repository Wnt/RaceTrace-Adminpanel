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

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

import fi.racetrace.adminpanel.data.Session;

public class SessionForm extends Panel {

	TextField name = new TextField("Name");
	@PropertyId("description")
	RichTextArea descriptionField = new RichTextArea("Description");
	DateField start = new DateField("Start");
	DateField end = new DateField("End");
	TextField zoom = new TextField("Zoom");
	TextField centerLat = new TextField("Latitude");
	TextField centerLon = new TextField("Longitude");
	VolLatLngPicker latLogPicker;

	public SessionForm(Session session) {

		latLogPicker = new VolLatLngPicker(session.getCenterLon(),
				session.getCenterLat());

		FormLayout layout = new FormLayout();
		layout.addComponent(name);
		layout.addComponent(descriptionField);
		layout.addComponent(start);
		layout.addComponent(end);
		layout.addComponent(zoom);
		layout.addComponent(centerLat);
		layout.addComponent(centerLon);
		layout.addComponent(latLogPicker);

		latLogPicker.setLatField(centerLat);
		latLogPicker.setLonField(centerLon);

		final FieldGroup binder = new FieldGroup(new BeanItem<Session>(session));
		binder.bindMemberFields(this);

		HorizontalLayout buttonPanel = new HorizontalLayout();
		layout.addComponent(buttonPanel);

		buttonPanel.addComponent(new Button("Discard",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						binder.discard();
						Notification.show("Discarded!");
					}
				}));
		buttonPanel.addComponent(new Button("Save", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
					Notification.show("Changes saved!");
				} catch (CommitException e) {
					Notification.show("An error occurred!");
				}
			}
		}));

		setContent(layout);
	}
}
