package com.gec.hawsteproject.hawaste.utils;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

/**
 * 封装上传到minio对象桶中的api
 */
public class UploadOssUtil {

    /**
     * putObject()
     * 传入一个文件流，上传到存储桶中
     *
     */
    @SneakyThrows
    public static void uploadFile(MultipartFile upfile, String fileName, OssProperties ossProperties) {
        //1.获取连接对象
        MinioClient client = MinioClient.builder()
                .endpoint(ossProperties.getEndpoint(), ossProperties.getPort(), ossProperties.isSecure())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecreKey())
                .build();
        //2.检查桶是否存在
        boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(ossProperties.getBucketName()).build());

        if(!exists){
            //创建一个名为bucketName(uploads)的桶  用于存储文件
            client.makeBucket(MakeBucketArgs.builder().bucket(ossProperties.getBucketName()).build());
        }else{
            System.out.println("当前对象桶:"+ossProperties.getBucketName()+"已存在");
        }
        //3.使用上传对象api上传文件到存储桶
        client.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(ossProperties.getBucketName())//指定上传的桶位置
                        .object(fileName)  //指定桶中的对象名  文件夹+文件名
                        .stream(upfile.getInputStream(),upfile.getSize(),-1)//指定读取文件流  确定知道长度，partSize设置为-1
                        .build());
    }

}
