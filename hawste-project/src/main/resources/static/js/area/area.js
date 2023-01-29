new Vue({
    el: '#main-container',
    data: function (){
        return {
            pageInfo:{
                current:1,
                size:5
            },
            params:{},//查询参数 id或者name
            settings:{
                //ztree的树的设置对象
                data:{
                    simpleData:{
                        enable:true,//开启简单数组格式
                        pIdKey:'parentId'  //设置父区域id字段
                    }
                },
                callback:{
                    onClick:this.onClick,
                    beforeEditName: this.beforeEditName,
                    beforeRemove: this.beforeRemove
                },
                edit: {  //节点图标编辑设置
                    enable: true ,  //设置为true  显示默认提供的修改和删除按钮
                    removeTitle: '删除区域',
                    renameTitle: '修改区域'
                },
                view:{
                    //鼠标移动到dom后，处理回调事件
                    addHoverDom:this.addHoverDom,
                    //鼠标离开后的回调
                    removeHoverDom: this.removeHoverDom
                }
            }
        }
    },
    methods: {
        select:function (current,size){
            let path = `/manager/area/select/${current}/${size}`
            axios({
                url:path,
                params:this.params
            }).then(r=>{
                if(r.data.code!==200){
                    layer.msg(r.data.msg);
                    return;
                }
                this.pageInfo=r.data.data;
            })
        },
        selectAll:function (){
          //清空查询条件
          this.params={};
          this.select(1,this.pageInfo.size);
        },
        selectName:function (){
            //根据区域名模糊查询
            this.params.id='';
            this.select(1,this.pageInfo.size);
        },
        doDelete:function (id){

            //1.弹出确认框
            layer.msg("是否删除?",{
                time:0,//取消默认的关闭窗口计时
                btn:['是','否'],
                //2.点击确认后，发送请求到后台接口，返回结果
                yes:index =>{
                    //index  是当前的信息框对象索引
                    //回调函数中默认this是windows对象，需要用箭头函数
                    axios({
                        url:'/manager/area/delete',
                        params:{id:id}
                    }).then(response=>{
                        //3.根据返回结果：进行提示  ，如果成功刷新数据
                        layer.close(index);//关闭msg
                        layer.msg(response.data.msg);
                        if(response.data.code==200){
                            this.initTree();//更新区域树
                            this.select(this.pageInfo.current,this.pageInfo.size);

                        }
                    })
                }
            })

        },
        toUpdate:function (id){
            axios({
                url:'/manager/area/selectOne',
                params: {id:id}
            }).then(respone=>{
                if(respone.data.code!=200){//发生异常的前端处理
                    layer.msg(respone.data.msg);
                    return;
                }
                //从数据库查询出id对应的app信息，再传递到子页面
                layer.obj = respone.data.data;
                let index = layer.open({
                    type:2,
                    title: false,
                    content:'/manager/area/area-update.html',
                    area: ['60%','80%'],
                    end: () => {
                        this.select(this.pageInfo.current,this.pageInfo.size);
                    }
                })
            })
        },
        exportExcel:function (){//文件下载
            location.href = '/manager/area/exportExcel';
        },
        importExport:function (e){//文件上传   e:事件对象
            /*
            *   1.绑定上传change事件
                2.事件处理函数中，获取事件对象，通过事件对象获取文件对象e.target.files[0]
                3.创建表单对象formDate
                4.放入文件对象formDate.appent("属性名",文件对象)，属性名与后端文件处理对象参数名一致
                5.设置请求头headers:{'content-type':multipart/form-data}和提交方式为post
            * */
            let target = e.target;//事件源  input
            // console.log(target);
            let file = target.files[0];//上传的文件对象
            let form = new FormData();//创建表单对象
            form.append("file",file);//绑定file对象，key要与后台MultipartFile参数名对应
            //ajax上传
            axios({
                url:'/manager/area/importExcel',
                method:"post",
                headers:{'content-type':'multipart/form-data'},//设置文件上传
                data:form
            }).then(r=>{
                if(r.data.code!==200){
                    layer.msg(r.data.msg);
                    return;
                }
                layer.msg(r.data.msg);
            })
        },
        initTree:function (){
            axios({
                url:'/manager/area/list'
            }).then(response=>{
                let nodes = response.data.data;
                //添加新的父节点
                nodes[nodes.length]={id:0,name:'区域列表',open:true}
                let treeObj = $.fn.zTree.init($('#treeMenu'),this.settings,nodes);
            })
        },
        onClick:function (event,treeId,treeNode){
            //与根据name查询互斥，需要先清空查询条件
            this.params={};
            this.params.id=treeNode.id;//设置查询条件，父区域id
            this.select(1,this.pageInfo.size);
        },
        beforeRemove: function (treeId,treeNode){  //删除节点前的回调事件处理  false不删除
            //删除成功后则应该  return true   失败则返回false
            this.doDelete(treeNode.id);
            return false;
        },
        beforeEditName: function (treeId,treeNode){  //修改节点名前的事件回调  false不触发事件回调
            //点击后触发跳转到更新页面，应付返回false阻止默认的事件行为
            this.toUpdate(treeNode.id);
            return false;
        },
        addHoverDom: function (treeId,treeNode){
            //自定义生成图标 可以查看网页源码中update图标
            //<span class="button remove" id="treeMenu_5_remove" title="删除区域" treenode_remove="" style=""></span>

            //1.创建标签dom对象       treeNode.tId+'_a'
            let aObj = $(`#${treeNode.tId}_a`)  ;//获取父节点  a  的  jquery对象
            //判断span是否存在，存在则返回
            let addObj = $(`#${treeNode.tId}_add`)  ;
            if(addObj.length>0){
                return ;//存在span 不要重复创建
            }

            let addHtml = `<span class="button add" id="${treeNode.tId}_add" title="添加区域" treenode_add="" style=""></span>`
            aObj.append(addHtml);//追加到父节点末尾
            //2.动态绑定点击事件
            if(addObj){
                $(`#${treeNode.tId}_add`).on("click",function (){
                    //调用新增方法
                    layer.msg("模拟弹出插入页面");
                })
            }
        },
        removeHoverDom:function (treeId,treeNode){
            //删除绑定事件对象，删除节点对象
            $(`#${treeNode.tId}_add`).unbind().remove();
        }
    },
    created: function () {
        this.select(1,this.pageInfo.size);
    },
    mounted:function (){
        this.initTree();

    }
})

