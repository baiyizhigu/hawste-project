new Vue({
    el: '#main-container',
    data:{
        area:''
    },
    methods:{
        /**
         * 访问更新接口，将 area 传递到后台更新数据库
         * 返回更新结果，关闭当前页面，在父窗口中显示春丽结果
         */
        doUpdate:function (){
            axios({
                'url':'/manager/area/saveOrUpdate',
                'method':'post',
                data:this.area
            }).then(response=>{
                parent.layer.msg(response.data.msg);
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);//关闭当前子窗口
            }).catch(e=>{
                layer.msg(e.message);
            })
        },
        toSelect:function (){
            //从数据库查询出对应的area信息，再传递到子页面
            layer.obj = this.area;
            let index = layer.open({
                type:2,
                title: false,
                content:'/manager/area/area-select.html',
                area: ['100%','100%'],
                end: () => {
                    //获取到子窗口返回的新父区域对象，进行设置新parent_id和parent_ids和parent_name
                    if(layer.selectObj!=undefined){
                        let parentArea = layer.selectObj;
                        this.area.parentName= parentArea.name;
                        this.area.parentId = parentArea.id;
                        //更新parent_ids  子级的parent_ids:父parent_ids+父id
                        this.area.parentIds = parentArea.parentIds+parentArea.id+","
                    }
                }
            })
        },
        toModules:function () {
            let index = layer.open({
                type: 2,
                title: false,
                content: '/manager/modules/font-awesome.html',
                area: ['100%', '100%'],
                end: () => {
                    this.area.icon = layer.icon; //给area的icon图标赋值
                }
            })
        }
    },
    created:function (){
        //在vue管理的data初始化后，获取父窗口的layer对象中绑定的数据传递过来
        this.area  = parent.layer.obj;
    }
})

