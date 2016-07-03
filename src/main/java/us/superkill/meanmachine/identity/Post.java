package us.superkill.meanmachine.identity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Represents a single post by an identity and the "posts" table.
 * 
 * @author ncassiani
 *
 */
@Entity
@Table(name = "posts")
public class Post implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -9179589285131077983L;


  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  @Column(name = "post_id", nullable = false, unique = true, length = 18)
  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  private Long postId;

  @Column(name = "content", nullable = false)
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  private String content;

  @Column(name = "timestamp", nullable = false)
  public Instant getTimeOfPost() {
    return timeOfPost;
  }

  public void setTimeOfPost(Instant timeOfPost) {
    this.timeOfPost = timeOfPost;
  }

  private Instant timeOfPost;

}
