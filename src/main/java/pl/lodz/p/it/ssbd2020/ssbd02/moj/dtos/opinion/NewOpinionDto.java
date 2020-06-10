package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * DTO do dodawania nowej opinii.
 */
public class NewOpinionDto {
    private Long rentalId;
    @Max(5)
    @Min(1)
    private int rating;
    private String comment;
    private Date date;

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

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
}
