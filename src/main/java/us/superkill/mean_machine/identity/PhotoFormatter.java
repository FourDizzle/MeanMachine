package us.superkill.mean_machine.identity;

import us.superkill.mean_machine.exceptions.FormatterTargetNotFound;

public abstract class PhotoFormatter {
	//TODO add methods
	public abstract Photo format(Photo photo) throws FormatterTargetNotFound;
}
