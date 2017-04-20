package util;

public class ParserHelper {

	private boolean inProgram = false;
	private boolean inMain = false;
	
	public boolean isInProgram() { return inProgram; }
	public boolean inInMain() { return inMain; }
	
	public void enteringProgram() { inProgram = true; }
	public void exitingProgram() { inProgram = false; }
	
	public void enteringMain() { inMain = true; }
	public void exitingMain() { inMain = false; }
}
