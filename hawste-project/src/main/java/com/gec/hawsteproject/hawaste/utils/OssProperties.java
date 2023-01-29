package com.gec.hawsteproject.hawaste.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OSS配置组件，从配置文件中读取配置信息，注入到类的属性中
 */
@Data
@ConfigurationProperties(prefix = "minio")
@Component   //设置spring管理的bean组件
public class OssProperties {

    String endpoint;//存储节点ip

    int port; //web管理服务访问端口

    String bucketName;  // uploads   #对象存储桶名字

    boolean secure;// false         #false为http true为https

    String accessKey;// minioadmin # minio服务登录账户密码

    String secreKey;// minioadmin
}
