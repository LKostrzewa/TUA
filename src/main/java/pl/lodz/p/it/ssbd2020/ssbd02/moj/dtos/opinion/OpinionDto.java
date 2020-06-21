package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion;

import java.util.Date;

/**
 * DTO opinii.
 */
public class OpinionDto {
    private int rating;
    private String comment;
    private Date date;
    private Boolean edited;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }
}
