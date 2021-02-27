<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['customerId', validatorRules.customerId]" :trigger-change="true" dictCode="per_customer,customer_name,id" placeholder="请选择客户名称" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="提单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['billNo']" placeholder="请输入提单编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="订单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['orderNo']" placeholder="请输入订单编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="产品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['productName', validatorRules.productName]" placeholder="请输入产品名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="产品大类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['productClass', validatorRules.productClass]" :trigger-change="true" dictCode="product_class" placeholder="请选择产品大类" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="总价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['total']" placeholder="请输入总价" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['consignee']" placeholder="请输入收货人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="收货地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['consigneeAddress']" placeholder="请输入收货地址"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="总重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['totalWeight']" placeholder="请输入总重量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'OrderBillForm',
    components: {
      JFormContainer,
      JDictSelectTag,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
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
          customerId: {
            rules: [
              { required: true, message: '请输入客户名称!'},
            ]
          },
          productName: {
            rules: [
              { required: true, message: '请输入产品名称!'},
            ]
          },
          productClass: {
            rules: [
              { required: true, message: '请输入产品大类!'},
            ]
          },
        },
        url: {
          add: "/ord/orderBill/add",
          edit: "/ord/orderBill/edit",
          queryById: "/ord/orderBill/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
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
          this.form.setFieldsValue(pick(this.model,'createTime','customerId','billNo','orderNo','productName','productClass','total','orderId','consignee','consigneeAddress','totalWeight'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
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
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'createTime','customerId','billNo','orderNo','productName','productClass','total','orderId','consignee','consigneeAddress','totalWeight'))
      },
    }
  }
</script>