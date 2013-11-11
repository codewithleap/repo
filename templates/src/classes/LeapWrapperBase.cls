public abstract class LeapWrapperBase {
	public Id id;  
	public boolean success = true;
	public List<String> errors = new List<String>();
	public boolean hasErrors(){ return errors.size() > 0;}
	public void clearErrors() { success = true; errors.clear();}
}