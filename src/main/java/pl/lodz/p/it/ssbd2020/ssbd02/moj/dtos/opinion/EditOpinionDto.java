package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * DTO do edycji opinii.
 */
public class EditOpinionDto {
    @Min(value = 1, message = "{validation.range}")
    @Max(value = 5, message = "{validation.range}")
    private int rating;
    @Pattern(regexp = "[a-zA-Z1-9ąĄćĆęĘłŁńŃóÓśŚźŹżŻ!?. ,@-]{0,1025}", message = "{validation.comment}")
    private String comment;

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
}
