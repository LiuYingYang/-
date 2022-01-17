<template>
  <div class="pageRoll">
    <div class="shopMsg">
      <img :src="exImg" alt="" style="width:320px;height:667px" />
      <div class="shopMsg__right">
        <div class="shopMsg__right--wed">
          <div class="shopMsg__right--wed--text">商品页模板</div>
          <el-select v-model="modelValue" placeholder="请选择" style="width:300px;" @change="selectModelValue" size="small"
            :popper-append-to-body="false">
            <el-option v-for="item in modelOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-button style="margin-left:20px" type="text" plain size="small" @click="showTemModel = true">示例</el-button>
        </div>
        <div style="margin-top:36px">
          <RichEditor :text="detailText" ref="wEditor" onchange="changeValue"></RichEditor>
          <!-- <Ueditor
            :config="configEditor"
            :id="ue1"
            ref="ue"
            :defaultMsg="detailText"
          ></Ueditor> -->
        </div>
      </div>
    </div>
    <el-dialog width="50%" title="查看示例" :visible.sync="showTemModel">
      <img :src="temModel" style="width:100%;height:auto;" />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop, Ref } from "vue-property-decorator";
import RichEditor from "@/components/RichEditor.vue";
import Ueditor from "@/components/Ueditor.vue";
import storage from "@/libs/storage";
import exImg from "@/assets/images/ex_img.png";
import temModel from "@/assets/images/tem_model.png";

/**
 * 编辑商品信息
 */
@Component({
  components: {
    RichEditor,
    Ueditor,
  },
})
export default class NewGoodDetailInfo extends Vue {
  @Prop({
    type: Object,
    default() {
      return null;
    },
  })
  readonly goodDetail: any;

  /** 商品详情 */
  detailText = "";

  @Ref()
  readonly wEditor: HTMLFormElement;

  ue1 = "1";

  configEditor = {};

  formModel = {} as any;

  exImg: string = exImg;

  temModel: string = temModel;

  modelValue = 1;

  /** 详情模板 */
  modelOptions = [
    {
      label: "规格折叠",
      value: 1,
    },
    {
      label: "规格展开",
      value: 0,
    },
  ];

  /** 显示示例 */
  showTemModel = false;

  selectModelValue(e) {
    this.modelValue = e;
  }

  getDetailHtml() {
    return this.wEditor.getHtml();
    // return (this.$refs.ue as any).getUEContent();
  }

  changeValue() {
    console.log("sss");
  }

  mounted() {
    this.getModel();
    this.setFormModel();
  }

  getModel() {
    if (!this.goodDetail) {
      this.formModel = this.$parent.goodFormModel;
    }
  }

  getNewForm() {
    return {
      detail: this.getDetailHtml(),
      openSpecs: Boolean(this.modelValue),
    };
  }

  setFormModel() {
    let goodDetail = this.goodDetail;
    if (storage.get("allFoorm") !== null) {
      if (Object.keys(storage.get("allFoorm")).length !== 0) {
        goodDetail = storage.get("allFoorm");
      }
    }
    if (goodDetail) {
      this.modelValue = goodDetail.openSpecs ? 1 : 0;
      this.detailText = goodDetail.detail;
    } else {
      this.modelValue = 1;
      this.detailText = "";
    }
  }

  async getFormModel() {
    return {
      /** 商品详情 */
      detail: this.getDetailHtml(),
      /** 规格是否展开 */
      openSpecs: Boolean(this.modelValue),
    };
  }
}
</script>

<style scoped>
.shopMsg {
  display: flex;
}
.shopMsg__right {
  flex: 1;
  overflow: hidden;
}
</style>
