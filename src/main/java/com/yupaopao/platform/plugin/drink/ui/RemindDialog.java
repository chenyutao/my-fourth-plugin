package com.yupaopao.platform.plugin.drink.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.yupaopao.platform.plugin.drink.service.PluginCoreService;
import com.yupaopao.platform.plugin.drink.util.SystemNotifyUtil;

import javax.swing.*;
import java.awt.event.*;

public class RemindDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public RemindDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        /*
          上面都是模板自带的逻辑
          下面才是该面板自己的逻辑
         */
        setSize(500, 200);//对话框的长宽
        setLocation(400,200);
    }

    private void onOK() {
        // 记录喝水时间
        PluginCoreService.getInstance().logDrinkAndPrepareNext();

        // 关闭窗口
        dispose();
    }

    private void onCancel() {
        // 关闭窗口
        dispose();
    }

}
