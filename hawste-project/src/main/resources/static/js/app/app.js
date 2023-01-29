new Vue({
    el: '#main-container',
    data:{
        pageInfo:{
            /*pageNum:1,  //设置默认值
            pageSize:5*/
            current:1,
            size:5
        },
        app:{
            platform:0,
            forceUpdate:0 //设置默认选中
        }, //用于保存操作的模型对象  作为模型对象，提供默认值 ，即使使用空对象或者空字符串都可以
        active:false  //是否激活状态  active为false表示显示列表  true表示显示新增表单
    },
    methods:{
        //列表接口查询,传递分页参数，返回分页结果page对象
        select:function (current,size){
            axios({
                url:'/manager/app/select',
                params:{
                    current:current,
                    size:size
                }
            }).then(response =>{
                //箭头函数会将上下文中的this(vue对象)传递过来
                // console.log(response);
                //response.data->  ResultBean<Page>   ResultBean.data -> Page对象  列表数据->Page对象.records
                this.pageInfo=response.data.data;
            }).catch(function (error){
                console.log(error);
            })
        },
        toUpdate:function (id){
            axios({
                url:'/manager/app/selectOne',
                params: {id:id}
            }).then(respone=>{
                if(respone.data.code!=200){//发生异常的前端处理
                    layer.msg(respone.data.msg);
                    return;
                }
                //从数据库查询出id对应的app信息，再传递到子页面
                layer.app = respone.data.data;
                console.log(layer);
                let index = layer.open({
                    type:2,
                    title: false,
                    content:'/manager/app/app-update.html',
                    area: ['60%','80%'],
                    end: () => {
                        this.select(this.pageInfo.current,this.pageInfo.size);
                    }
                })
            }).catch(function (error){
                //msg方法参数只能是字符串
                layer.msg(error.message);
            })

        },
        save:function (){
            axios({
                'url':'/manager/app/saveOrUpdate',
                'method':'post',
                data:this.app
            }).then(response=>{
               if(response.data.code==200){//失败则不切换列表div
                   //成功操作:1.切换到列表div  2.更新列表   3.清空新增表单内容
                   this.active = false;
                   this.select(this.pageInfo.current,this.pageInfo.size);
                   this.app = {
                       platform:0,
                       forceUpdate:0 //设置默认选中
                   };//清空模型对象的属性值
               }
                layer.msg(response.data.msg);
            })
        },
        changeActive:function (){//修改激活状态
            this.active = !this.active;
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
                        url:'/manager/app/delete',
                        params:{id:id}
                    }).then(response=>{
                        //3.根据返回结果：进行提示  ，如果成功刷新数据
                        layer.close(index);//关闭msg
                        layer.msg(response.data.msg);
                        if(response.data.code==200){
                            this.select(this.pageInfo.current,this.pageInfo.size);
                        }
                    })
                }
            })
        }
    },
    created:function (){
        this.select(this.pageInfo.current,this.pageInfo.size);
    }
})

