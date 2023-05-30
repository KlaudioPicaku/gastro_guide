
package com.uniroma3.it.gastroguide.models;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
public class Review {
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;

    @Column(name = "title", nullable = false, length = 256)
    private String title;

    @Column(name = "rating", nullable = false, length = 5)
    private Integer rating;

    @Column(name = "body", nullable = false, length = 1764)
    private String body;

    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "film_Id", nullable = false)
    private Recipe recipe;


    // costruttori, getter, setter, equals, hashCode, toString
    public Review(){

    }
    public Review( String title, Integer rating, String body, User user, Recipe recipe) {
        this.title = title;
        this.rating = rating;
        this.body = body;
        this.createdOn =LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
        this.user = user;
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public User getuser() {
        return user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", body='" + body + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", user=" + user +
                ", film=" + recipe +
                '}';
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
