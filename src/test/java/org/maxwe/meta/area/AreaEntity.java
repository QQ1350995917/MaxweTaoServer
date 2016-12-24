package org.maxwe.meta.area;

/**
 * Created by Pengwei Ding on 2016-07-26 20:30.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 构件行政区域对象
 */
public class AreaEntity {
    private String addressCode; // 十二位行政区域码
    private String addressLabel;
    private String addressName;
    private String addressDesc;
    private int addressLevel;
    private int addressOrder;
    private int addressStatus;
    private String addressPCode;


    public AreaEntity(String addressCode, String addressName, int addressLevel, int addressOrder, int addressStatus, String addressPCode) {
        this.addressCode = addressCode;
        this.addressName = addressName;
        this.addressLevel = addressLevel;
        this.addressOrder = addressOrder;
        this.addressStatus = addressStatus;
        this.addressPCode = addressPCode;
    }

    public AreaEntity(String addressCode, String addressLabel, String addressName, int addressLevel, int addressOrder, int addressStatus, String addressPCode) {
        this.addressCode = addressCode;
        this.addressLabel = addressLabel;
        this.addressName = addressName;
        this.addressLevel = addressLevel;
        this.addressOrder = addressOrder;
        this.addressStatus = addressStatus;
        this.addressPCode = addressPCode;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressLabel() {
        if (this.addressLabel == null) {
            if (this.addressName.contains("省")) {
                this.addressLabel = this.addressName.substring(0, this.addressName.lastIndexOf("省"));
            }
            if (this.addressName.contains("市")) {
                this.addressLabel = this.addressName.substring(0, this.addressName.lastIndexOf("市"));
            }
            if (this.addressLabel == null) {
                this.addressLabel = this.addressName;
            }
            if (this.addressLabel.contains("维吾尔自治区")) {
                this.addressLabel = this.addressLabel.replace("维吾尔自治区", "");
            }
            if (this.addressLabel.contains("回族自治区")) {
                this.addressLabel = this.addressLabel.replace("回族自治区", "");
            }
            if (this.addressLabel.contains("壮族自治区")) {
                this.addressLabel = this.addressLabel.replace("壮族自治区", "");
            }
            if (this.addressLabel.contains("自治区")) {
                this.addressLabel = this.addressLabel.replace("自治区", "");
            }
        }

        if (this.addressLabel.endsWith("第一居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第一居民委员会", "");
        }
        if (this.addressLabel.endsWith("第二居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第二居民委员会", "");
        }
        if (this.addressLabel.endsWith("第三居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第三居民委员会", "");
        }
        if (this.addressLabel.endsWith("第四居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第四居民委员会", "");
        }
        if (this.addressLabel.endsWith("第五居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第五居民委员会", "");
        }
        if (this.addressLabel.endsWith("第六居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第六居民委员会", "");
        }
        if (this.addressLabel.endsWith("第七居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第七居民委员会", "");
        }
        if (this.addressLabel.endsWith("第八居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第八居民委员会", "");
        }
        if (this.addressLabel.endsWith("第九居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第九居民委员会", "");
        }
        if (this.addressLabel.endsWith("第十居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第十居民委员会", "");
        }
        if (this.addressLabel.endsWith("第十一居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第十一居民委员会", "");
        }
        if (this.addressLabel.endsWith("第十二居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第十二居民委员会", "");
        }
        if (this.addressLabel.endsWith("第十三居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第十三居民委员会", "");
        }
        if (this.addressLabel.endsWith("第十四居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第十四居民委员会", "");
        }
        if (this.addressLabel.endsWith("第十五居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第十五居民委员会", "");
        }
        if (this.addressLabel.endsWith("第十六居民委员会")) {
            this.addressLabel = this.addressLabel.replace("第十六居民委员会", "");
        }

        if (this.addressLabel.endsWith("第一居委会")) {
            this.addressLabel = this.addressLabel.replace("第一居委会", "");
        }
        if (this.addressLabel.endsWith("第二居委会")) {
            this.addressLabel = this.addressLabel.replace("第二居委会", "");
        }
        if (this.addressLabel.endsWith("第三居委会")) {
            this.addressLabel = this.addressLabel.replace("第三居委会", "");
        }
        if (this.addressLabel.endsWith("第四居委会")) {
            this.addressLabel = this.addressLabel.replace("第四居委会", "");
        }
        if (this.addressLabel.endsWith("第五居委会")) {
            this.addressLabel = this.addressLabel.replace("第五居委会", "");
        }
        if (this.addressLabel.endsWith("第六居委会")) {
            this.addressLabel = this.addressLabel.replace("第六居委会", "");
        }
        if (this.addressLabel.endsWith("第七居委会")) {
            this.addressLabel = this.addressLabel.replace("第七居委会", "");
        }
        if (this.addressLabel.endsWith("第八居委会")) {
            this.addressLabel = this.addressLabel.replace("第八居委会", "");
        }
        if (this.addressLabel.endsWith("第九居委会")) {
            this.addressLabel = this.addressLabel.replace("第九居委会", "");
        }
        if (this.addressLabel.endsWith("第十居委会")) {
            this.addressLabel = this.addressLabel.replace("第十居委会", "");
        }
        if (this.addressLabel.endsWith("第十一居委会")) {
            this.addressLabel = this.addressLabel.replace("第十一居委会", "");
        }
        if (this.addressLabel.endsWith("第十二居委会")) {
            this.addressLabel = this.addressLabel.replace("第十二居委会", "");
        }
        if (this.addressLabel.endsWith("第十三居委会")) {
            this.addressLabel = this.addressLabel.replace("第十三居委会", "");
        }
        if (this.addressLabel.endsWith("第十四居委会")) {
            this.addressLabel = this.addressLabel.replace("第十四居委会", "");
        }
        if (this.addressLabel.endsWith("第十五居委会")) {
            this.addressLabel = this.addressLabel.replace("第十五居委会", "");
        }
        if (this.addressLabel.endsWith("第十六居委会")) {
            this.addressLabel = this.addressLabel.replace("第十六居委会", "");
        }


        if (this.addressLabel.endsWith("居民委员会")) {
            this.addressLabel = this.addressLabel.replace("居民委员会", "");
        }
        if (this.addressLabel.endsWith("村民委员会")) {
            this.addressLabel = this.addressLabel.replace("村民委员会", "村");
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
        }
        if (this.addressLabel.contains("村民委员会")) {
            this.addressLabel = this.addressLabel.replace("村民委员会", "村");
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
        }

        if (this.addressLabel.endsWith("居委会")) {
            this.addressLabel = this.addressLabel.replace("居委会", "");
        }
        if (this.addressLabel.contains("社区居委会")) {
            this.addressLabel.replace("社区居委会", "");
        }

        if (this.addressLabel.endsWith("村委会")) {
            this.addressLabel = this.addressLabel.replace("村委会", "村");
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
        }
        if (this.addressLabel.contains("村委会")) {
            this.addressLabel = this.addressLabel.replace("村委会", "村");
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
            if (this.addressLabel.endsWith("村村")) {
                this.addressLabel = this.addressLabel.replace("村村", "村");
            }
        }

        if (this.addressLabel.endsWith("街道办事处")) {
            this.addressLabel = this.addressLabel.replace("街道办事处", "");
        }

        if (this.addressLabel.contains("街道办事处")) {
            this.addressLabel = this.addressLabel.replace("街道办事处", "");
        }

        if (this.addressLabel.endsWith("公共服务站")) {
            this.addressLabel = this.addressLabel.replace("公共服务站", "");
        }

        if (this.addressLabel.contains("公共服务站")) {
            this.addressLabel = this.addressLabel.replace("公共服务站", "");
        }

        return this.addressLabel;
    }

    public void setAddressLabel(String addressLabel) {
        this.addressLabel = addressLabel;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public int getAddressLevel() {
        return addressLevel;
    }

    public void setAddressLevel(int addressLevel) {
        this.addressLevel = addressLevel;
    }

    public int getAddressOrder() {
        return addressOrder;
    }

    public void setAddressOrder(int addressOrder) {
        this.addressOrder = addressOrder;
    }

    public int getAddressStatus() {
        if (this.getAddressName().equals("市辖区") || this.getAddressName().equals("县") || this.getAddressName().equals("省直辖县级行政区划")) {
            return 0;
        }
        if (this.getAddressName().contains("自治州") && this.getAddressLevel() == 2) {
            return 0;
        }
        if (this.getAddressName().contains("大兴安岭地区") && this.getAddressLevel() == 2) {
            return 0;
        }
        if (this.getAddressName().contains("街道办事处") || this.getAddressName().contains("公共服务站")) {
            return 0;
        }
        if (this.getAddressName().equals("无县级区划")){
            return 0;
        }

        return 1;
    }

    public void setAddressStatus(int addressStatus) {
        this.addressStatus = addressStatus;
    }

    public String getAddressPCode() {
        return addressPCode;
    }

    public void setAddressPCode(String addressPCode) {
        this.addressPCode = addressPCode;
    }

    @Override
    public boolean equals(Object obj) {
        AreaEntity address = (AreaEntity) obj;
        if (this.addressCode.equals(address.getAddressCode()) && this.addressPCode.equals(address.getAddressPCode())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String values = this.getAddressCode() + "','" + this.getAddressLabel() + "','" + this.getAddressName() + "','','" + this.getAddressLevel() + "','" + this.getAddressOrder() + "','" + this.getAddressStatus() + "','" + this.getAddressPCode();
//        String string = "insert into meta_address(addressCode,addressLabel,addressName,addressDesc,addressLevel,addressOrder,addressStatus,addressPCode) values('" + values + "');";
        String string = "INSERT INTO `address`(`code`, `label`, `name`, `desc`, `level`, `order`, `status`, `Pcode`) VALUES ('" + values + "');";
        return string;
    }
}
