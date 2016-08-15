package model.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import controller.Project;
import model.Variables;

public class SaveStateFactory {
	public static void saveMath(Project pm, String name) throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name+".txt", false)));
		
		Variables variables = pm.getVariables();

		printVariablesInfo(variables, out);

		out.flush();
		out.close();

	}

	private static void printVariablesInfo(Variables variables, PrintWriter out) {
		Set<String> vars = variables.getVariableList();
		for (Iterator<String> it = vars.iterator();
				it.hasNext(); ) {
		   String var = (String) it.next();
		   Double value = variables.getVariable(var);
		   out.println("set " + var + " " + value);
		}
	}
}
