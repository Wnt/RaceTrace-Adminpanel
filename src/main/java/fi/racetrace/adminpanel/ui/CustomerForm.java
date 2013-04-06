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

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

public class CustomerForm extends Panel {

	TextField name = new TextField("Name");
	@PropertyId("description")
	RichTextArea descriptionField = new RichTextArea("Description");

	public CustomerForm(Item customer) {

		FormLayout layout = new FormLayout();
		layout.addComponent(name);
		layout.addComponent(descriptionField);

		final FieldGroup binder = new FieldGroup(customer);
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
