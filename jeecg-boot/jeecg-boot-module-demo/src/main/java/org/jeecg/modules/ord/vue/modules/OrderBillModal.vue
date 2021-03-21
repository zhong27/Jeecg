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
            <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['customerId']" dict="man_customer,customer_name,id"  />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="提单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['billNo']" placeholder="请输入提单编号" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="订单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['orderNo']" placeholder="请输入订单编号" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['business']" dict="man_business,name,id"  />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="总重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['totalWeight']" placeholder="请输入总重量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="总价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['total']" placeholder="请输入总价" style="width: 100%" />
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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "OrderBillModal",
    components: { 
      JSearchSelectTag,
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
        },
        url: {
          add: "/ord/orderBill/add",
          edit: "/ord/orderBill/edit",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','customerId','billNo','orderNo','business','totalWeight','total'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','customerId','billNo','orderNo','business','totalWeight','total'))
      },

      
    }
  }
</script>