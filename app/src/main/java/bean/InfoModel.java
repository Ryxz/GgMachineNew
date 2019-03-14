package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ryx on 2019/2/13.
 */
public class InfoModel implements Serializable {


    /**
     * code : 200
     * message : 获取成功
     * data : [{"id":1,"content1":["2","/tmp/uploads/20190131\\56519eaa510a490972e99b82952ab5ae.mp4"],"content2":["1","/tmp/uploads/20190131\\6ad8eab7bac1bc34be0a1e529e50e0ee.png"],"content3":["1","/tmp/uploads/20190131\\ccd6e5e6772854b21268b413e0160628.png"],"time":15,"type":2},{"id":5,"content1":["2","/public/tmp/uploads/20190213/730f3e5e2b14876dd9ee5960fdd0f908.mp4"],"content2":["1","/public/tmp/uploads/20190213/d85c66977e32e189df3086412c995823.png"],"content3":["1","/public/tmp/uploads/20190213/4950be8e0bc8093fadbd3c24afc89b73.png"],"time":20,"type":2}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {


            /**
             * id : 3
             * content1 : ["2","/tmp/uploads/20190131\\803f5c3dbac30096f333a6a6a5dd0f9b.mp4"]
             * content2 : ["1","/tmp/uploads/20190131\\a4d1b780090d5cde48e9993fc30e959c.jpg"]
             * time : 15
             * type : 1
             */

            private int id;
            private int time;
            private int type;
            private List<String> content1;
            private List<String> content2;
            private List<String> content3;

        public List<String> getContent3() {
            return content3;
        }

        public void setContent3(List<String> content3) {
            this.content3 = content3;
        }

        public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public List<String> getContent1() {
                return content1;
            }

            public void setContent1(List<String> content1) {
                this.content1 = content1;
            }

            public List<String> getContent2() {
                return content2;
            }

            public void setContent2(List<String> content2) {
                this.content2 = content2;
            }
    }
}