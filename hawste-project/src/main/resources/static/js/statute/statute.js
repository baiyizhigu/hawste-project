new Vue({
    el: '#main-container',
    data: {
        pageInfo:{
            current: 1,
            size: 5
        },
        params:{
            type:'' //设置默认
        }
    },
    methods: {
        //列表接口查询,传递分页参数，返回分页结果page对象
        select:function (current,size){
            axios({
                url:`/manager/statute/select/${current}/${size}`,
                params:this.params
            }).then(response =>{
                //箭头函数会将上下文中的this(vue对象)传递过来
                // console.log(response);
                //response.data->  ResultBean<Page>   ResultBean.data -> Page对象  列表数据->Page对象.records
                this.pageInfo=response.data.data;
            }).catch(function (error){
                console.log(error);
            })
        },
        selectAll:function (){//查询所有，不带条件
            this.params = {//初始化参数  ，不提交查询条件参数
                type:''
            }
            this.select(1,this.pageInfo.size);
        },
        toUpdate:function (id){
            axios({
                url:'/manager/statute/selectOne',
                params:{id:id}
            }).then(response=>{
                if(response.data.code!=200){
                    layer.msg(response.data.msg);
                    return;
                }
                //获取到后台接口查询对象,弹出更新页面，绑定到父页面layer，传递对象到更新页面
                layer.obj = response.data.data;
                layer.open({
                    type:2,//frame
                    title:false,
                    area:['60%','80%'],
                    content:'/manager/statute/statute-update.html',
                    end:()=>{
                        this.select(1,this.pageInfo.size);
                    }
                })

            })
        }
    },
    created: function () {
        this.select(1,this.pageInfo.size);
    }
})

