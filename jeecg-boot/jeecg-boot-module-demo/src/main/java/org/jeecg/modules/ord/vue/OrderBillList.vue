<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户">
              <j-search-select-tag placeholder="请选择客户" v-model="queryParam.customerId" dict="man_customer,customer_name,id"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="业务员">
              <j-search-select-tag placeholder="请选择业务员" v-model="queryParam.business" dict="man_business,name,id"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('提单信息管理')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:'radio'}"
        :customRow="clickThenSelect"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="司机信息" key="1" >
        <OrderDriverList :mainId="selectedMainId" />
      </a-tab-pane>
      <a-tab-pane tab="提单材料明细" key="2" forceRender>
        <OrderMaterList :mainId="selectedMainId" />
      </a-tab-pane>
      <a-tab-pane tab="收料人信息" key="3" forceRender>
        <OrderConsigneeList :mainId="selectedMainId" />
      </a-tab-pane>
    </a-tabs>

    <orderBill-modal ref="modalForm" @ok="modalFormOk"></orderBill-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import OrderBillModal from './modules/OrderBillModal'
  import { getAction } from '@/api/manage'
  import OrderDriverList from './OrderDriverList'
  import OrderMaterList from './OrderMaterList'
  import OrderConsigneeList from './OrderConsigneeList'
  import {initDictOptions,filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import '@/assets/less/TableExpand.less'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: "OrderBillList",
    mixins:[JeecgListMixin],
    components: {
      JSearchSelectTag,
      OrderDriverList,
      OrderMaterList,
      OrderConsigneeList,
      OrderBillModal,
      JSuperQuery
    },
    data () {
      return {
        description: '提单信息管理管理页面',
        // 表头
        columns: [
          {
            title:'创建人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'创建日期',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title:'客户',
            align:"center",
            dataIndex: 'customerId_dictText',
          },
          {
            title:'提单编号',
            align:"center",
            dataIndex: 'billNo'
          },
          {
            title:'订单编号',
            align:"center",
            dataIndex: 'orderNo'
          },
          {
            title:'业务员',
            align:"center",
            dataIndex: 'business_dictText',
          },
          {
            title:'总重量',
            align:"center",
            dataIndex: 'totalWeight'
          },
          {
            title:'总价',
            align:"center",
            dataIndex: 'total'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/ord/orderBill/list",
          delete: "/ord/orderBill/delete",
          deleteBatch: "/ord/orderBill/deleteBatch",
          exportXlsUrl: "/ord/orderBill/exportXls",
          importExcelUrl: "ord/orderBill/importExcel",
        },
        dictOptions:{
        },
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '50'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        selectedMainId:'',
        superFieldList:[],
      }
    },
    created() {
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('man_customer,customer_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'customerId', res.result)
          }
        })
        initDictOptions('man_business,name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'business', res.result)
          }
        })
      },
      clickThenSelect(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        }
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.selectedMainId=''
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedMainId=selectedRowKeys[0]
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.onClearSelected()
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'createBy',text:'创建人',dictCode:''})
        fieldList.push({type:'datetime',value:'createTime',text:'创建日期'})
        fieldList.push({type:'sel_search',value:'customerId',text:'客户',dictTable:'man_customer', dictText:'customer_name', dictCode:'id'})
        fieldList.push({type:'string',value:'billNo',text:'提单编号',dictCode:''})
        fieldList.push({type:'string',value:'orderNo',text:'订单编号',dictCode:''})
        fieldList.push({type:'sel_search',value:'business',text:'业务员',dictTable:'man_business', dictText:'name', dictCode:'id'})
        fieldList.push({type:'BigDecimal',value:'totalWeight',text:'总重量',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'total',text:'总价',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>