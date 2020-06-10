package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion;

/**
 * DTO do edycji opinii.
 */
public class EditOpinionDto {
    private int rating;
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
