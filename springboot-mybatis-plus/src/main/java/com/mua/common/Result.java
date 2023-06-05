package com.mua.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Result extends LinkedHashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    public Result() {
    }

    public Result(int code, String message, Object data, long timestamp) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
        this.setTimestamp(timestamp);
    }

    public Result(Map<String, ?> map) {
        this.setMap(map);
    }

    public Integer getCode() {
        return (Integer) this.get("code");
    }

    public String getMessage() {
        return (String) this.get("message");
    }

    public long getTimestamp() {
        return (long) this.get("timestamp");
    }

    public Object getData() {
        return this.get("data");
    }

    public Result setCode(int code) {
        this.put("code", code);
        return this;
    }

    public Result setMessage(String message) {
        this.put("message", message);
        return this;
    }

    private Result setTimestamp(long timestamp) {
        this.put("timestamp", timestamp);
        return this;
    }

    public Result setData(Object data) {
        this.put("data", data);
        return this;
    }

    public Result set(String key, Object data) {
        this.put(key, data);
        return this;
    }

    public Result setMap(Map<String, ?> map) {
        Iterator var2 = map.keySet().iterator();

        while (var2.hasNext()) {
            String key = (String) var2.next();
            this.put(key, map.get(key));
        }

        return this;
    }

    public static Result success() {
        return new Result(200, "success", (Object) null, System.currentTimeMillis());
    }

    public static Result success(String message) {
        return new Result(200, message, (Object) null, System.currentTimeMillis());
    }

    public static Result code(int code) {
        return new Result(code, (String) null, (Object) null, System.currentTimeMillis());
    }

    public static Result data(Object data) {
        return new Result(200, "success", data, System.currentTimeMillis());
    }

    public static Result error() {
        return new Result(500, "error", (Object) null, System.currentTimeMillis());
    }

    public static Result error(String message) {
        return new Result(500, message, (Object) null, System.currentTimeMillis());
    }

    public static Result get(int code, String message, Object data) {
        return new Result(code, message, data, System.currentTimeMillis());
    }
}
