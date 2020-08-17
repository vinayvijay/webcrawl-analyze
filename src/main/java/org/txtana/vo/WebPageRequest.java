package org.txtana.vo;

public class WebPageRequest {
    
    private String weblink;

    private String count;

    public WebPageRequest() {}
/*
    public WebPageRequest(String weblink, int count) {
        this.weblink = weblink;
        this.count = count;
    }
*/
    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}