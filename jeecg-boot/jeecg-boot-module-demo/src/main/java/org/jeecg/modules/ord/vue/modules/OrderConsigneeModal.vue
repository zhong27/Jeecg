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
            <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入姓名" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['sex', validatorRules.sex]" :trigger-change="true" dictCode="sex" placeholder="请选择性别" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['age']" placeholder="请输入年龄" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="电话号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['phone', validatorRules.phone]" placeholder="请输入电话号码" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所在地区" :labelCol="labelCol" :wrapperCol="wrapperCol">
		      <j-area-linkage type="cascader" v-decorator="['address']" placeholder="请输入省市区" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['addressDet']" rows="4" placeholder="请输入详细地址" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="电子邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['email', validatorRules.email]" placeholder="请输入电子邮箱" ></a-input>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'

  export default {
    name: "OrderConsigneeModal",
    components: {
      JDictSelectTag,
      JAreaLinkage,
    },
    props:{
      mainId:{
        type:String,
        required:false,
        default:''
      }
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
          name: {
            rules: [
              { required: true, message: '请输入姓名!'},
            ]
          },
          sex: {
            rules: [
              { required: true, message: '请输入性别!'},
            ]
          },
          phone: {
            rules: [
              { required: true, message: '请输入电话号码!'},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          email: {
            rules: [
              { required: false},
              { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
            ]
          },
        },
        url: {
          add: "/ord/orderBill/addOrderConsignee",
          edit: "/ord/orderBill/editOrderConsignee",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','delFlag','name','sex','age','phone','address','addressDet','email','billId'))
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
            formData['billId'] = this.mainId
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','delFlag','name','sex','age','phone','address','addressDet','email','billId'))
      },


    }
  }
</script>
