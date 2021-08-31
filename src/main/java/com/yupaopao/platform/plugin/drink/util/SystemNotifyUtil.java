package com.yupaopao.platform.plugin.drink.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

public class SystemNotifyUtil {

    public static void msgNotify(String body){
        // 系统模板的通知功能
        NotificationGroup notificationGroup = new NotificationGroup("RestAndDrinkNotification", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(body, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }

}
