new Vue({
    el: '#main-container',
    data: function(){
        // console.log(this);//this是vue对象
        return {
            setting:{//ztree的配置对象
                data:{//ztree的数据配置对象
                    simpleData: {//设置简单节点数组配置
                        enable:true,   //默认false 设置true表示节点数组可以是一位结构
                        pIdKey: 'parentId'  //设置自定义的父id属性名
                    }
                },
                callback:{//回调事件处理
                    onClick: this.onClick
                }
            },
            area:{}
        }
    },
    methods: {
        /**
         * ztree开发流程：
         * 1.声明配置setting对象
         * 2.声明配置nodes对象
         * 3.根据配置和节点对象，创建ztree对象
         * $.fn.zTree.init(需要挂载树的dom节点对象，配置对象，节点数组对象)
         * 树初始化方法
         */
        initTree:function (){
            axios({
                url:'/manager/area/list'
            }).then(response=>{
                let nodes = response.data.data;
                //添加新的父节点
                nodes[nodes.length]={id:0,name:'区域列表',open:true}
                let treeObj = $.fn.zTree.init($('#select-tree'),this.setting,nodes);
            })
        },
        onClick:function (event,treeId,treeNode){
            //点击某个区域，设置为新的父区域
            //不能点击原父区域
            if(treeNode.id==this.area.parentId||treeNode.id==this.area.id){
                layer.msg("请选正确父区域");
                return;
            }
            parent.layer.selectObj = treeNode;//将新的父节点传递给父layer的selectObj
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    },
    created: function () {
        this.area=parent.layer.obj;
    },//注意ul节点需要挂载到dom后，才能找到该节点  使用mounted生命周期方法
    mounted:function (){
        this.initTree();
    }
})

