package com.lipeng.window.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.lipeng.consts.GlobalConstants;
import com.lipeng.util.ZkUtil;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 节点选中时，加载子节点数据
 * @author lipeng 2023/12/21
 */
public class AddChildSelectionListener implements TreeSelectionListener {
    private final JTree dataTree;

    public AddChildSelectionListener(JTree dataTree) {
        this.dataTree = dataTree;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        String host = String.valueOf(dataTree.getModel().getRoot());
        if (CollectionUtil.isEmpty(ZkUtil.getAllHost()) || GlobalConstants.isCloseMsg(host)) {
            return;
        }

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) dataTree.getLastSelectedPathComponent();
        if (selectedNode != null) {
            // 已有数据则返回
            if (selectedNode.children().hasMoreElements()) {
                return;
            }

            // 加载子节点，同时加载两级
            ZkUtil.initChild(host, selectedNode, 0);

            // 通知树模型节点结构已更改
            dataTree.updateUI();
        }
    }


}
