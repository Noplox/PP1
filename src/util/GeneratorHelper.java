package util;

import source.MJParser;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.mj.runtime.Code;

public class GeneratorHelper {
	private ParserHelper parserHelper;

	public static boolean constructorCall = false;
	public static boolean additionalPopsNeeded = true;
	public static boolean minus = false;
	public static int parenNestingLevel = 0;
	
	public GeneratorHelper(ParserHelper myHelper) {
		parserHelper = myHelper;
	}
	
	public static void swapStack() {
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
	}

	public static void prepareArrayStore() {
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
	}

	public static void doAdditionalPops() {
		removeExcessIndices();//Clean code, as I've said.
	}

	public static void storeDesignator(Obj designator) {
		if(designator.getType().getKind() != Struct.Array)
			Code.store(designator);
		else {
			if(!constructorCall) {
				if(designator.getType().getElemType().getKind() == Struct.Int)
					Code.put(Code.astore);
				else
					Code.put(Code.bastore);
			} else {
				Code.store(designator);
			}
		}
		constructorCall = false;
	}

	public static void removePretposlednji() {
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.pop);
	}

	public static void removeExcessIndices(){
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
		//Clean code ^^
	}

	public static void executeOperation(int opCode) {
		switch(opCode){
			case ParserHelper.PLUSASSIGN : Code.put(Code.add); break;
			case ParserHelper.MINUSASSIGN : Code.put(Code.sub); break;
			case ParserHelper.MULASSIGN : Code.put(Code.mul); break;
			case ParserHelper.DIVASSIGN : Code.put(Code.div); break;
			case ParserHelper.MODASSIGN : Code.put(Code.rem); break;
			case ParserHelper.PLUS : Code.put(Code.add); break;
			case ParserHelper.MINUS : Code.put(Code.sub); break;
			case ParserHelper.MUL : Code.put(Code.mul); break;
			case ParserHelper.DIV : Code.put(Code.div); break;
			case ParserHelper.MOD : Code.put(Code.rem); break;
		}
	}

	public static void storeAndLoad(Obj object) {
		if(object.getType().getKind() == Struct.Array) {
			if(object.getType().getElemType().getKind() == Struct.Int) {
				Code.put(Code.astore);
				Code.put(Code.aload);
			} else {
				Code.put(Code.bastore);
				Code.put(Code.baload);
			}
		} else {
			Code.store(object);
			Code.load(object);
		}
	}
/*
	public static void loadDesignator(Obj designator) {
		if(designator.getType().getKind() != Struct.Array) {
			Code.load(parser.parserHelper.designatorStatementDesignator);
		} else {

		}
	}

	//Vrv ne treba... Videcu
	public static void loadArray(Obj arrayObj) {
		Code.load(arrayObj);
		GeneratorHelper.swapStack();
		if(arrayObj.getType().getKind() == Struct.Int) {
			Code.put(Code.aload);
		} else {
			Code.put(Code.baload);
		}
	}
*/
}
