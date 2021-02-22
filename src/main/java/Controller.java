import com.fasterxml.classmate.AnnotationConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

import java.io.File;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ImageView ImageView;
    private MediaPlayerFactory mediaPlayerFactory;
    private EmbeddedMediaPlayer player;
    private EmbeddedMediaPlayerComponent component;
    private MediaPlayer mp;
    private Media me;
    private Session session;
    @FXML
    private Label TrescPytaniaLabel;
    @FXML
    private Label odp1Label;
    @FXML
    private Label odp2Label;
    @FXML
    private Label odp3Label;
    @FXML
    private Button startButton;
    @FXML
     private Button checkButton;
    int selectedanswer = -1;
    String corrAnswer;
    String mediapath;

    public Controller() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadQuestion();

    }


    public void LoadQuestion() {
        session = HibernateConn.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Random rand = new Random();
            int randnumber = rand.nextInt(3000);
            List lista = session.createQuery("FROM Question WHERE id='" + randnumber + "'").list();

            for (Object o : lista) {
                Question question = (Question) o;
                PopulateLabel(question.getContent(), question.getAns1(), question.getAns2(), question.getAns3());
                corrAnswer = question.getCorrect();
                if (question.getMedia() == null) {
                    mediapath = "D:\\Multimedia_KieroTest\\nophoto.jpg";
                } else {
                    mediapath = "D:\\Multimedia_KieroTest\\" + question.getMedia();
                }
                if (mediapath.contains(".jpg")) {
                    MediaButton();
                }
                System.out.println("Content: " + question.getContent());
                System.out.println("Odp1: " + question.getAns1());
                System.out.println("Odp2: " + question.getAns2());
                System.out.println("Odp3: " + question.getAns3());
                System.out.println("Poprawna: " + question.getCorrect());
                System.out.println("Mediapath: " + mediapath);
            }
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @FXML
    private void MediaButton() {
        mediaPlayerFactory = new MediaPlayerFactory();
        player = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        player.videoSurface().set(videoSurfaceForImageView(ImageView));
        player.media().play(mediapath);
        System.out.println("Media w miejscu odpalania: " + mediapath);

        startButton.setDisable(true);


    }

    public void PopulateLabel(String content, String odp1, String odp2, String odp3) {
        TrescPytaniaLabel.setText(content);
        if (odp1 == null || odp2 == null || odp3 == null) {
            odp1Label.setText("Tak");
            odp2Label.setText("Nie");
            odp3Label.setVisible(false);
        } else {
            odp1Label.setText(odp1);
            odp2Label.setText(odp2);
            odp3Label.setText(odp3);
        }
    }

    public boolean AnswerChecker() {
        return (selectedanswer == 1 && corrAnswer.equals("A") || ((selectedanswer == 1) && corrAnswer.equals("T")) || (selectedanswer == 2 && corrAnswer.equals("B")) || (selectedanswer == 2 && corrAnswer.equals("N"))) || (selectedanswer == 3 && corrAnswer.equals("C"));
    }

    public Label correctLabel() {
        if (corrAnswer.equals("A") || corrAnswer.equals("T")) return odp1Label;
        if (corrAnswer.equals("B") || corrAnswer.equals("N")) return odp2Label;
        if (corrAnswer.equals("C")) return odp3Label;
        else return null;
    }

    public Label selectedLabel() {
        if (selectedanswer == 1) return odp1Label;
        if (selectedanswer == 2) return odp2Label;
        if (selectedanswer == 3) return odp3Label;
        else return null;
    }

    @FXML
    public void checkButtonAction() {
        if (selectedanswer == -1) {
            System.out.println("Nie wybrano żadnej odpowiedzi!");
            correctLabel().setStyle("-fx-background-color: #007700");
        } else {
            if (AnswerChecker()) {
                System.out.println("Dobrze! To poprawna odpowiedź!");
                correctLabel().setStyle("-fx-background-color: #070");
            } else {
                selectedLabel().setStyle("-fx-background-color: #800");
                correctLabel().setStyle("-fx-background-color: #070");
            }
        }

    }


    @FXML
    public void Odp1Clicked() {
        odp1Label.setStyle("-fx-background-color: #a1a1a1");
        odp2Label.setStyle(null);
        odp3Label.setStyle(null);
        selectedanswer = 1;
        checkButton.setDisable(false);
    }

    @FXML
    public void Odp2Clicked() {
        selectedanswer = 2;
        odp2Label.setStyle("-fx-background-color: #a1a1a1");
        odp1Label.setStyle(null);
        odp3Label.setStyle(null);
        checkButton.setDisable(false);
    }

    @FXML
    public void Odp3Clicked() {
        selectedanswer = 3;
        odp3Label.setStyle("-fx-background-color: #a1a1a1");
        odp1Label.setStyle(null);
        odp2Label.setStyle(null);
        checkButton.setDisable(false);
    }

    @FXML
    public void nextButton() {
        LoadQuestion();
        selectedanswer = -1;
        odp1Label.setStyle(null);
        odp2Label.setStyle(null);
        odp3Label.setStyle(null);
        ImageView.setImage(null);
        startButton.setDisable(false);
        checkButton.setDisable(true);
        odp3Label.setVisible(true);
        odp3Label.setText(null);
        mediaPlayerFactory=null;
        player=null;
    }


}