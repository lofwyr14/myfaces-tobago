package org.apache.myfaces.tobago.internal.component;

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

import org.apache.myfaces.tobago.component.SupportsMarkup;
import org.apache.myfaces.tobago.component.TreeModelBuilder;
import org.apache.myfaces.tobago.config.Configurable;
import org.apache.myfaces.tobago.event.TreeExpansionListener;
import org.apache.myfaces.tobago.event.TreeMarkedListener;
import org.apache.myfaces.tobago.internal.util.Deprecation;
import org.apache.myfaces.tobago.model.MixedTreeModel;
import org.apache.myfaces.tobago.model.TreeDataModel;
import org.apache.myfaces.tobago.model.TreePath;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIColumn;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.model.DataModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public abstract class AbstractUITreeNode
    extends UIColumn implements SupportsMarkup, TreeModelBuilder, Configurable {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractUITreeNode.class);

  @Override
  public void encodeBegin(FacesContext facesContext) throws IOException {
    final AbstractUIData data = ComponentUtils.findAncestor(this, AbstractUIData.class);
    DataModel model = data.getDataModel();
    if (model instanceof TreeDataModel) {
      final TreeDataModel treeDataModel = (TreeDataModel) model;
      treeDataModel.setRowClientId(getClientId(facesContext));
    }

    super.encodeBegin(facesContext);
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void buildTreeModelBegin(FacesContext facesContext, MixedTreeModel model) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void buildTreeModelChildren(FacesContext facesContext, MixedTreeModel model) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void buildTreeModelEnd(FacesContext facesContext, MixedTreeModel model) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  /**
   * Finds the value of the current node via the var attribute of the tree data.
   * Returns null if it will be called not inside of {@link AbstractUITreeData}
   */
  // todo: make independent from impl.: DefaultMutableTreeNode
/*  private DefaultMutableTreeNode getDataNode() {
    UIComponent component = this;
    while (component != null) {
      if (component instanceof AbstractUITreeData) {
        final AbstractUITreeData data = (AbstractUITreeData) component;
        final Object currentNode
            = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(data.getVar());
        return (DefaultMutableTreeNode) currentNode;
      } else if (component instanceof AbstractUITree) {
        return null;
      }
      component = component.getParent();
    }
    return null;
  }*/

  protected DefaultMutableTreeNode getRowData() {
    final UIData data = ComponentUtils.findAncestor(this, UIData.class);
    final Object rowData = data.getRowData();
    return (DefaultMutableTreeNode) rowData;
  }
  
  /**
   * Returns the level of the tree node inside of the virtual tree. The root node has level 0.
   * The children of the root note have level 1, and so on. 
   */
  public int getLevel() {
    final DefaultMutableTreeNode node = getRowData();
    return node.getLevel();
  }

  public String nodeStateId(FacesContext facesContext) {
    final String clientId = getClientId(facesContext);
    final UIData data = ComponentUtils.findAncestor(this, UIData.class);
    String dataId = data.getClientId(facesContext);
    return clientId.substring(dataId.length() + 1);
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public int getDepth() {
    Deprecation.LOG.error("Doesn't work anymore.");
    return 1;
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void setDepth(int depth) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  public boolean isFolder() {
    final DefaultMutableTreeNode rowData = getRowData();
    return !rowData.isLeaf();
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void setFolder(boolean folder) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  public TreePath getPath() {
    return new TreePath(getRowData());
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void setPath(TreePath path) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  public List<Boolean> getJunctions() {
    final DefaultMutableTreeNode node = getRowData();
    List<Boolean> junctions = new Stack<Boolean>();
    for (TreeNode ancestor : node.getPath()) {
      junctions.add(((DefaultMutableTreeNode) ancestor).getNextSibling() != null);
    }
    return junctions;
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void setJunctions(List<Boolean> junctions) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public boolean isHasNextSibling() {
    return getRowData().getNextSibling() != null;
  }

  /**
   * @deprecated since 1.6.0
   */
  @Deprecated
  public void setHasNextSibling(boolean hasNextSibling) {
    Deprecation.LOG.error("Doesn't work anymore.");
  }

  public abstract MethodBinding getTreeExpansionListener();

  public abstract void setTreeExpansionListener(MethodBinding treeExpansionListener);

  public void addTreeExpansionListener(TreeExpansionListener listener) {
    addFacesListener(listener);
  }

  public TreeExpansionListener[] getTreeExpansionListeners() {
    return (TreeExpansionListener[]) getFacesListeners(TreeExpansionListener.class);
  }

  public void removeStateChangeListener(TreeExpansionListener listener) {
    removeFacesListener(listener);
  }

  public abstract MethodBinding getTreeMarkedListener();

  public abstract void setTreeMarkedListener(MethodBinding treeMarkedListener);

  public void addTreeMarkedListener(TreeMarkedListener listener) {
    addFacesListener(listener);
  }

  public TreeMarkedListener[] getTreeMarkedListeners() {
    return (TreeMarkedListener[]) getFacesListeners(TreeMarkedListener.class);
  }

  public void removeStateChangeListener(TreeMarkedListener listener) {
    removeFacesListener(listener);
  }

  public abstract boolean isMarked();

  public abstract void setMarked(boolean b);

  public abstract boolean isExpanded();

  public abstract void setExpanded(boolean expanded);

  public abstract boolean isSelected();

  public abstract void setSelected(boolean selected);
}
