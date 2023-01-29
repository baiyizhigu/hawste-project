new Vue({
    el: '#main-container',
    data:{
        work:''
    },
    methods:{

    },
    created:function (){
        //在vue管理的data初始化后，获取父窗口的layer对象中绑定的数据传递过来
        this.work = parent.layer.obj;
    }
})

