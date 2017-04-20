package util;

public class ParserHelper {

	private int globalVarCnt = 0;
	private int localVarCnt = 0;
	private int globalConstCnt = 0;
	private int globalArrayCnt = 0;
	
	private boolean inProgram = false;
	private boolean inMain = false;
	
	public boolean isInProgram() { return inProgram; }
	public boolean isInMain() { return inMain; }
	
	public void enteringProgram() { inProgram = true; }
	public void exitingProgram() { inProgram = false; }
	
	public void enteringMain() { inMain = true; }
	public void exitingMain() { inMain = false; }

	public void globalVarFound() { globalVarCnt++; }
	public void localVarFound() { localVarCnt++; }
	public void globalConstFound() { globalConstCnt++; }
	public void globalArrayFound() { globalArrayCnt++; }
	
	public int getGlobalVarCnt() {
		return globalVarCnt;
	}
	public int getLocalVarCnt() {
		return localVarCnt;
	}
	public int getGlobalConstCnt() {
		return globalConstCnt;
	}
	public int getGlobalArrayCnt() {
		return globalArrayCnt;
	}
	
	public String printParseCount() {
		String retVal = "";
		retVal += "Global variable count: " + globalVarCnt + "\n";
		retVal += "Local variable count: " + localVarCnt + "\n";
		retVal += "Global array count: " + globalArrayCnt + "\n";
		retVal += "Global constant count: " + globalConstCnt + "\n";
		return retVal;
	}
	
}
