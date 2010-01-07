package org.bh.plugin.xmldataexchange;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Check box tree, which keeps track of checked selections
 * @author Winston Prakash
 */
public class XMLDataExchangeCheckBoxTree extends JTree{
    Vector checkedPaths = new Vector();
    JCheckBoxTreeRenderer checkBoxCellRenderer;
    
    public XMLDataExchangeCheckBoxTree(Object[] value) {
        super(value);
    }
    
    public XMLDataExchangeCheckBoxTree(Vector<?> value){
        super(value);
    }
    
    public XMLDataExchangeCheckBoxTree(Hashtable<?,?> value){
        super(value);
    }
    
    public XMLDataExchangeCheckBoxTree(TreeNode root){
        super(root);
    }
    
    public XMLDataExchangeCheckBoxTree(TreeNode root, boolean asksAllowsChildren){
        super(root, asksAllowsChildren);
    }
    public XMLDataExchangeCheckBoxTree(TreeModel newModel){
        super(newModel);
    }
    
    public XMLDataExchangeCheckBoxTree() {
        super();
        init();
    }
    
    private void init(){
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        checkBoxCellRenderer= new JCheckBoxTreeRenderer();
        setCellRenderer(checkBoxCellRenderer);
        addMouseListener(new JCheckBoxTreeMouseListener(this));
    }
    
    public void setChecked(TreePath path) {
        if(checkedPaths.contains(path)) {
            checkedPaths.remove(path);
            setParentsUnchecked(path);
            setDescendantsUnchecked(path);
        } else {
            checkedPaths.add(path);
            setParentsChecked(path);
            setDescendantsChecked(path);
        }
        setParentsChecked(path);
        repaintPath(path);
    }
    
    private void setDescendantsChecked(TreePath path) {
        if(!hasBeenExpanded(path)) {
            return;
        }
        Object component = path.getLastPathComponent();
        int childCount = getModel().getChildCount(component);
        for(int i = 0; i < childCount; i++) {
            Object childComponent = getModel().getChild(component, i);
            TreePath childComponentPath = path.pathByAddingChild(childComponent);
            if(!checkedPaths.contains(childComponentPath)) {
                checkedPaths.add(childComponentPath);
                repaintPath(childComponentPath);
            }
            setDescendantsChecked(childComponentPath);
        }
    }
    
    private void setDescendantsUnchecked(TreePath path) {
        if(hasBeenExpanded(path)) {
            Object cmp = path.getLastPathComponent();
            
            int component = getModel().getChildCount(cmp);
            for(int i = 0; i < component; i++) {
                Object childComponent = getModel().getChild(cmp, i);
                TreePath childComponentPath = path.pathByAddingChild(childComponent);
                if(checkedPaths.contains(childComponentPath)) {
                    checkedPaths.remove(childComponentPath);
                    repaintPath(childComponentPath);
                }
                setDescendantsUnchecked(childComponentPath);
            }
        }
    }
    
    private boolean isAnyChildChecked(TreePath path) {
        if ((path != null) || (checkedPaths != null)) {
            for(int i=0; i < checkedPaths.size(); i++) {
                TreePath checkedPath = (TreePath)checkedPaths.elementAt(i);
                if(path.isDescendant(checkedPath)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isParentChecked(TreePath path) {
        if ((path != null) || (checkedPaths != null)){
            if (checkedPaths == null) return false;
            TreePath parentPath = path.getParentPath();
            if(checkedPaths.contains(parentPath)) {
                return true;
            }
        }
        return false;
    }
    
    private void setParentsChecked(TreePath path) {
        TreePath parentPath = path.getParentPath();
        if(parentPath != null){
            boolean shouldAdd = true;
            Object component = parentPath.getLastPathComponent();
            int childCount = getModel().getChildCount(component);
            for(int i=0; i<childCount;i++) {
                Object childComponent = getModel().getChild(component, i);
                TreePath childPath = parentPath.pathByAddingChild(childComponent);
                if(!checkedPaths.contains(childPath)) {
                    shouldAdd = false;
                }
            }
            if(shouldAdd) {
                checkedPaths.add(parentPath);
            }
            repaintPath(parentPath);
            setParentsChecked(parentPath);
        }
    }
    
    private void setParentsUnchecked(TreePath path) {
        TreePath parentPath = path.getParentPath();
        if (parentPath != null){
            if(checkedPaths.contains(parentPath)) {
                checkedPaths.remove(parentPath);
            }
            repaintPath(parentPath);
            setParentsUnchecked(parentPath);
        }
    }
    
    public boolean isChecked(TreePath path) {
        return (checkedPaths.contains(path));
    }
    
    private void repaintPath(TreePath path) {
        Rectangle pathRect = getPathBounds(path);
        if(pathRect != null){
            repaint(pathRect.x, pathRect.y, pathRect.width, pathRect.height);
        }
    }
    
    private class JCheckBoxTreeRenderer extends DefaultTreeCellRenderer {
        private JPanel panel = new JPanel();
        public JCheckBox checkBox  = new JCheckBox();
        
        public JCheckBoxTreeRenderer() {
            super();
            panel.setLayout(new java.awt.BorderLayout(5, 0));
            checkBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            checkBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
            panel.add(checkBox, BorderLayout.WEST);
        }
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                boolean expanded, boolean leaf, int row, boolean hasFocus) {
            panel.setBackground(tree.getBackground());
            checkBox.setBackground(tree.getBackground());
            boolean checked=false;
            boolean anyChecked=false;
            if(!(checked=isChecked(getPathForRow(row)))){
                anyChecked = isAnyChildChecked(getPathForRow(row));
            }
            if(checked || anyChecked ){
                checkBox.setSelected(true);
            } else {
                checkBox.setSelected(false);
            }
            Component comp = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            panel.add(comp, BorderLayout.CENTER);
            return (panel);
        }
        
    }
    
    private class JCheckBoxTreeMouseListener extends MouseAdapter {
        JTree tree;
        public JCheckBoxTreeMouseListener(JTree tree) {
            this.tree = tree;
        }
        
        public void mousePressed(MouseEvent e) {
            int selRow = getRowForLocation(e.getX(), e.getY());
            if(selRow != -1) {
                if (!isRowSelected(selRow)) {
                    setSelectionRow(selRow);
                }
                Rectangle rect = getRowBounds(selRow);
                if(rect.contains(e.getX(),e.getY())) {
                    setChecked(getPathForRow(selRow));
                }
            }
        }
    }
}
