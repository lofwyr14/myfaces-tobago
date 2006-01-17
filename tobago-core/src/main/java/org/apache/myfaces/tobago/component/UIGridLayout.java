/*
 * Copyright 2002-2005 The Apache Software Foundation.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 * Created Nov 20, 2002 at 11:39:23 AM.
 * $Id$
 */
package org.apache.myfaces.tobago.component;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_COLUMNS;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_LAYOUT_ROWS;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_SPAN_X;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_SPAN_Y;
import org.apache.myfaces.tobago.util.LayoutUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UIGridLayout extends UILayout {

  private static final Log LOG = LogFactory.getLog(UIGridLayout.class);

  public static final String COMPONENT_TYPE = "org.apache.myfaces.tobago.GridLayout";
  public static final String COMPONENT_FAMILY = "org.apache.myfaces.tobago.GridLayout";

  public static final Marker FREE = new Marker("free");
  public static final String USED = "used";

  @Override
  public String getFamily() {
    return COMPONENT_FAMILY;
  }

  private boolean ignoreFree;

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  @Override
  public void encodeChildren(FacesContext context)
      throws IOException {
    // do nothing here
  }

  @Override
  public void encodeChildrenOfComponent(
      FacesContext facesContext, UIComponent component) throws IOException {
    super.encodeChildrenOfComponent(facesContext, component);
    clearRows();
  }

  private void clearRows() {
    getAttributes().remove(ATTR_LAYOUT_ROWS);
  }

  public int getColumnCount() {
    String columns =
        ComponentUtil.getStringAttribute(this, ATTR_COLUMNS);
    int columnCount;
    if (columns != null) {
      columnCount = 1 + StringUtils.countMatches(columns, ";");
    } else {
      columnCount = 1;
    }
    return columnCount;
  }

  public List<Row> ensureRows() {
    List<Row> rows = (List<Row>) getAttributes().get(ATTR_LAYOUT_ROWS);
    if (rows == null) {
      rows = createRows();
    }
    return rows;
  }

  private List<Row> createRows() {
    List<Row> rows = new ArrayList<Row>();
    int columnCount = getColumnCount();
    List<UIComponent> children
        = LayoutUtil.addChildren(new ArrayList<UIComponent>(), getParent());

    for (UIComponent component : children) {
      int spanX = getSpanX(component);
      int spanY = getSpanY(component);

      int r = nextFreeRow(rows);
      if (r == rows.size()) {
        rows.add(new Row(columnCount));
      }
      int c = rows.get(r).nextFreeColumn();
      rows.get(r).addControl(component, spanX);
      rows.get(r).fill(c + 1, c + spanX, component.isRendered());

      for (int i = r + 1; i < r + spanY; i++) {

        if (i == rows.size()) {
          rows.add(new Row(columnCount));
        }
        rows.get(i).fill(c, c + spanX, component.isRendered());
      }
    }
    getAttributes().put(ATTR_LAYOUT_ROWS, rows);
    return rows;
  }

  private int nextFreeRow(List rows) {
    int i = 0;
    for (; i < rows.size(); i++) {
      if (((Row) rows.get(i)).nextFreeColumn() != -1) {
        return i;
      }
    }
    return i;
  }

  public static int getSpanX(UIComponent component) {
    return ComponentUtil.getIntAttribute(
        component, ATTR_SPAN_X, 1);
  }

  public static int getSpanY(UIComponent component) {
    return ComponentUtil.getIntAttribute(
        component, ATTR_SPAN_Y, 1);
  }

// ///////////////////////////////////////////// bean getter + setter

  public boolean isIgnoreFree() {
    return ignoreFree;
  }

  public void setIgnoreFree(boolean ignoreFree) {
    this.ignoreFree = ignoreFree;
  }

// ///////////////////////////////////////////////////////////////
// ///////////////////////////////////////////// class Row
// ///////////////////////////////////////////////////////////////

  public static class Row implements Serializable {

    private int columns;

    private List cells;

    private boolean hidden;

    public Row(int columns) {
      setColumns(columns);
    }

    private void addControl(UIComponent component, int spanX) {

      int i = nextFreeColumn();

      cells.set(i, component);
      fill(i + 1, i + spanX, component.isRendered());
    }

    private void fill(int start, int end, boolean rendered) {

      if (end > columns) {
        LOG.error("Error in Jsp (end > columns). " +
            "Try to insert more spanX as possible.");
        LOG.error("start:   " + start);
        LOG.error("end:     " + end);
        LOG.error("columns: " + columns);
        LOG.error("Actual cells:");
        for (Object component : cells) {
          if (component instanceof UIComponent) {
            LOG.error("Cell-ID: " + ((UIComponent) component).getId()
                + " " + ((UIComponent) component).getRendererType());
          } else {
            LOG.error("Cell:    " + component); // e.g. marker
          }
        }

        end = columns; // fix the "end" parameter to continue the processing.
      }

      for (int i = start; i < end; i++) {
        cells.set(i, new Marker(USED, rendered));
      }
    }

    private int nextFreeColumn() {
      for (int i = 0; i < columns; i++) {
        if (FREE.equals(cells.get(i))) {
          return i;
        }
      }
      return -1;
    }

    public List getElements() {
      return cells;
    }

    public int getColumns() {
      return columns;
    }

    private void setColumns(int columns) {
      this.columns = columns;
      cells = new ArrayList(columns);
      for (int i = 0; i < columns; i++) {
        cells.add(FREE);
      }
    }

    public boolean isHidden() {
      return hidden;
    }

    public void setHidden(boolean hidden) {
      this.hidden = hidden;
    }
  }

  public static class Marker implements Serializable {

    private final String name;

    private boolean rendered;

    private Marker(String name) {
      this.name = name;
    }

    public Marker(String name, boolean rendered) {
      this.name = name;
      this.rendered = rendered;
    }

    @Override
    public String toString() {
      return name;
    }

    public boolean isRendered() {
      return rendered;
    }
  }
}
