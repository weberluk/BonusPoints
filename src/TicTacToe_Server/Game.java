package TicTacToe_Server;


public class Game {
	
	private String id;
	private String gameid;
	private String name;
	private String points;
	
	public Game (String id, String gameid, String name, String points){
		this.id = id;
		this.gameid = gameid;
		this.name = name;
		this.points = points;
	}
	
	public String getId(){
		return this.id;
	}
	public String getGameId(){
		return this.gameid;
	}
	public String getName(){
		return this.name;
	}
	public String getPoints(){
		return this.points;
	}

}
