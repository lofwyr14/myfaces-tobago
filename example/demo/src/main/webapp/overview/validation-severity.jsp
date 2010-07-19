<%--
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
--%>
<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://myfaces.apache.org/tobago/extension" prefix="tx" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:overview>
  <jsp:body>

    <tc:box label="Validation and Severity">
      <f:facet name="layout">
        <tc:gridLayout rows="fixed;30px;fixed;*;fixed"/>
      </f:facet>

      <tc:messages/>

      <tc:out value="The validation process in JSF creates FacesMessages with different severities.
      These severities will be used to add an corresponding CSS class to the elements via the markup mechanism, e. g. tobago-label-markup-error"/>

      <tc:panel>
        <f:facet name="layout">
          <tc:gridLayout columns="1*;1*"/>
        </f:facet>
        <tx:in label="Fatal" value="something" validator="#{validationSeverity.addFatal}"/>
        <tc:out value="(Validator creates an fatal error)"/>
        <tx:in label="Error" value="something" validator="#{validationSeverity.addError}"/>
        <tc:out value="(Validator creates an error)"/>
        <tx:in label="Warn" value="something" validator="#{validationSeverity.addWarn}"/>
        <tc:out value="(Validator creates a warning)"/>
        <tx:in label="Info" value="something" validator="#{validationSeverity.addInfo}"/>
        <tc:out value="(Validator creates an info)"/>
      </tc:panel>

      <tc:cell/>

      <tc:panel>
        <f:facet name="layout">
          <tc:gridLayout columns="1*;100px"/>
        </f:facet>
        <tc:cell/>
        <tc:button label="#{overviewBundle.validation_submit}"/>
      </tc:panel>

    </tc:box>
  </jsp:body>
</layout:overview>
