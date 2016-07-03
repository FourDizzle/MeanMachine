package us.superkill.meanmachine.identity;

import java.util.List;

public interface PostFilter {

  public abstract List<Post> filter(List<Post> posts);
}
