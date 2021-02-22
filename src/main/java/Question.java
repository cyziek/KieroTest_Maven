import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pytania")

public class Question implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "ans1")
    private String ans1;
    @Column(name = "ans2")
    private String ans2;
    @Column(name = "ans3")
    private String ans3;
    @Column(name = "correct")
    private String correct;
    @Column(name = "media")
    private String media;


    public Question(int id, String content, String ans1, String ans2, String ans3, String correct, String media) {
        this.id = id;
        this.content = content;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.correct = correct;
        this.media = media;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
