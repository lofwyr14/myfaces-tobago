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

package org.apache.myfaces.tobago.util;

import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.internal.component.AbstractUIMenu;

import javax.faces.component.UIComponent;

/**
 * Utility class to provide a type save way to get a specific facet from a components.
 */
public final class FacetUtils {

  private FacetUtils() {
  }

  /**
   * A type save utility to get the facet <code>contextMenu</code> from a component.
   */
  public static AbstractUIMenu getContextMenu(final UIComponent component) {
    return (AbstractUIMenu) ComponentUtils.getFacet(component, Facets.contextMenu);
  }

  public static void setContextMenu(final UIComponent component, final AbstractUIMenu menu) {
    ComponentUtils.setFacet(component, Facets.contextMenu, menu);
  }

  /**
   * A type save utility to get the facet <code>dropDownMenu</code> from a component.
   */
  public static AbstractUIMenu getDropDownMenu(final UIComponent component) {
    return (AbstractUIMenu) ComponentUtils.getFacet(component, Facets.dropDownMenu);
  }

  public static void setDropDownMenu(final UIComponent component, final AbstractUIMenu menu) {
    ComponentUtils.setFacet(component, Facets.dropDownMenu, menu);
  }
}
