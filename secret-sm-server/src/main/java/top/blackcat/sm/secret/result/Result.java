package top.blackcat.sm.secret.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private String timestamp;
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.setCode(ResultStatus.Code.SUCCESS);
        result.setMsg(ResultStatus.Msg.SUCCESS);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultStatus.Code.SUCCESS);
        result.setMsg(ResultStatus.Msg.SUCCESS);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<T>();
        result.setCode(ResultStatus.Code.SUCCESS);
        result.setMsg(msg);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        return result;
    }

    public static <T> Result<T> success(T data, String msg) {
        Result<T> result = new Result<T>();
        result.setCode(ResultStatus.Code.SUCCESS);
        result.setMsg(msg);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        result.setData(data);
        return result;
    }


    public static <T> Result<T> failure() {
        Result<T> result = new Result<T>();
        result.setCode(ResultStatus.Code.FAILURE);
        result.setMsg(ResultStatus.Msg.FAILURE);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        return result;
    }

    public static <T> Result<T> failure(int code, String msg) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(msg);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        return result;
    }

    public static <T> Result<T> failure(String msg) {
        Result<T> result = new Result<T>();
        result.setCode(ResultStatus.Code.FAILURE);
        result.setMsg(msg);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        return result;
    }


    public static <T> Result<T> failure(T data, String msg) {
        Result<T> result = new Result<T>();
        result.setCode(ResultStatus.Code.FAILURE);
        result.setMsg(msg);
        result.setTimestamp(DateTimeUtil.getCurrentLongDateTimeStr());
        result.setData(data);
        return result;
    }
}
