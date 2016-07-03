package us.superkill.meanmachine.identity;

import java.util.ArrayList;
import java.util.List;

public class CompositePostFilter implements PostFilter {

  List<PostFilter> filters = new ArrayList<PostFilter>();

  @Override
  public List<Post> filter(List<Post> posts) {
    // TODO Auto-generated method stub
    return posts;
  }

  public void add(PostFilter filter) {
    this.filters.add(filter);
  }

  public void remove(PostFilter filter) {
    this.filters.remove(filter);
  }
}
