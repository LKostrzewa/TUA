package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image;

/**
 * DTO zdjęcia jachtu.
 */
public class ImageDto {
    private Long id;
    private byte[] lob;
    private Long yachtModelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLob() {
        return lob;
    }

    public void setLob(byte[] lob) {
        this.lob = lob;
    }

    public Long getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(Long yachtModelId) {
        this.yachtModelId = yachtModelId;
    }
}
