<!--
 * @description: 抽离开源版本
 * @Author: chuyinlong
 * @Date: 2021-08-20 17:29:06
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-09-17 15:56:22
-->
<template>
  <div style="width: 100%; z-index: 1; position: relative" ref="editor"></div>
</template>

<script>
import wEditor from "wangeditor";
import { upLoad } from "@/api/index";
export default {
  name: "wangeditor",
  props: ["text"],
  data() {
    return {
      editor: null,
    };
  },
  watch: {
    text(val) {
      val = val.replace(
        /\<img/g,
        '<img style="width:100%;height:auto;display:block"',
      );
      if (this.editor) {
        this.editor.txt.html(`<p>${val}</p>`);
      }
    },
  },
  mounted() {
    this.initEditor(this.text); // 初始化富文本编辑器
  },
  methods: {
    // 初始化富文本编辑器
    initEditor(editorHtml = "") {
      /** 服务详情富文本 */
      this.editor = new wEditor(this.$refs.editor);
      this.editor.config.pasteFilterStyle = true;
      // this.editor.config.uploadImgShowBase64 = true // 使用 base64 保存图片
      this.editor.config.uploadImgMaxSize = 5 * 1024 * 1024; // 图片大小限制
      this.editor.config.uploadImgMaxLength = 5; // 限制一次最多上传 5 张图片
      this.editor.config.uploadImgServer = "/upload"; // 配置服务器端地址
      this.editor.config.customUploadImg = (files, insert) => {
        // files 是 input 中选中的文件列表
        this.uploadFiles(files).then((res) => {
          res.forEach((item) => {
            insert(item.data); // insert 是获取图片 url 后，插入到编辑器的方法
          });
        });
      };
      // this.editor.config.onchange = html => {};

      this.editor.config.pasteTextHandle = function (content) {
        content = content.replace(
          /\<img/g,
          '<img style="width:100%;height:auto;display:block"',
        );
        return content;
      };

      this.editor.config.showLinkImg = false;
      this.editor.config.customAlert = (info) => {
        // info 是需要提示的内容
        this.$message({
          showClose: true,
          message: info,
          type: "error",
        });
      };

      this.editor.config.menus = [
        "head",
        "bold",
        "fontSize",
        "fontName",
        "italic",
        "underline",
        "strikeThrough",
        "indent",
        "lineHeight",
        "foreColor",
        "backColor",
        "link",
        "list",
        "todo",
        "justify",
        "quote",
        "emoticon",
        "image",
        "video",
        "table",
        "code",
        "splitLine",
        "undo",
        "redo",
      ];
      // 颜色配置
      this.editor.config.colors = [
        '#fff','#000','#eeece1','#1f497d','#4f81bd','#c0504d','#9bbb59','#8064a2','#4bacc6','#f79646',
        '#f2f2f2','#7f7f7f','#ddd9c3','#c6d9f0','#dbe5f1','#f2dcdb','#ebf1dd','#e5e0ec','#dbeef3','#fdeada',
        '#bfbfbf','#3f3f3f','#938953','#548dd4','#95b3d7','#d99694','#c3d69b','#b2a2c7','#92cddc','#fac08f',
        '#7f7f7f','#0c0c0c','#1d1b10','#0f243e','#244061','#632423','#4f6128','#3f3151','#205867','#974806',
        '#c00000','#ff0000','#ffc000','#ffff00','#92d050','#00b050','#00b0f0','#0070c0','#002060','#7030a0'
      ];
      this.editor.config.fontSizes = {
        "x-small": { name: "10px", value: "1" },
        small: { name: "13px", value: "2" },
        normal: { name: "16px", value: "3" },
        large: { name: "18px", value: "4" },
        "x-large": { name: "24px", value: "5" },
        "xx-large": { name: "32px", value: "6" },
        "xxx-large": { name: "48px", value: "7" },
      };
      // this.change();
      this.editor.create();
      this.editor.txt.html(`<p>${editorHtml}</p>`);
    },

    change() {
      return (this.editor.config.onchange = (e) => {
        console.log("change", e);
      });
    },

    /**
     * 获取编辑框html
     */
    getHtml() {
      // var filterHtml = filterXSS(html) // 此处进行 xss 攻击过滤
      return this.editor.txt.html(); // 获取编辑框html
    },

    /**
     * 获取编辑框text
     */
    getText() {
      return this.editor.txt.text(); // 获取编辑框text
    },

    /**
     * 清空编辑器内容
     */
    clearEditor() {
      this.editor.txt.clear(); // 清空编辑器内容
    },

    uploadFiles(files = []) {
      const promiseAll = files.map((file) => {
        return upLoad({ file });
      });
      return Promise.all(promiseAll);
    },
  },
};
</script>
<style scoped></style>
