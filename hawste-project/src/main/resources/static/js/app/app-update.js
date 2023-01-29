new Vue({
    el: '#main-container',
    data:{
       app:''
    },
    methods:{
        /**
         * 访问更新接口，将app传递到后台更新数据库
         * 返回更新结果，关闭当前页面，在父窗口中显示春丽结果
         */
        doUpdate:function (){
            axios({
                'url':'/manager/app/saveOrUpdate',
                'method':'post',
                data:this.app
            }).then(response=>{
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
        this.app = parent.layer.app;
    }
})

