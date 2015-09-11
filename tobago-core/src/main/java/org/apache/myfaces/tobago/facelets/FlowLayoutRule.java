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

package org.apache.myfaces.tobago.facelets;

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.UIFlowLayout;
import org.apache.myfaces.tobago.layout.Measure;

import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

public class FlowLayoutRule extends MetaRule {

  public static final FlowLayoutRule INSTANCE = new FlowLayoutRule();

  public Metadata applyRule(final String name, final TagAttribute attribute, final MetadataTarget metadataTarget) {
    if (metadataTarget.isTargetInstanceOf(UIFlowLayout.class)) {
      if (attribute.isLiteral()) {
        if (Attributes.MARGIN_LEFT.equals(name)) {
          return new MarginLeftMapper(attribute);
        }
        if (Attributes.MARGIN_TOP.equals(name)) {
          return new MarginTopMapper(attribute);
        }
        if (Attributes.MARGIN_RIGHT.equals(name)) {
          return new MarginRightMapper(attribute);
        }
        if (Attributes.MARGIN_BOTTOM.equals(name)) {
          return new MarginBottomMapper(attribute);
        }
        if (Attributes.MARGIN.equals(name)) {
          return new MarginMapper(attribute);
        }
      }
    }
    return null;
  }

  static final class MarginLeftMapper extends Metadata {
    private final TagAttribute attribute;

    MarginLeftMapper(final TagAttribute attribute) {
      this.attribute = attribute;
    }

    public void applyMetadata(final FaceletContext ctx, final Object instance) {
      final UIFlowLayout gridLayout = (UIFlowLayout) instance;
      gridLayout.setMarginLeft(Measure.valueOf(attribute.getValue()));
    }
  }

  static final class MarginTopMapper extends Metadata {
    private final TagAttribute attribute;

    MarginTopMapper(final TagAttribute attribute) {
      this.attribute = attribute;
    }

    public void applyMetadata(final FaceletContext ctx, final Object instance) {
      final UIFlowLayout gridLayout = (UIFlowLayout) instance;
      gridLayout.setMarginTop(Measure.valueOf(attribute.getValue()));
    }
  }

  static final class MarginRightMapper extends Metadata {
    private final TagAttribute attribute;

    MarginRightMapper(final TagAttribute attribute) {
      this.attribute = attribute;
    }

    public void applyMetadata(final FaceletContext ctx, final Object instance) {
      final UIFlowLayout gridLayout = (UIFlowLayout) instance;
      gridLayout.setMarginRight(Measure.valueOf(attribute.getValue()));
    }
  }

  static final class MarginBottomMapper extends Metadata {
    private final TagAttribute attribute;

    MarginBottomMapper(final TagAttribute attribute) {
      this.attribute = attribute;
    }

    public void applyMetadata(final FaceletContext ctx, final Object instance) {
      final UIFlowLayout gridLayout = (UIFlowLayout) instance;
      gridLayout.setMarginBottom(Measure.valueOf(attribute.getValue()));
    }
  }

  static final class MarginMapper extends Metadata {
    private final TagAttribute attribute;

    MarginMapper(final TagAttribute attribute) {
      this.attribute = attribute;
    }

    public void applyMetadata(final FaceletContext ctx, final Object instance) {
      final UIFlowLayout gridLayout = (UIFlowLayout) instance;
      gridLayout.setMargin(Measure.valueOf(attribute.getValue()));
    }
  }
}
