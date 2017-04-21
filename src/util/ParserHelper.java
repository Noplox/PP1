package util;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import source.MJParser;

public class ParserHelper {

	private MJParser parser;
	public Struct boolType;

	private int globalVarCnt = 0;
	private int localVarCnt = 0;
	private int globalConstCnt = 0;
	private int globalArrayCnt = 0;
	private Obj currentTypeDeclaration = null;

	public ParserHelper(MJParser myParser) {
		parser = myParser;
	}

	public Obj typeNameToObj(String typeName) {
		Obj temp = Tab.find(typeName);
		if(temp == Tab.noObj) {
			parser.report_error("Unknown type " + typeName + "!", null);
			return temp;
		} else if (temp.getKind() == Obj.Type) {
			return temp;
		} else {
			parser.report_error("Unknown type " + typeName + "! Name in use but not as type!", null);
			return temp;
		}
	}
	
	public Obj getCurrentTypeDeclaration() {
		return currentTypeDeclaration;
	}
	public void setCurrentTypeDeclaration(Obj currentTypeDeclaration) {
		this.currentTypeDeclaration = currentTypeDeclaration;
	}

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
	
	public void initBool() {
		boolType = new Struct(Struct.Bool);
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}

	public Obj evaluateNumConst(Integer constant) {
		Obj retVal = new Obj(Obj.Con, "Temp", Tab.intType);
		retVal.setAdr(constant.intValue());
		return retVal;
	}

	public Obj evaluateCharConst(Character constant) {
		Obj retVal = new Obj(Obj.Con, "Temp", Tab.charType);
		retVal.setAdr((int)constant.charValue());
		return retVal;
	}

	public Obj evaluateBoolConst(Boolean constant) {
		Obj retVal =  new Obj(Obj.Con, "Temp", boolType);
		retVal.setAdr(constant.booleanValue() ? 1 : 0);
		return retVal;
	}
}
