package se.chalmers.dryleafsoftware.androidrally.shared;

public abstract class ACOperator {
	private ACClient client;	
	
	public ACOperator(ACClient c) {
		this.client = c;
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
				verifyClientID(data, route + operator + "$");
				break;
			} case 2:{
				//TODO
			} default:{
				break;
			}
		}
			// TODO Auto-generated method stub
		
	}

	protected abstract void verifyClientID(String data, String route);
	
	protected abstract void generateNewGame(String data, String route); //TODO använd
	
	
	
	
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

	public ACClient getClient() {
		return client;
	}



}
