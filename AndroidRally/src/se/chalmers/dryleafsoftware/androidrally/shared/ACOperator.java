package se.chalmers.dryleafsoftware.androidrally.shared;

public abstract class ACOperator {
	
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
				client(newOperator, newData);
				break;
			} case 2:{
				game(newOperator, newData);
			} default:{
				break;
			}
		}
	}
	private void game(int operator, String data) {
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
	private void client(int operator, String data) {
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

	protected abstract void verifyClientID(String data);
	
	
	
	
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
