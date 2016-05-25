package us.superkill.mean_machine.identity;

import java.util.List;

public abstract class PostFilter {
	
	public abstract List<Post> filter(List<? extends Post> posts);
}
