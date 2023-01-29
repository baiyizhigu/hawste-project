new Vue({
    el: '#main-container',
    data:{
        qualification:''
    },
    methods:{
        /**
         * 访问更新接口，将qualification传递到后台更新数据库
         * 返回更新结果，关闭当前页面，在父窗口中显示春丽结果
         */
        doUpdate:function (check){
            //设置审核状态
            this.qualification.check = check;
            axios({
                'url':'/manager/qualification/saveOrUpdate',
                'method':'post',
                data:this.qualification
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
        this.qualification = parent.layer.obj;
    }
})

