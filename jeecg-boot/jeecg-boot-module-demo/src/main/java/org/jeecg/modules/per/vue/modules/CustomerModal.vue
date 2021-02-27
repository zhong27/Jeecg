<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row>
          <a-col :span="24">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerName', validatorRules.customerName]" placeholder="请输入客户名称" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['addres']" placeholder="请输入地址" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label=" 电话号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['number']" placeholder="请输入 电话号码" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="客户类型   " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerType']" placeholder="请输入客户类型   " ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="账户状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['accountStatus']" placeholder="请输入账户状态" ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: "CustomerModal",
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules: {
          customerName: {
            rules: [
              { required: true, message: '请输入客户名称!'},
            ]
          },
        },
        url: {
          add: "/per/customer/add",
          edit: "/per/customer/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'customerName','addres','number','customerType','accountStatus'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'customerName','addres','number','customerType','accountStatus'))
      },

      
    }
  }
</script>