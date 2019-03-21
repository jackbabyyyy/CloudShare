package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/3/1.
 */
public class ToolListBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"title":"征信查询","icon":"https://www.dafyun.cn/Public/images/app/m1.png","type":"view","url":""},{"title":"信用卡进度查询","icon":"https://www.dafyun.cn/Public/images/app/m2.png","type":"view","url":""},{"title":"进度查询","icon":"https://www.dafyun.cn/Public/images/app/m3.png","type":"view","url":""},{"title":"运营商分析","icon":"https://www.dafyun.cn/Public/images/app/m4.png","type":"view","url":""},{"title":"产品匹配","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"组织管理","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"button","menu":[{"title":"新增员工","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"button","menu":[{"title":"是否管理当前组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"员工基本信息","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"是否查看当前组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"是否管理本级以下组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""}]},{"title":"移出员工","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"新增组织名称","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"修改组织名称","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""}]},{"title":"扫一扫","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"业务二维码","icon":"https://www.dafyun.cn/Public/images/app/m6.png","type":"view","url":""}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 征信查询
         * icon : https://www.dafyun.cn/Public/images/app/m1.png
         * type : view
         * url :
         * menu : [{"title":"新增员工","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"button","menu":[{"title":"是否管理当前组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"员工基本信息","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"是否查看当前组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"是否管理本级以下组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""}]},{"title":"移出员工","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"新增组织名称","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"修改组织名称","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""}]
         */

        private String title;
        private String icon;
        private String type;
        private String url;
        private List<MenuBeanX> menu;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<MenuBeanX> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuBeanX> menu) {
            this.menu = menu;
        }

        public static class MenuBeanX {
            /**
             * title : 新增员工
             * icon : https://www.dafyun.cn/Public/images/app/m5.png
             * type : button
             * menu : [{"title":"是否管理当前组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"员工基本信息","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"是否查看当前组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"是否管理本级以下组织","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""}]
             * url :
             */

            private String title;
            private String icon;
            private String type;
            private String url;
            private List<MenuBean> menu;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<MenuBean> getMenu() {
                return menu;
            }

            public void setMenu(List<MenuBean> menu) {
                this.menu = menu;
            }

            public static class MenuBean {
                /**
                 * title : 是否管理当前组织
                 * icon : https://www.dafyun.cn/Public/images/app/m5.png
                 * type : view
                 * url :
                 */

                private String title;
                private String icon;
                private String type;
                private String url;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
