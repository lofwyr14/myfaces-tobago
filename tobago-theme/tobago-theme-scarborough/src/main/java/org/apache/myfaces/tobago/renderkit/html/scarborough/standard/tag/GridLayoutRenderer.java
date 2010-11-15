package org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.config.Configurable;
import org.apache.myfaces.tobago.layout.LayoutContainer;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.renderkit.MarginValues;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.SpacingValues;
import org.apache.myfaces.tobago.renderkit.util.RenderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class GridLayoutRenderer extends RendererBase implements SpacingValues, MarginValues {

  private static final Logger LOG = LoggerFactory.getLogger(GridLayoutRenderer.class);

  @Override
  public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
//    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
//    UIGridLayout gridLayout = (UIGridLayout) component;
//    writer.startElement(HtmlElements.DIV, gridLayout);
//    writer.writeClassAttribute();
  }

  @Override
  public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException {
    UIComponent container = component.getParent();
    if (container instanceof LayoutContainer && !((LayoutContainer) container).isLayoutChildren()) {
      return;
    }
    RenderUtils.encodeChildren(facesContext, container);
  }

  @Override
  public void encodeEnd(
      FacesContext facesContext,
      UIComponent component) throws IOException {
//    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
//    writer.endElement(HtmlElements.DIV);
  }

  public Measure getColumnSpacing(FacesContext facesContext, Configurable component) {
    return getResourceManager().getThemeMeasure(facesContext, component, Attributes.COLUMN_SPACING);
  }

  public Measure getRowSpacing(FacesContext facesContext, Configurable component) {
    return getResourceManager().getThemeMeasure(facesContext, component, Attributes.ROW_SPACING);
  }

  public Measure getMarginLeft(FacesContext facesContext, Configurable component) {
    Configurable parent = (Configurable) ((UIComponent) component).getParent();
    return getResourceManager().getThemeMeasure(facesContext, parent, Attributes.MARGIN_LEFT);
  }

  public Measure getMarginRight(FacesContext facesContext, Configurable component) {
    Configurable parent = (Configurable) ((UIComponent) component).getParent();
    return getResourceManager().getThemeMeasure(facesContext, parent, Attributes.MARGIN_RIGHT);
  }

  public Measure getMarginTop(FacesContext facesContext, Configurable component) {
    Configurable parent = (Configurable) ((UIComponent) component).getParent();
    return getResourceManager().getThemeMeasure(facesContext, parent, Attributes.MARGIN_TOP);
  }

  public Measure getMarginBottom(FacesContext facesContext, Configurable component) {
    Configurable parent = (Configurable) ((UIComponent) component).getParent();
    return getResourceManager().getThemeMeasure(facesContext, parent, Attributes.MARGIN_BOTTOM);
  }
}
