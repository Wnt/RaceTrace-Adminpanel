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

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public abstract class EditableTable extends Panel implements Handler {
	protected Table table;
	protected HorizontalLayout buttons;
	protected Button addButton;
	protected Button discardButton;
	protected Button saveButton;
	protected Action actionNew = new Action("New");
	protected Action actionEdit = new Action("Edit");
	protected Action actionDelete = new Action("Delete");
	protected EditOneFieldFactory fieldFactory;
	protected BeanItemContainer<?> dataSource;
	protected VerticalLayout layout = new VerticalLayout();

	public EditableTable(BeanItemContainer<?> beanItemContainer) {
		this.dataSource = beanItemContainer;
		setContent(layout);
	}

	public EditableTable(BeanItemContainer<?> beanItemContainer, String caption) {
		super(caption);
		this.dataSource = beanItemContainer;
		setContent(layout);
	}

	protected void buildButtons() {
		buttons = new HorizontalLayout();
		layout.addComponent(buttons);

		addButton = new Button("Add new");
		buttons.addComponent(addButton);

		addButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				createNew();
			}
		});
	}

	protected void createNew() {
		Object newItem = getNewItem();
		table.addItem(newItem);
		table.select(newItem);

		fieldFactory.setEditableId(table.getValue());
		setEditableMode(true);
	}

	protected abstract Object getNewItem();

	protected void setEditableMode(boolean b) {
		if (b) {
			table.setEditable(true);

			addButton.setEnabled(false);
			buttons.addComponent(getDiscardButton());
			buttons.addComponent(getSaveButton());
		} else {
			table.setEditable(false);

			if (discardButton != null) {
				buttons.removeComponent(discardButton);
			}
			if (saveButton != null) {
				buttons.removeComponent(saveButton);
			}

			addButton.setEnabled(true);
		}
	}

	private Component getSaveButton() {
		if (saveButton == null) {
			saveButton = new Button("Save");
			saveButton.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					table.commit();
					setEditableMode(false);
					saveData();
				}

			});
		}
		return saveButton;
	}

	private Component getDiscardButton() {
		if (discardButton == null) {
			discardButton = new Button("Discard");
			discardButton.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					table.discard();
					setEditableMode(false);
				}

			});
		}
		return discardButton;
	}

	protected void buildTable() {
		initTable();
		layout.addComponent(table);

		table.setVisibleColumns(getNaturalColOrder());
		table.setColumnHeaders(getColHeaders());
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		table.setWidth(600, Unit.PIXELS);

		fieldFactory = getFieldFactory();
		table.setTableFieldFactory(fieldFactory);
		// table.setImmediate(false);
		table.setBuffered(true);

		table.addActionHandler(this);
	}

	protected EditOneFieldFactory getFieldFactory() {
		return new EditOneFieldFactory();
	}

	protected abstract String[] getColHeaders();

	protected abstract Object[] getNaturalColOrder();

	protected abstract void initTable();

	@Override
	public Action[] getActions(Object target, Object sender) {
		return new Action[] { actionNew, actionEdit, actionDelete };
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		if (action == actionNew) {
			createNew();
		} else if (table.getValue() == null) {
			Notification.show("No item selected!");
		} else if (action == actionEdit) {
			fieldFactory.setEditableId(table.getValue());
			setEditableMode(true);
		} else if (action == actionDelete) {
			String windowCaption = "Please confirm";
			String message = "Are you sure you want do delete the selected item?";
			String okCaption = "Delete";
			String cancelCaption = "Cancel";
			org.vaadin.dialogs.ConfirmDialog.Listener listener = new ConfirmDialog.Listener() {

				@Override
				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						Object item = table.getValue();
						table.removeItem(item);
						table.commit();
						removeItem(item);
						saveData();
					}
				}
			};
			ConfirmDialog.show(UI.getCurrent(), windowCaption, message,
					okCaption, cancelCaption, listener);
		}
	}

	protected abstract void saveData();

	protected void removeItem(Object object) {

	}

}
