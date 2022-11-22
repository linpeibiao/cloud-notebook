package com.xiaohu.cloud_notebook_common.result;

/**
 * @author xiaohu
 * @date 2022/09/21
 * @description
 * 使用常量定义一组值的缺点： 类型不安全。常量一般使用String或者int类型定义，程序可以传递任意的值。
 * 枚举对象不能在枚举外部进行实例化，因此它的构造方法不能说public和protected。JVM在运行期间会为每个枚举实例生成一个唯一标识。
 * 使用枚举类型来封装异常码.
 * 枚举隐含了所创建的类型都是 java.lang.Enum 类的子类，可以把 enum 看成是一个普通的 class，它们都可以定义一些属性和方法
 */
public enum ResultCode {
    SUCCESS(0, "ok", ""),
    FAIL(40000, "请求失败", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    PARAMS_ERROR(40002, "请求参数错误", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    NOT_FOUND(40004, "未找到资源", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ResultCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
