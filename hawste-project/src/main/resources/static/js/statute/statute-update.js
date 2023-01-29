new Vue({
    el: '#main-container',
    data:{
        state:'',
        ueditorConfig:{
            UEDITOR_HOME_URL: "/ueditor/",  //默认ueditor静态资源路径前缀
            // 服务器统一请求接口路径
            serverUrl: '/ueditor/exec',
            maximumWords:300000       //允许的最大字符数
        }
    },
    methods:{
        /**
         * 访问更新接口，将statute传递到后台更新数据库
         * 返回更新结果，关闭当前页面，在父窗口中显示春丽结果
         */
        doUpdate:function (){
            axios({
                'url':'/manager/statute/saveOrUpdate',
                'method':'post',
                data:this.state
            }).then(response=>{
                if(response.data.code!=200){
                    layer.msg(response.data.msg);
                    return;
                }
                parent.layer.msg(response.data.msg);
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);//关闭当前子窗口
            }).catch(e=>{
                layer.msg(e.message);
            })
        }
    },
    created:function (){
        //在vue管理的data初始化后，获取父窗口的layer对象中绑定的数据传递过来
        this.state = parent.layer.obj;
    },
    mounted:function (){//当$el的内容挂载到dom节点后执行
        jeDate({
            dateCell: '#modifydate',
            format: 'YYYY-MM-DD',
            zIndex: 999999999,
            choosefun:(val) => {//选中日期后的回调
                //查看源文件可以发现config配置信息中有选中日期后的回调
                //当选中日期后，让val赋值给vue管理的state对象
                //jeDate插件并不是由vue双向绑定实现的，值变化无法直接写入到vue的state对象上
                this.state.pubDate=val;
            }
        });
    },
    /**
     * components:用于对vue组件属性管理，引入一些已经封装的vue组件对象
     * 由vue-ueditor-wrap.min.js创建该组件对象
     */
    components:{
        VueUeditorWrap
    }

})