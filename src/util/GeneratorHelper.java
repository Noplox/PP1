package util;

import source.MJParser;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.mj.runtime.Code;

public class GeneratorHelper {
	private ParserHelper parserHelper;

	public static boolean constructorCall = false;
	
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
