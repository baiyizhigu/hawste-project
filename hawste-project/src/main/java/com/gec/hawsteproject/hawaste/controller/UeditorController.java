package com.gec.hawsteproject.hawaste.controller;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.gec.hawsteproject.hawaste.utils.OssProperties;
import com.gec.hawsteproject.hawaste.utils.UploadOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class UeditorController {

    @Autowired
    OssProperties ossProperties;

    /**
     * 后端处理富文本统一请求接口
     * @param action        根据前端传递的参数来判断是处理哪种逻辑
     * action=config:表示访问统一接口，返回服务端的配置json
     * 根据服务端配置对应设置前端的请求处理接口参数
     * action=uploadimage:访问统一接口，进行文件上传，并返回处理结果json
     * 。。。
     * @param upfile 由springmvc自动封装的文件处理对象
     * @return
     */
    @RequestMapping("/ueditor/exec")
    public String exec(String action, HttpServletRequest request, MultipartFile upfile){
        String result = null;
        String rootPath = request.getServletContext().getRealPath( "/" );
        //前端富文本组件初始化，会自动发送请求到该接口，并且传递参数
        // action=config:表示访问统一接口，返回服务端的配置json
        if("config".equals(action)){
            //读取当前resources下的config.json，并且返回


            result = new ActionEnter( request, rootPath ).exec();
        }else if("uploadimage".equals(action)){
            //文件上传操作
            /**
             * 1.封装对象存储工具
             * 2.服务器统一接口，添加MultipartFile对象参数，参数名与后台配置中对应
             * 3.文件上传处理
             * 4.返回响应字符串
             */
            ConfigManager configManager = ConfigManager.getInstance(rootPath, request.getContextPath(), request.getRequestURI());
            //获取单图片上传的配置参数
            Map<String, Object> config = configManager.getConfig(ActionMap.UPLOAD_IMAGE);

            //判断上传文件格式
            String originalFilename = upfile.getOriginalFilename();
            String suffix = FileType.getSuffixByFilename(originalFilename);//获取文件类型

            if(!validType(suffix, (String[]) config.get("allowFiles"))){
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE).toJSONString();
            }

            //保存路径处理
            originalFilename = originalFilename.substring(0,originalFilename.length()-suffix.length());//获取去掉后缀后的文件名
            //获取config.json中的配置的imagePathFormat路径
            String savePath = (String) config.get("savePath");
            //处理保存路径
            savePath = savePath + suffix;
            savePath = PathFormat.parse(savePath,originalFilename);

            UploadOssUtil.uploadFile(upfile,savePath,ossProperties);//上传

            //返回统一响应json
            BaseState state = new BaseState(true);
            state.putInfo("size",upfile.getSize());
            state.putInfo("title",originalFilename+suffix);
            //前端savePath自动拼接了配置文件config.json中的前缀imageUrlPrefix
            state.putInfo("url",savePath);
            state.putInfo("type",suffix);
            state.putInfo("original",originalFilename+suffix);
            result = state.toJSONString();
        }

        return result;
    }

    /**
     * 校验文件格式
     * @param type
     * @param allowTypes
     * @return
     */
    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
