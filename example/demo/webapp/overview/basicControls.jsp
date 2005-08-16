<%--
 * Copyright 2002-2005 atanion GmbH.
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
--%>
<%@ taglib uri="http://www.atanion.com/tobago/component" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:overview>
  <jsp:body>
    <t:panel >
      <f:facet name="layout">
        <t:gridLayout border="0" rows="20px;fixed;1*;20px;fixed;1*;20px;fixed" />
  <%--      <t:gridLayout border="0" rows="1*;6*;1*;1*;6*;1*;1*;6*" />--%>
      </f:facet>

  <%--row    --%>
      <t:out escape="false" value="#{overviewBundle.basic_text_input}" />

  <%--row    --%>
      <t:box label="#{overviewBundle.basicControls_sampleInputTitle}" >
        <f:facet name="layout">
          <t:gridLayout columns="1*;1*"  border="0" />
        </f:facet>

        <t:panel >
          <f:facet name="layout">
            <t:gridLayout />
          </f:facet>
          <t:in value="#{overviewController.basicInput}"
              label="#{overviewBundle.basic_textboxLabel}" />
          <t:date value="#{overviewController.basicDate}"
              label="#{overviewBundle.basic_dateLabel}">
            <f:convertDateTime pattern="dd.MM.yyyy" />
          </t:date>
        </t:panel>

        <t:textarea value="#{overviewController.basicArea}"
          label="#{overviewBundle.basic_textareaLabel}" />
      </t:box>

  <%--row    --%>
      <t:cell /> <%-- spacer--%>

  <%--row    --%>
      <t:out escape="false" value="#{overviewBundle.basic_text_select}" />

  <%--row    --%>
      <t:box label="#{overviewBundle.basicControls_sampleSelectTitle}" >
        <f:facet name="layout">
          <t:gridLayout columns="1*;1*"  border="0"/>
        </f:facet>
        <t:selectOneRadio value="#{overviewController.radioValue}" id="rg0">
          <f:facet name="label"><t:label value="#{bundle.radiogroup_0}" /></f:facet>
          <f:selectItems value="#{overviewController.items}" id="items0" />
        </t:selectOneRadio>

        <t:panel >
          <f:facet name="layout">
            <t:gridLayout rows="fixed;fixed"/>
          </f:facet>
          <t:selectManyCheckbox value="#{overviewController.multiValue}" id="cbg0" renderRange="1-2" >
            <f:selectItems value="#{overviewController.items}" id="itemsg0" />
          </t:selectManyCheckbox>
          <t:selectOneChoice value="#{overviewController.singleValue}">
            <f:selectItems value="#{overviewController.items}" />
          </t:selectOneChoice>
        </t:panel>
      </t:box>
  <%--row    --%>
      <t:cell /> <%-- spacer--%>

  <%--row    --%>
      <t:out escape="false" value="#{overviewBundle.basic_text_action}" />

  <%--row    --%>
      <t:box label="#{overviewBundle.basicControls_sampleLinkTitle}" >
        <f:facet name="layout">
          <t:gridLayout columns="1*;1*;1*" border="0"/>
        </f:facet>
        <t:link id="link" action="overview/basicControls"
            actionListener="#{overviewController.click}"
            label="#{overviewBundle.basic_linkAction}" />
        <t:link id="image" action="overview/basicControls"
            actionListener="#{overviewController.click}"
            image="image/image_button.gif" />
        <t:button id="button" action="overview/basicControls"
            actionListener="#{overviewController.click}"
            width="100px"  label="#{overviewBundle.basic_buttonAction}" />
        <t:cell spanX="3">
          <t:in value="#{overviewController.lastAction}" readonly="true"
            label="#{overviewBundle.basic_lastActionLabel}" />
        </t:cell>
      </t:box>
    </t:panel>
  </jsp:body>
</layout:overview>
