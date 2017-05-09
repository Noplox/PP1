package util;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import source.MJParser;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.mj.runtime.Code;

public class GeneratorHelper {
	private ParserHelper parserHelper;
	
	private Stack<Obj> mulopRightStack = new Stack<>();
	private Queue<Obj> mulopLeftQueue = new ArrayDeque<>();
	private boolean mulopLeftOccured = false;
	public boolean newArray = false;
	
	public GeneratorHelper(ParserHelper myHelper) {
		parserHelper = myHelper;
	}
	
	public void pushMulopRightOperand(Obj object) {
		if(!newArray)
			mulopRightStack.push(object);
	}
	
	public void pushMulopRightOperator(int opCode) {
		if(!newArray) {
			Obj operation = new Obj(Obj.Con, "op", Tab.noType);
			
			switch(opCode) {
			case ParserHelper.MULASSIGN:
				operation.setAdr(Code.mul);
				break;
			case ParserHelper.DIVASSIGN:
				operation.setAdr(Code.div);
				break;
			case ParserHelper.MODASSIGN:
				operation.setAdr(Code.rem);
				break;
			default:
				operation.setAdr(Code.add);
				break;
			}
			mulopRightStack.push(operation);
		}
	}
	
	public void putMulopLeftOperand(Obj object) {
		if(!newArray)
			mulopLeftQueue.add(object);
	}
	
	public void putMulopLeftOperator(int opCode) {
		if(!newArray) {
			Obj operation = new Obj(Obj.Con, "op", Tab.noType);
			
			switch(opCode) {
			case ParserHelper.MUL:
				operation.setAdr(Code.mul);
				break;
			case ParserHelper.DIV:
				operation.setAdr(Code.div);
				break;
			case ParserHelper.MOD:
				operation.setAdr(Code.rem);
				break;
			default:
				operation.setAdr(Code.add);
				break;
			}
			mulopLeftQueue.add(operation);
		}
	}
	
	public void generateMulopCode() {
		if(!newArray) {
			boolean lastLoad = true;
			Obj op1 = null;
			Obj op2 = null;
			Obj operation = null;
			if(!mulopLeftQueue.isEmpty()) {
				mulopLeftOccured = true;
				op1 = mulopRightStack.pop();
				Code.load(op1);
				while(!mulopLeftQueue.isEmpty()) {
					operation = mulopLeftQueue.remove();
					op2 = mulopLeftQueue.remove();
					Code.load(op2);
					Code.put(operation.getAdr());
				}
			}
			if(mulopLeftOccured && !mulopRightStack.isEmpty()) {
				operation = mulopRightStack.pop();
				op1 = mulopRightStack.pop();
				Code.load(op1);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				Code.put(operation.getAdr());
				Code.store(op1);
			} else {
				if(!mulopRightStack.isEmpty())
					op1 = mulopRightStack.pop();
				else
					lastLoad = false;
			}
			while(!mulopRightStack.isEmpty()) {
				op2 = op1;
				operation = mulopRightStack.pop();
				op1 = mulopRightStack.pop();
				Code.load(op1);
				Code.load(op2);
				Code.put(operation.getAdr());
				Code.store(op1);
			}
			if(lastLoad)
				Code.load(op1);
			mulopLeftOccured = false;
		}
	}
}
