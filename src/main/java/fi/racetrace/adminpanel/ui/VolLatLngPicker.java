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

import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Point;
import org.vaadin.vol.PointVector;
import org.vaadin.vol.VectorLayer;
import org.vaadin.vol.VectorLayer.DrawingMode;
import org.vaadin.vol.VectorLayer.VectorDrawnEvent;
import org.vaadin.vol.VectorLayer.VectorDrawnListener;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;

public class VolLatLngPicker extends CustomComponent {
	private final OpenLayersMap map = new OpenLayersMap();
	private Field<String> latField;
	private Field<String> lonField;
	private VectorLayer markerLayer;
	private double centerLon;
	private double centerLat;
	private PointVector pointVector;

	public VolLatLngPicker(double centerLon, double centerLat) {
		this.centerLon = centerLon;
		this.centerLat = centerLat;

		setCompositionRoot(map);

		map.setCenter(centerLon, centerLat);
		map.setZoom(10);
		map.setWidth(400, Unit.PIXELS);
		map.setHeight(400, Unit.PIXELS);
		map.setImmediate(true);
		// base layer
		map.addLayer(new OpenStreetMapLayer());
		// vector layer where user can draw his desired location
		VectorLayer vl = new VectorLayer();
		map.addLayer(vl);
		vl.setImmediate(true);

		vl.setDrawindMode(DrawingMode.POINT);
		vl.addListener(new VectorDrawnListener() {
			@Override
			public void vectorDrawn(VectorDrawnEvent event) {
				PointVector v = (PointVector) event.getVector();
				Point point = v.getPoint();
				locationSelected(point);
			}
		});

		updateMarker();

	}

	private void updateMarker() {
		if (markerLayer == null) {
			markerLayer = new VectorLayer();
			map.addLayer(markerLayer);
		}

		if (pointVector == null) {
			pointVector = new PointVector(centerLon, centerLat);
			// pointVector.setStyleName("marker");
			markerLayer.addVector(pointVector);
		}
		Point[] points = { new Point(centerLon, centerLat) };
		pointVector.setPoints(points);
	}

	public void setLatField(Field<String> field) {
		latField = field;
	}

	public void setLonField(Field<String> field) {
		lonField = field;
	}

	public void locationSelected(Point point) {
		centerLat = point.getLat();
		centerLon = point.getLon();
		latField.setValue("" + centerLat);
		lonField.setValue("" + centerLon);
		map.setCenter(centerLon, centerLat);
		updateMarker();
	}
}
