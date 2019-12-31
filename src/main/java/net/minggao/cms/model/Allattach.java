package net.minggao.cms.model;

public class Allattach {
    private String filename;

    private String filerealname;

    private Long filesize;

    private Integer encrypt;

    private String fielPath;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFilerealname() {
        return filerealname;
    }

    public void setFilerealname(String filerealname) {
        this.filerealname = filerealname == null ? null : filerealname.trim();
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public String getFielPath() {
        return fielPath;
    }

    public void setFielPath(String fielPath) {
        this.fielPath = fielPath == null ? null : fielPath.trim();
    }
}