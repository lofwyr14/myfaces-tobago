/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.internal.renderkit.renderer;

import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.internal.component.AbstractUISelectOneList;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.SelectItemUtils;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.CssItem;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.Arias;
import org.apache.myfaces.tobago.renderkit.html.CustomAttributes;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.HtmlInputTypes;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectOneListRenderer<T extends AbstractUISelectOneList> extends SelectOneRendererBase<T> {

  @Override
  protected void encodeBeginField(FacesContext facesContext, T component) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    final String clientId = component.getClientId(facesContext);
    final String fieldId = component.getFieldId(facesContext);
    final String selectFieldId = clientId + ComponentUtils.SUB_SEPARATOR + "selectField";
    final String filterId = clientId + ComponentUtils.SUB_SEPARATOR + "filter";
    final List<SelectItem> items = SelectItemUtils.getItemList(facesContext, component);
    final boolean disabled = !items.iterator().hasNext() || component.isDisabled() || component.isReadonly();
    final String filter = component.getFilter();
    final boolean expanded = component.isExpanded();
    final String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, component);
    final Integer tabIndex = component.getTabIndex();

    encodeHiddenSelect(facesContext, component, items, clientId, fieldId, disabled);

    writer.startElement(HtmlElements.DIV);
    writer.writeClassAttribute(
        expanded ? BootstrapClass.LIST_GROUP : BootstrapClass.DROPDOWN,
        expanded ? BootstrapClass.borderColor(ComponentUtils.getMaximumSeverity(component)) : null);

    encodeSelectField(facesContext, component,
        clientId, selectFieldId, filterId, filter, disabled, expanded, title, tabIndex);
    encodeOptions(facesContext, component, items, clientId, expanded, disabled);

    writer.endElement(HtmlElements.DIV);
  }

  private void encodeHiddenSelect(
      final FacesContext facesContext, final T component, final List<SelectItem> items,
      final String clientId, final String fieldId, final boolean disabled) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    writer.startElement(HtmlElements.SELECT);
    writer.writeIdAttribute(fieldId);
    writer.writeNameAttribute(clientId);
    writer.writeAttribute(HtmlAttributes.DISABLED, disabled);
    writer.writeAttribute(HtmlAttributes.REQUIRED, component.isRequired());
    writer.writeClassAttribute(BootstrapClass.D_NONE);

    final String submittedValue = (String) component.getSubmittedValue();
    renderSelectItems(component, null, items, component.getValue(), submittedValue, writer, facesContext);
    writer.endElement(HtmlElements.SELECT);
  }

  private void encodeSelectField(
      final FacesContext facesContext, final T component,
      final String clientId, final String selectFieldId, final String filterId, final String filter,
      final boolean disabled, final boolean expanded, final String title, final Integer tabIndex) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final Markup markup = component.getMarkup() != null ? component.getMarkup() : Markup.NULL;

    writer.startElement(HtmlElements.DIV);
    writer.writeIdAttribute(selectFieldId);
    writer.writeNameAttribute(clientId);
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, component);
    writer.writeClassAttribute(
        expanded ? BootstrapClass.FORM_CONTROL : BootstrapClass.FORM_SELECT,
        expanded && markup.contains(Markup.LARGE) ? BootstrapClass.FORM_CONTROL_LG : null,
        expanded && markup.contains(Markup.SMALL) ? BootstrapClass.FORM_CONTROL_SM : null,
        !expanded && markup.contains(Markup.LARGE) ? BootstrapClass.FORM_SELECT_LG : null,
        !expanded && markup.contains(Markup.SMALL) ? BootstrapClass.FORM_SELECT_SM : null,
        TobagoClass.SELECT__FIELD,
        expanded ? BootstrapClass.LIST_GROUP_ITEM : BootstrapClass.DROPDOWN_TOGGLE,
        expanded ? null : BootstrapClass.borderColor(ComponentUtils.getMaximumSeverity(component)),
        component.getCustomClass());
    writer.writeAttribute(HtmlAttributes.TITLE, title, true);
    writer.writeAttribute(Arias.EXPANDED, Boolean.FALSE.toString(), false);
    writer.writeAttribute(HtmlAttributes.DISABLED, disabled);

    writer.startElement(HtmlElements.SPAN);
    writer.endElement(HtmlElements.SPAN);

    writer.startElement(HtmlElements.INPUT);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.SEARCH);
    writer.writeIdAttribute(filterId);
    writer.writeClassAttribute(TobagoClass.FILTER, BootstrapClass.FORM_CONTROL);
    writer.writeAttribute(HtmlAttributes.AUTOCOMPLETE, "off", false);
    writer.writeAttribute(HtmlAttributes.READONLY, filter == null || filter.isEmpty());
    writer.writeAttribute(HtmlAttributes.DISABLED, disabled);
    writer.writeAttribute(HtmlAttributes.TABINDEX, tabIndex);
    renderFocus(clientId, component.isFocus(), component.isError(), facesContext, writer);

    writer.endElement(HtmlElements.INPUT);

    writer.endElement(HtmlElements.DIV);
  }

  private void encodeOptions(
      final FacesContext facesContext, final T component, final List<SelectItem> items,
      final String clientId, final boolean expanded, final boolean disabled) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final Markup markup = component.getMarkup() != null ? component.getMarkup() : Markup.NULL;

    writer.startElement(HtmlElements.DIV);
    writer.writeClassAttribute(
        TobagoClass.OPTIONS,
        expanded ? BootstrapClass.LIST_GROUP_ITEM : TobagoClass.DROPDOWN__MENU);
    writer.writeNameAttribute(clientId);

    writer.startElement(HtmlElements.TABLE);
    writer.writeClassAttribute(BootstrapClass.TABLE, BootstrapClass.TABLE_HOVER,
        markup.contains(Markup.LARGE) ? TobagoClass.LARGE : null,
        markup.contains(Markup.SMALL) ? TobagoClass.SMALL : null,
        markup.contains(Markup.LARGE) ? null : BootstrapClass.TABLE_SM);
    writer.startElement(HtmlElements.TBODY);

    final Object value = component.getValue();
    final Object submittedValue = component.getSubmittedValue();
    for (SelectItem item : items) {
      if (item instanceof SelectItemGroup) {
        writer.startElement(HtmlElements.TR);
        writer.writeClassAttribute(TobagoClass.SELECT__ITEM__GROUP, BootstrapClass.DISABLED);
        writer.writeAttribute(HtmlAttributes.TABINDEX, -1);
        writer.startElement(HtmlElements.TD);
        final String label = item.getLabel();
        if (label != null) {
          writer.writeText(label);
        }
        writer.endElement(HtmlElements.TD);
        writer.endElement(HtmlElements.TR);
        final SelectItem[] selectItems = ((SelectItemGroup) item).getSelectItems();
        for (SelectItem selectItem : selectItems) {
          encodeSelectItem(facesContext, writer, component, selectItem, value, submittedValue,
              disabled || item.isDisabled(), true);
        }
      } else {
        encodeSelectItem(facesContext, writer, component, item,  value, submittedValue, disabled, false);
      }
    }

    writer.endElement(HtmlElements.TBODY);
    writer.startElement(HtmlElements.TFOOT);
    writer.startElement(HtmlElements.TR);
    writer.writeClassAttribute(TobagoClass.NO__ENTRIES, BootstrapClass.D_NONE);
    writer.startElement(HtmlElements.TD);
    writer.writeText("---");
    writer.endElement(HtmlElements.TD);
    writer.endElement(HtmlElements.TR);
    writer.endElement(HtmlElements.TFOOT);
    writer.endElement(HtmlElements.TABLE);
    writer.endElement(HtmlElements.DIV);
  }

  private void encodeSelectItem(FacesContext facesContext, TobagoResponseWriter writer, T component, SelectItem item,
        Object value, Object submittedValue, boolean disabled, boolean group) throws IOException {
    Object itemValue = item.getValue();
    // when using selectItem tag with a literal value: use the converted value
    if (itemValue instanceof String && value != null && !(value instanceof String)) {
      itemValue = ComponentUtils.getConvertedValue(facesContext, component, (String) itemValue);
    }
    final String formattedValue = getFormattedValue(facesContext, (T) component, itemValue);
    final boolean contains;
    if (submittedValue != null) {
      contains = submittedValue.equals(formattedValue);
    } else {
      contains = value != null && value.equals(itemValue);
    }
    writer.startElement(HtmlElements.TR);
    writer.writeAttribute(DataAttributes.VALUE, formattedValue, true);
    writer.writeClassAttribute(
        TobagoClass.SELECT__ITEM,
        contains ? BootstrapClass.TABLE_PRIMARY : null,
        disabled || item.isDisabled() ? TobagoClass.DISABLED : null,
        disabled || item.isDisabled() ? BootstrapClass.DISABLED : null);
    writer.writeAttribute(HtmlAttributes.TABINDEX, -1);
    writer.startElement(HtmlElements.TD);
    writer.writeAttribute(HtmlAttributes.VALUE, formattedValue, true);
    if (group) {
      writer.startElement(HtmlElements.SPAN);
    }
    final String label = item.getLabel();
    if (label != null) {
      writer.writeText(label);
    }
    if (group) {
      writer.endElement(HtmlElements.SPAN);
    }
    writer.endElement(HtmlElements.TD);
    writer.endElement(HtmlElements.TR);
  }

  @Override
  protected void encodeEndField(FacesContext facesContext, T component) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    encodeBehavior(writer, facesContext, component);
  }

  @Override
  public HtmlElements getComponentTag() {
    return HtmlElements.TOBAGO_SELECT_ONE_LIST;
  }

  @Override
  protected CssItem[] getComponentCss(final FacesContext facesContext, final T component) {
    final List<SelectItem> items = SelectItemUtils.getItemList(facesContext, component);
    final boolean disabled = !items.iterator().hasNext() || component.isDisabled() || component.isReadonly();

    List<CssItem> cssItems = new ArrayList<>();
    if (disabled) {
      cssItems.add(TobagoClass.DISABLED);
    }
    return cssItems.toArray(new CssItem[0]);
  }

  @Override
  protected void writeAdditionalAttributes(FacesContext facesContext, TobagoResponseWriter writer, T input)
      throws IOException {
    super.writeAdditionalAttributes(facesContext, writer, input);
    writer.writeAttribute(CustomAttributes.FILTER, input.getFilter(), true);
  }

  @Override
  protected String getFieldId(FacesContext facesContext, T component) {
    return component.getFieldId(facesContext);
  }
}
