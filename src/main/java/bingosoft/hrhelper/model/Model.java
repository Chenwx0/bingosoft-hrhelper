package bingosoft.hrhelper.model;

public class Model {
    private Integer id;

    private String modelName;

    private String modelContent;

    private String attachmentHref;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getModelContent() {
        return modelContent;
    }

    public void setModelContent(String modelContent) {
        this.modelContent = modelContent == null ? null : modelContent.trim();
    }

    public String getAttachmentHref() {
        return attachmentHref;
    }

    public void setAttachmentHref(String attachmentHref) {
        this.attachmentHref = attachmentHref == null ? null : attachmentHref.trim();
    }
}