package se.chalmers.dryleafsoftware.androidrally.server;

import java.lang.reflect.Method;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.shared.IProtocol;
import se.chalmers.dryleafsoftware.androidrally.shared.OperatorValue;


public class Operator {
	private Connection connection = null;
	private List<Game> games = null;
	private IProtocol protocol = null;
	
	public Operator(Connection c, List<Game> g) {
		this.games = g;
		this.connection = c;
		// TODO Auto-generated constructor stub
	}
	
	public void handle(String data){
		int operator = parseIntToFirst(data, '$');
		data = substractData(data, '$');
		
		switch (operator){
		case 1:{
//			client(newOperator, newData, operator +"$");
			break;
		} case 2:{
//			game(newOperator, newData, operator +"$");
		} default:{
			break;
		}
	}
		
		
//		Method[] methods = IProtocol.class.getMethods();
//		
//		for ( Method method : methods ) {
//	        if ( method.isAnnotationPresent(OperatorValue.class) ) {
//	        	OperatorValue annotation = method.getAnnotation(OperatorValue.class);
//	        	
//	        	
//	        	
//	        	System.out.println(annotation.value());
//	        	break;
//	        }
//		}
	}
	
	
	
	public void interpret(String data){
		int newOperator = parseIntToFirst(data, '$');
		String newData = substractData(data, '$');
		interpret(newOperator, newData);
	}
	
	private void interpret(int operator, String data){
		int newOperator = parseIntToFirst(data, '$');
		String newData = substractData(data, '$');
		
		switch (operator){
			case 1:{
				client(newOperator, newData, operator +"$");
				break;
			} case 2:{
				game(newOperator, newData, operator +"$");
			} default:{
				break;
			}
		}
	}
	private void game(int operator, String data, String route) {
		int newOperator = parseIntToFirst(data, '$');
		String newData = substractData(data, '$');
		
		switch (operator){
			case 1:{
				//TODO
				break;
			} case 2:{
				//TODO
			} default:{
				break;
			}
		}
		// TODO Auto-generated method stub
		
	}
	private void client(int operator, String data, String route) {
		int newOperator = parseIntToFirst(data, '$');
		String newData = substractData(data, '$');
		switch (operator){
			case 1:{
				verifyClientID(data);
				break;
			} case 2:{
				//TODO
			} default:{
				break;
			}
		}
			// TODO Auto-generated method stub
		
	}

	private void verifyClientID(String data){
		connection.send(Client.getCorrectID(data));
	}

	protected void generateNewGame(String data, String route){
		//TODO method
	}
	
	
	
	/**
	 * Will parse the first characters of a String till the character specified shows up.
	 * @param data the string.
	 * @param c the first character not to be included.
	 * @return -1 if failed otherwise the parsed data.
	 */
	private int parseIntToFirst(String data, int c){
		int i =-1;
		try {
			String data2 = data.substring(0, data.indexOf(c));
			i = Integer.parseInt(data2);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return i;
	}
	
	private String substractData(String data, int c){
		String s = null;
		try {
			s = data.substring(data.indexOf(c)+1);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return s;
		
	}


}
