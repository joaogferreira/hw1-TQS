package com.joao.tqshw1;

import java.util.List;

public class Stations {

    private String status;
    private List<Station> data;

    protected String getStatus() {
        return status;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    protected List<Station> getData() {
        return data;
    }

    protected void setData(List<Station> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Stations [status=" + status + ", data=" + data + "]";
    }
}
