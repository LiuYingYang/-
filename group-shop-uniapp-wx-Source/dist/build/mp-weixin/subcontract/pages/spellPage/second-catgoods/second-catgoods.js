(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["subcontract/pages/spellPage/second-catgoods/second-catgoods"],{1553:function(t,e,n){"use strict";function o(t){return o="function"===typeof Symbol&&"symbol"===typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"===typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},o(t)}Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var r=n("9ab4"),c=n("60a3"),i=n("771b");function a(t,e){var n=Object.keys(t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(t);e&&(o=o.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),n.push.apply(n,o)}return n}function s(t){for(var e=1;e<arguments.length;e++){var n=null!=arguments[e]?arguments[e]:{};e%2?a(Object(n),!0).forEach((function(e){u(t,e,n[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(n)):a(Object(n)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(n,e))}))}return t}function u(t,e,n){return e in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}function f(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function l(t,e){for(var n=0;n<e.length;n++){var o=e[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(t,o.key,o)}}function d(t,e,n){return e&&l(t.prototype,e),n&&l(t,n),t}function h(t,e){if("function"!==typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function");t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,writable:!0,configurable:!0}}),e&&p(t,e)}function p(t,e){return p=Object.setPrototypeOf||function(t,e){return t.__proto__=e,t},p(t,e)}function y(t){var e=g();return function(){var n,o=m(t);if(e){var r=m(this).constructor;n=Reflect.construct(o,arguments,r)}else n=o.apply(this,arguments);return b(this,n)}}function b(t,e){return!e||"object"!==o(e)&&"function"!==typeof e?v(t):e}function v(t){if(void 0===t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return t}function g(){if("undefined"===typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"===typeof Proxy)return!0;try{return Boolean.prototype.valueOf.call(Reflect.construct(Boolean,[],(function(){}))),!0}catch(t){return!1}}function m(t){return m=Object.setPrototypeOf?Object.getPrototypeOf:function(t){return t.__proto__||Object.getPrototypeOf(t)},m(t)}var P=function(){n.e("subcontract/pages/spellPage/components/second-goodslist/second-goodslist").then(function(){return resolve(n("c637"))}.bind(null,n)).catch(n.oe)},O=function(){n.e("subcontract/pages/spellPage/components/second-goodsdetail/second-goodsdetail").then(function(){return resolve(n("de2c"))}.bind(null,n)).catch(n.oe)},w=function(){n.e("subcontract/pages/spellPage/components/search-plugin/search-plugin").then(function(){return resolve(n("e426"))}.bind(null,n)).catch(n.oe)},j=function(t){h(n,t);var e=y(n);function n(){var t;return f(this,n),t=e.apply(this,arguments),t.activeTab=1,t.tabList=["综合","销量","新品","价格"],t.secondCatGoodsStyle=1,t.showCategoryId="",t.cartButton=3,t.searchForm={current:1,size:10},t.hasMore=!0,t.goodsList=[],t.chanel=0,t.queryName="",t.isPrice="",t.couponId="",t}return d(n,[{key:"saleMode",get:function(){return this.$STORE.setStore.saleMode}},{key:"onLoad",value:function(t){var e=this;this.setData({chanel:Number(t.chanel),showCategoryId:t.categoryId||"",cartButton:t.cartButton||3,queryName:t.queryName||""},(function(){e.initData()}))}},{key:"initData",value:function(){2===this.chanel||12===this.chanel?this.getGooodsList():4===this.chanel&&this.searchGoodsList()}},{key:"changeStatus",value:function(){this.setData({secondCatGoodsStyle:1===this.secondCatGoodsStyle?2:1})}},{key:"changeActiveTab",value:function(t){var e=this,n=this.isPrice,o=t.currentTarget.dataset.index;n=4===o?"asc"===this.isPrice?"desc":"asc":"",3===o&&(n="desc"),this.setData({goodsList:[],hasMore:!0,searchForm:{current:1,size:10},activeTab:o,isPrice:n},(function(){e.initData()}))}},{key:"loadMored",value:function(){this.initData()}},{key:"getGooodsList",value:function(){var t=this;if(this.hasMore){var e=s(s({},this.searchForm),{},{type:this.activeTab,name:this.queryName,showCategoryId:this.showCategoryId,sort:this.isPrice});(0,i.getGoodsList)(e,{}).then((function(e){var n=[];e.list.forEach((function(e){n.push({id:Number(e.id),name:e.name,img:t.returnGoodsImg(e),actPrice:e.minPrice,price:e.maxPrice,soldCount:e.sale,inventory:e.inventory})})),t.setData({goodsList:t.goodsList.concat(n)},(function(){t.dealSearchParam(e)}))}))}}},{key:"searchGoodsList",value:function(){var t=this;if(this.hasMore){var e=s(s({},this.searchForm),{},{type:this.activeTab,name:this.queryName,sort:this.isPrice});(0,i.searchGoodsList)(e).then((function(e){var n=[];e.list.forEach((function(e){n.push({id:Number(e.id),name:e.name,img:t.returnGoodsImg(e),actPrice:e.minPrice,price:e.maxPrice,soldCount:e.sale,inventory:e.inventory})})),t.setData({goodsList:t.goodsList.concat(n)},(function(){t.dealSearchParam(e)}))}))}}},{key:"dealSearchParam",value:function(t){var e=this.goodsList.length<t.total,n=this.searchForm;e&&(n.current+=1),this.setData({hasMore:e,searchForm:n})}},{key:"returnGoodsImg",value:function(t){var e=t.widePic,n=t.pic,o=n;return 2===this.secondCatGoodsStyle&&(o=e?e.split(",")[0]:n),o}}]),n}(c.Vue);j=(0,r.__decorate)([(0,c.Component)({components:{SecondGoodslist:P,SecondGoodsdetail:O,SearchPlugin:w}})],j);var S=j;e.default=S},"244a":function(t,e,n){"use strict";var o=n("d61e"),r=n.n(o);r.a},"2f18":function(t,e,n){"use strict";n.r(e);var o=n("1553"),r=n.n(o);for(var c in o)["default"].indexOf(c)<0&&function(t){n.d(e,t,(function(){return o[t]}))}(c);e["default"]=r.a},"914c":function(t,e,n){"use strict";n.r(e);var o=n("e204"),r=n("2f18");for(var c in r)["default"].indexOf(c)<0&&function(t){n.d(e,t,(function(){return r[t]}))}(c);n("244a");var i,a=n("f0c5"),s=Object(a["a"])(r["default"],o["b"],o["c"],!1,null,"43f9775d",null,!1,o["a"],i);e["default"]=s.exports},d61e:function(t,e,n){},e204:function(t,e,n){"use strict";var o;n.d(e,"b",(function(){return r})),n.d(e,"c",(function(){return c})),n.d(e,"a",(function(){return o}));var r=function(){var t=this,e=t.$createElement;t._self._c},c=[]},f2a0:function(t,e,n){"use strict";(function(t){n("6cdc");var e=o(n("914c"));function o(t){return t&&t.__esModule?t:{default:t}}t(e.default)}).call(this,n("543d")["createPage"])}},[["f2a0","common/runtime","common/vendor"]]]);